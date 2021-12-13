package Main;

import Callbacks.SteamFriendsImpl;
import Callbacks.SteamUserImpl;
import com.codedisaster.steamworks.SteamFriends;
import com.codedisaster.steamworks.SteamID;
import com.codedisaster.steamworks.SteamUser;

import java.util.HashSet;

public class ApplicationController {

    public final int DOTA_2_ID = 570;
    private SteamID personalID;
    private HashSet<SteamID> inDotaClientSet;       // Set for friends in Dota.
    private HashSet<SteamID> inDotaMatchSet;        // Set for friends in current match.
    private SteamUser steamUser;                    // SteamUser for interacting with user information.
    private SteamFriends steamFriends;              // SteamFriends for interacting with steam friends information.
    private static ApplicationController instance;

    private ApplicationController() {
        inDotaClientSet = new HashSet<>();
        inDotaMatchSet = new HashSet<>();
        steamUser = new SteamUser(new SteamUserImpl());
        steamFriends = new SteamFriends(new SteamFriendsImpl(
                inDotaClientSet,
                inDotaMatchSet
        ));
        personalID = steamUser.getSteamID();
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
    }

    public void addToClientSet(SteamID friendID) {
        if (!this.inDotaClientSet.contains(friendID) && !friendID.equals(this.personalID)) {
            this.inDotaClientSet.add(friendID);
        }
    }

    public void removeFromClientSet(SteamID friendID) {
        if (this.inDotaClientSet.contains(friendID)) {
            this.inDotaClientSet.remove(friendID);
        }
    }

    public void addToInGameSet(SteamID friendID) {
        if (!this.inDotaMatchSet.contains(friendID) && !friendID.equals(this.personalID)) {
            this.inDotaMatchSet.add(friendID);

            // TODO: This is where message would be sent.
            sendLobbyChatMessage();
        }
    }

    public void removeFromInGameSet(SteamID friendID) {
        if (this.inDotaMatchSet.contains(friendID)) {
            this.inDotaMatchSet.remove(friendID);

            // If the user is still in Dota 2
            SteamFriends.FriendGameInfo info = new SteamFriends.FriendGameInfo();
            this.steamFriends.getFriendGamePlayed(friendID, info);

            if (info.getGameID() == this.DOTA_2_ID) {
                // TODO: This is where message would be sent.
                sendPostGameMessage();
            }
        }
    }

    public void sendLobbyChatMessage() {
        System.out.println("Good luck on your game!");
    }

    public void sendPostGameMessage() {
        System.out.println("Awesome job on your game!");
    }


    public static ApplicationController getInstance() {
        if (instance == null) {
            instance = new ApplicationController();
        }
        return instance;
    }

    public SteamFriends getSteamFriends() {
        return this.steamFriends;
    }

    public HashSet<SteamID> getInDotaClientSet() {
        return inDotaClientSet;
    }

    public HashSet<SteamID> getInDotaMatchSet() {
        return inDotaMatchSet;
    }
}
