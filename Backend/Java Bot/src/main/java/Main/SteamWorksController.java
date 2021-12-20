package Main;

import API_Interactions.DotaAPI;
import API_Interactions.SpectatorAPI;
import Callbacks.SteamFriendsImpl;
import Callbacks.SteamUserImpl;
import com.codedisaster.steamworks.SteamFriends;
import com.codedisaster.steamworks.SteamID;
import com.codedisaster.steamworks.SteamUser;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Author Bryan Hill
 */

public class SteamWorksController {

    public final int DOTA_2_ID = 570;
    private SteamID personalID;
    private HashSet<SteamID> inDotaClientSet;       // Set for friends in Dota.
    private HashSet<SteamID> inDotaMatchSet;        // Set for friends in current match.
    private Queue<SteamID> presenceQueue;           // Queue for handling input.
    private SteamUser steamUser;                    // SteamUser for interacting with user information.
    private SteamFriends steamFriends;              // SteamFriends for interacting with steam friends information.
    private MessageManager messageManager;          // MessageManager for sending steam messages.
    private SpectatorAPI spectatorAPI;              // API for getting steam_server_ids.
    private DotaAPI dotaAPI;
    private static SteamWorksController instance;   // Singleton instance.

    private SteamWorksController() {
        printSpacer();
        inDotaClientSet = new HashSet<>();
        inDotaMatchSet = new HashSet<>();
        presenceQueue = new LinkedList<>();
        steamUser = new SteamUser(new SteamUserImpl());
        steamFriends = new SteamFriends(new SteamFriendsImpl());
        messageManager = new MessageManager();
        spectatorAPI = new SpectatorAPI();
        dotaAPI = new DotaAPI();
        personalID = steamUser.getSteamID();
        printSpacer();
    }

    /**
     * Method will fill inDotaClientSet with all friends that are currently online and in Dota 2.
     * @PostCondition inDotaClientSet is filled with online friends that are in Dota 2.
     */
    public void refreshClientSet() {

        this.inDotaClientSet = new HashSet<>();
        int friendCount = this.steamFriends.getFriendCount(SteamFriends.FriendFlags.Immediate);

        for (int i = 0; i < friendCount; i++) {
            SteamID id = this.steamFriends.getFriendByIndex(i, SteamFriends.FriendFlags.Immediate);
            SteamFriends.PersonaState state = this.steamFriends.getFriendPersonaState(id);
            if (state == SteamFriends.PersonaState.Online) {
                SteamFriends.FriendGameInfo info = new SteamFriends.FriendGameInfo();
                this.steamFriends.getFriendGamePlayed(id, info);
                if (info.getGameID() == this.DOTA_2_ID) {
                    this.inDotaClientSet.add(id);
                }
            }
        }
        System.out.println("Friends in Dota 2: " + this.inDotaClientSet);
    }

    /**
     * Method adds friend to client set if not present and not the running user.
     * @param friendID
     */
    public void addToClientSet(SteamID friendID) {
        if (!this.inDotaClientSet.contains(friendID) && !friendID.equals(this.personalID)) {
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
            sendLobbyChatMessage(friendID);
        }
    }

    /**
     * Method removes user from game set and sends message (If still in dota 2).
     * @param friendID
     */
    public void removeFromInGameSet(SteamID friendID) {
        if (this.inDotaMatchSet.contains(friendID)) {
            this.inDotaMatchSet.remove(friendID);

            // If the user is still in Dota 2
            SteamFriends.FriendGameInfo info = new SteamFriends.FriendGameInfo();
            this.steamFriends.getFriendGamePlayed(friendID, info);

            if (info.getGameID() == this.DOTA_2_ID) {
                System.out.println("Player has left game: " + friendID);
                sendPostGameMessage(friendID);
            }
        }
    }

    /**
     * Method is currently a stub.
     */
    public void sendLobbyChatMessage(SteamID id) {
        try {
            Thread.sleep(500);
            System.out.println("Sending Message to: " + id);
            String steam_server_id = spectatorAPI.requestSteamServerID(id);
            String message = getPlayerMessage(steam_server_id);
            messageManager.sendMessage(id, message);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Error with thread.sleep");
        }
    }

    public String getPlayerMessage(String server_steam_id) {
        List<DotaAPI.PlayerInfo> players = dotaAPI.getPlayersFromServer(server_steam_id);
        if (players.size() == 0) {
            return "Dota 2 Game Coordinator is unresponsive";
        }
        String message = "Players in lobby:\n";

        for (int i = 0; i < players.size(); i++) {
            DotaAPI.PlayerInfo info = players.get(i);
            message += "Player #" + i + ": " +
                    info.steamID + ", " +
                    "Team: " + (i / 5) + ", " +
                    info.personaName + "\n";
        }

        return message;

    }

    /**
     * Method will handle the SteamIDs one at a time.
     */
    public void processQueue() {
        while (!presenceQueue.isEmpty()) {
            SteamID steamID = presenceQueue.poll();
            System.out.println("Popping id: " + steamID + " off the queue.");
            handlePresenceChange(steamID);
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

        // Getting rich context
        String richPresence = SteamWorksController.
                getInstance().
                getSteamFriends().
                getFriendRichPresence(steamID, "steam_display");

        // Adding to dota client set.
        addOrRemoveFromClientSet(info, steamID);

        // If friend is loaded in-game.x
        if (richPresence.toLowerCase().contains("lvl") || richPresence.toLowerCase().contains("level")) {
            SteamWorksController.getInstance().addToInGameSet(steamID);
        }
        if (richPresence.equals("Main Menu")) {
            SteamWorksController.getInstance().removeFromInGameSet(steamID);
        }
    }

    /**
     * Method will remove or add users to the Dota 2 client set based on if they are playing the game.
     * @param info Object to information on user's current game they are playing.
     * @param steamID Object identifying the user being examined.
     */
    public void addOrRemoveFromClientSet(SteamFriends.FriendGameInfo info, SteamID steamID) {
        if (info.getGameID() == SteamWorksController.getInstance().DOTA_2_ID) {
            SteamWorksController.getInstance().addToClientSet(steamID);
        } else {
            SteamWorksController.getInstance().removeFromClientSet(steamID);
            SteamWorksController.getInstance().removeFromInGameSet(steamID);
        }
    }

    /**
     * Method is currently a stub.
     */
    public void sendPostGameMessage(SteamID id) {
        messageManager.sendMessage(id, "Nice Job Feeding!");
    }

    /**
     * Method used to improve console output.
     */
    public void printSpacer() {
        System.out.println("\n**********************************\n");
    }


    /**
     * Method to get instance of this class. Singleton pattern.
     * @return running instance of SteamWorksController.
     */
    public static SteamWorksController getInstance() {
        if (instance == null) {
            instance = new SteamWorksController();
        }
        return instance;
    }

    /**
     * Getter for SteamFriends.
     * @return SteamFriends
     */
    public SteamFriends getSteamFriends() {
        return this.steamFriends;
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

    /**
     * Getter for the messageManager object.
     * @return MessageManager for sending messages.
     */
    public MessageManager getMessageManager() {
        return messageManager;
    }

    /**
     * Getter for the presence queue.
     * @return Queue<SteamID> presenceQueue.
     */
    public Queue<SteamID> getPresenceQueue() {
        return presenceQueue;
    }


    /**
     * Getter for the personal SteamID.
     * @return the SteamID of the running account.
     */
    public SteamID getPersonalID() {
        return personalID;
    }
}
