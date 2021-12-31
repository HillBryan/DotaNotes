package Steam_Controllers;

import Messages.MessageFactory;
import Steam_Controllers.SteamWorksController;
import com.codedisaster.steamworks.SteamFriends;
import com.codedisaster.steamworks.SteamID;

import java.util.HashSet;

/**
 * @Author Bryan Hill
 */
public class FriendStateManager {

    private HashSet<SteamID> inDotaClientSet;       // Set for friends in Dota.
    private HashSet<SteamID> inDotaMatchSet;        // Set for friends in current match.
    private SteamID personalID;                     // Personal steamID.


    public FriendStateManager() {
        //this.personalID = SteamWorksController.getInstance().getPersonalID();
        inDotaClientSet = new HashSet<>();
        inDotaMatchSet = new HashSet<>();
        this.personalID = SteamWorksController.getInstance().getPersonalID();
    }

    /**
     * Method adds friend to client set if not present and not the running user.
     * @param friendID
     */
    public void addToClientSet(SteamID friendID) {
        if (!this.inDotaClientSet.contains(friendID) && !friendID.equals(personalID)) {
            this.inDotaClientSet.add(friendID);
        }
    }

    /**
     * Method removes user from the client set if present.
     * @param friendID
     */
    public void removeFromClientSet(SteamID friendID) {
        if (this.inDotaClientSet.contains(friendID)) {
            this.inDotaClientSet.remove(friendID);
        }
    }

    /**
     * Method adds user to game set and sends chat message.
     * @param friendID
     */
    public void addToInGameSet(SteamID friendID) {
        if (!this.inDotaMatchSet.contains(friendID) && !friendID.equals(this.personalID)) {
            this.inDotaMatchSet.add(friendID);

            System.out.println("Player has joined game: " + friendID);
            MessageFactory.getInstance().sendPreGameMessage(friendID);
        }
    }

    /**
     * Method removes user from game set and sends message (If still in dota 2).
     * @param friendID
     */
    public void removeFromInGameSet(SteamID friendID) {
        if (this.inDotaMatchSet.contains(friendID)) {
            this.inDotaMatchSet.remove(friendID);

            // If the user is still in Dota 2.
            SteamFriends.FriendGameInfo info = new SteamFriends.FriendGameInfo();
            SteamWorksController.getInstance().getSteamFriends().getFriendGamePlayed(friendID, info);

            // Player leaves the Dota 2 match.
            if (info.getGameID() == SteamWorksController.getInstance().DOTA_2_ID) {
                System.out.println("Player has left game: " + friendID);
                MessageFactory.getInstance().sendPostGameMessage(friendID);
            }
        }
    }

    /**
     * Method will determine if friend has entered or left Dota or a dota game.
     * @param steamID The SteamID of the friend.
     */
    public void handlePresenceChange(SteamID steamID) {
        // Getting current game.
        SteamFriends.FriendGameInfo info = new SteamFriends.FriendGameInfo();
        SteamWorksController.getInstance().getSteamFriends().getFriendGamePlayed(steamID, info);

        // Getting rich presence
        String richPresence = SteamWorksController.
                getInstance().
                getSteamFriends().
                getFriendRichPresence(steamID, "steam_display");

        // Adding to dota client set.
        addOrRemoveFromClientSet(info, steamID);

        // If friend is loaded in-game.x
        if (richPresence.toLowerCase().contains("lvl") || richPresence.toLowerCase().contains("level")) {
            addToInGameSet(steamID);
        }
        if (richPresence.equals("Main Menu")) {
            removeFromInGameSet(steamID);
        }
    }


    /**
     * Method will remove or add users to the Dota 2 client set based on if they are playing the game.
     * @param info Object to information on user's current game they are playing.
     * @param steamID Object identifying the user being examined.
     */
    public void addOrRemoveFromClientSet(SteamFriends.FriendGameInfo info, SteamID steamID) {
        if (info.getGameID() == SteamWorksController.getInstance().DOTA_2_ID) {
            addToClientSet(steamID);
        } else {
            removeFromClientSet(steamID);
            removeFromInGameSet(steamID);
        }
    }

    /**
     * Method will fill inDotaClientSet with all friends that are currently online and in Dota 2.
     * @PostCondition inDotaClientSet is filled with online friends that are in Dota 2.
     */
    public void refreshClientSet() {

        // Getting SteamFriends and friend counts.
        SteamFriends steamFriends = SteamWorksController.getInstance().getSteamFriends();
        int friendCount = steamFriends.getFriendCount(SteamFriends.FriendFlags.Immediate);

        // Iterating over all friends and seeing if they are online and in Dota 2.
        for (int i = 0; i < friendCount; i++) {
            SteamID id = steamFriends.getFriendByIndex(i, SteamFriends.FriendFlags.Immediate);
            SteamFriends.PersonaState state = steamFriends.getFriendPersonaState(id);
            if (state == SteamFriends.PersonaState.Online) {
                SteamFriends.FriendGameInfo info = new SteamFriends.FriendGameInfo();
                steamFriends.getFriendGamePlayed(id, info);
                if (info.getGameID() == SteamWorksController.getInstance().DOTA_2_ID) {
                    this.inDotaClientSet.add(id);
                }
            }
        }
        System.out.println("Friends in Dota 2: " + this.inDotaClientSet);
    }

    /**
     * Method returns true if steamID is valid for joining the process queue.
     * Three conditions for joining the queue:
     * - steamID is not the bot personal steam ID.
     * - Friend joins Dota "Main Menu".
     * - Friend joins Dota game denoted by the level or lvl keyword.
     * @param steamID The steamID to be added to the queue.
     * @return True if steamID is valid for the queue, false otherwise.
     */
    public boolean considerForQueue(SteamID steamID) {

        // Getting rich presence
        String richPresence = SteamWorksController.
                getInstance().
                getSteamFriends().
                getFriendRichPresence(steamID, "steam_display");

        return !steamID.equals(personalID) &&
               (richPresence.toLowerCase().contains("lvl 1") ||
                richPresence.toLowerCase().contains("level 1") ||
                richPresence.toLowerCase().contains("Main Menu"));
    }

    /**
     * Getter for client set.
     * @return HashSet of clients in dota 2.
     */
    public HashSet<SteamID> getInDotaClientSet() {
        return inDotaClientSet;
    }

    /**
     * Getter for in match set.
     * @return HashSet of clients in a match.
     */
    public HashSet<SteamID> getInDotaMatchSet() {
        return inDotaMatchSet;
    }

}
