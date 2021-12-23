package Steam_Controllers;

import Credentials.Config;
import com.codedisaster.steamworks.SteamID;
import in.dragonbra.javasteam.enums.EChatEntryType;
import in.dragonbra.javasteam.enums.EPersonaState;
import in.dragonbra.javasteam.enums.EResult;
import in.dragonbra.javasteam.steam.handlers.steamfriends.SteamFriends;
import in.dragonbra.javasteam.steam.handlers.steamuser.LogOnDetails;
import in.dragonbra.javasteam.steam.handlers.steamuser.SteamUser;
import in.dragonbra.javasteam.steam.handlers.steamuser.callback.*;
import in.dragonbra.javasteam.steam.steamclient.SteamClient;
import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackManager;
import in.dragonbra.javasteam.steam.steamclient.callbacks.ConnectedCallback;
import in.dragonbra.javasteam.steam.steamclient.callbacks.DisconnectedCallback;

/**
 * @Author Bryan Hill
 */
public class SteamKitController {

    private SteamClient steamClient;
    private SteamFriends steamFriends;
    private CallbackManager manager;
    private SteamUser steamUser;

    public SteamKitController() {
        // Steam Objects
        steamClient = new SteamClient();
        steamFriends = steamClient.getHandler(SteamFriends.class);
        manager = new CallbackManager(steamClient);
        steamUser = steamClient.getHandler(SteamUser.class);

        // Callbacks
        manager.subscribe(ConnectedCallback.class, this::onConnected);
        manager.subscribe(DisconnectedCallback.class, this::onDisconnected);
        manager.subscribe(LoggedOnCallback.class, this::onLoggedOn);
        manager.subscribe(AccountInfoCallback.class, this::onAccountInfo);

        // Starting the connection to steam.
        connectToSteam();
    }

    /**
     * Method for connecting to Steam.
     */
    public void connectToSteam() {
        System.out.println("SteamKit: Connecting to steam...");

        // initiate the connection
        steamClient.connect();

        // create our callback handling loop. Four callbacks, five Loop runs.
        for (int i = 0; i < 5; i++) {
            // in order for the callbacks to get routed, they need to be handled by the manager
            manager.runWaitCallbacks(1000L);
        }
    }

    /**
     * Method for sending message on steam.
     * @param id The SteamID for the recipient.
     * @param message The Message to be sent.
     */
    public void sendMessage(SteamID id, String message) {
        // Getting SteamWorks SteamID and converting to Long.
        String idString = id.toString();
        Long idLong = Long.parseLong(idString, 16);

        System.out.println("Sending message: " + message + " To: " + id);
        // Sending Message with new SteamKit SteamID.
        steamFriends.sendChatMessage(
                new in.dragonbra.javasteam.types.SteamID(idLong),
                EChatEntryType.ChatMsg,
                message
        );

        System.out.println("Message sent!");
    }

    /**
     * Callback for when the Client connects to steam.
     * @param callback
     */
    private void onConnected(ConnectedCallback callback) {
        System.out.println("SteamKit: Connected to Steam! Logging in " + Config.USERNAME + "...");
        LogOnDetails details = new LogOnDetails();
        details.setUsername(Config.USERNAME);
        details.setPassword(Config.PASSWORD);
        details.setLoginID(149);
        steamUser.logOn(details);
    }

    /**
     * Call back for when the client disconnects from steam.
     * @param callback
     */
    private void onDisconnected(DisconnectedCallback callback) {
        System.out.println("SteamKit: Disconnected from Steam");
    }

    /**
     * Callback for when the client logs onto steam.
     * @param callback
     */
    private void onLoggedOn(LoggedOnCallback callback) {
        if (callback.getResult() != EResult.OK) {
            if (callback.getResult() == EResult.AccountLogonDenied) {
                System.out.println("SteamKit: Unable to logon to Steam: This account is SteamGuard protected.");
                return;
            }
            System.out.println("SteamKit: Unable to logon to Steam: " + callback.getResult());
            return;
        }
        System.out.println("SteamKit: Successfully logged on!");
    }

    /**
     * Call back for when the steam client logs offline.
     * @param callback
     */
    private void onLoggedOff(LoggedOffCallback callback) {
        System.out.println("SteamKit: Logged off of Steam: " + callback.getResult());
    }

    /**
     * Callback for when steam loads up. Used to set online state.
     * @param callback
     */
    private void onAccountInfo(AccountInfoCallback callback) {
        steamFriends.setPersonaState(EPersonaState.Online);
    }
}
