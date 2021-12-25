package Steam_Controllers;

import Callbacks.SteamFriendsImpl;
import Callbacks.SteamMatchmakingImpl;
import Callbacks.SteamUserImpl;
import Messages.MessageFactory;
import com.codedisaster.steamworks.SteamFriends;
import com.codedisaster.steamworks.SteamID;
import com.codedisaster.steamworks.SteamMatchmaking;
import com.codedisaster.steamworks.SteamUser;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author Bryan Hill
 */

public class SteamWorksController {

    public final int DOTA_2_ID = 570;
    private Queue<SteamID> presenceQueue;           // Queue for handling input.
    private SteamUser steamUser;                    // SteamUser for interacting with user information.
    private SteamFriends steamFriends;              // SteamFriends for interacting with steam friends information.
    private SteamMatchmaking steamMatchmaking;      // SteamMatchmaking impl.
    private SteamKitController steamKitController;  // SteamKitController for sending steam messages.
    private FriendStateManager friendStateManager;  // State manager for friends in Dota 2.
    private static SteamWorksController instance;   // Singleton instance.

    private SteamWorksController() {
        presenceQueue = new LinkedList<>();
        steamUser = new SteamUser(new SteamUserImpl());
        steamFriends = new SteamFriends(new SteamFriendsImpl());
        steamMatchmaking = new SteamMatchmaking(new SteamMatchmakingImpl());
        friendStateManager = new FriendStateManager(steamUser.getSteamID());
        steamKitController = new SteamKitController();
    }

    /**
     * Method will handle the SteamIDs one at a time.
     */
    public void processQueue() {
        while (!presenceQueue.isEmpty()) {
            SteamID steamID = presenceQueue.poll();
            System.out.println("Popping id: " + steamID + " off the queue.");
            friendStateManager.handlePresenceChange(steamID);
        }
    }

    /**
     * Method is currently a stub.
     */
    public void sendPreGameMessage(SteamID id) {
        System.out.println("Sending Message to: " + id);
        String message = MessageFactory.getInstance().getPreGameMessage(id);

        // Trying to get lobby data
        SteamID lobbyID = MessageFactory.getInstance().getSteamServerID(id);
        steamMatchmaking.requestLobbyData(lobbyID);
        int data = steamMatchmaking.getLobbyDataCount(lobbyID);
        System.out.println("Key Count: " + data);



        try {
            Thread.sleep(1000);
            steamKitController.sendMessage(id, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Method is currently a stub.
     */
    public void sendPostGameMessage(SteamID id) {
        String message = MessageFactory.getInstance().getPostGameMessage(id);
        steamKitController.sendMessage(id, message);
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
     * Getter for the steamKitController object.
     * @return SteamKitController for sending messages.
     */
    public SteamKitController getSteamKitController() {
        return steamKitController;
    }

    /**
     * Getter for the FriendStateManager Object.
     * @return friendStateManager for managing friend state.
     */
    public FriendStateManager getFriendStateManager() {
        return friendStateManager;
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
        return steamUser.getSteamID();
    }
}
