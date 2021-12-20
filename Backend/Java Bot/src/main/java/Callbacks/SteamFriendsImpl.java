package Callbacks;

import Main.SteamWorksController;
import com.codedisaster.steamworks.SteamFriends;
import com.codedisaster.steamworks.SteamFriendsCallback;
import com.codedisaster.steamworks.SteamID;
import com.codedisaster.steamworks.SteamResult;

import java.util.HashSet;

/**
 * @Author Bryan Hill
 */

public class SteamFriendsImpl implements SteamFriendsCallback {

    public SteamFriendsImpl() {
    }

    @Override
    public void onSetPersonaNameResponse(boolean b, boolean b1, SteamResult steamResult) {

    }

    @Override
    public void onPersonaStateChange(SteamID steamID, SteamFriends.PersonaChange personaChange) {
        // In some instances, this will trigger when one leaves a game.
        System.out.println("Received friend persona change: " + steamID);
        SteamWorksController.getInstance().getPresenceQueue().add(steamID);
    }

    @Override
    public void onGameOverlayActivated(boolean b) {

    }

    @Override
    public void onGameLobbyJoinRequested(SteamID steamID, SteamID steamID1) {

    }

    @Override
    public void onAvatarImageLoaded(SteamID steamID, int i, int i1, int i2) {

    }

    @Override
    public void onFriendRichPresenceUpdate(SteamID steamID, int i) {
        // This is where game change notifications will take place.
        System.out.println("Received Friend Presence change: " + steamID);
        SteamWorksController.getInstance().getPresenceQueue().add(steamID);
    }

    @Override
    public void onGameRichPresenceJoinRequested(SteamID steamID, String s) {

    }

    @Override
    public void onGameServerChangeRequested(String s, String s1) {

    }
}
