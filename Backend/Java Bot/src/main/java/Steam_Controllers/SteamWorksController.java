package Steam_Controllers;

import Callbacks.SteamFriendsImpl;
import Callbacks.SteamUserImpl;
import Messages.MessageFactory;
import com.codedisaster.steamworks.SteamFriends;
import com.codedisaster.steamworks.SteamID;
import com.codedisaster.steamworks.SteamUser;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author Bryan Hill
 */

public class SteamWorksController {

    public final int DOTA_2_ID = 570;
    private SteamUser steamUser;                    // SteamUser for interacting with user information.
    private SteamFriends steamFriends;              // SteamFriends for interacting with steam friends information.
    private static SteamWorksController instance;   // Singleton instance.

    private SteamWorksController() {
        steamUser = new SteamUser(new SteamUserImpl());
        steamFriends = new SteamFriends(new SteamFriendsImpl());
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
     * Getter for the personal SteamID.
     * @return the SteamID of the running account.
     */
    public SteamID getPersonalID() {
        return steamUser.getSteamID();
    }
}
