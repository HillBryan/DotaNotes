package Callbacks;

import Main.ApplicationController;
import com.codedisaster.steamworks.SteamFriends;
import com.codedisaster.steamworks.SteamFriendsCallback;
import com.codedisaster.steamworks.SteamID;
import com.codedisaster.steamworks.SteamResult;

import java.util.HashSet;

public class SteamFriendsImpl implements SteamFriendsCallback {

    HashSet<SteamID> inDotaClientSet;
    HashSet<SteamID> inDotaMatchSet;

    public SteamFriendsImpl(HashSet<SteamID> inDotaClientSet, HashSet<SteamID> inDotaMatchSet) {
        this.inDotaClientSet = inDotaClientSet;
        this.inDotaMatchSet = inDotaMatchSet;
    }

    @Override
    public void onSetPersonaNameResponse(boolean b, boolean b1, SteamResult steamResult) {

    }

    @Override
    public void onPersonaStateChange(SteamID steamID, SteamFriends.PersonaChange personaChange) {

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

        // Getting current game.
        SteamFriends.FriendGameInfo info = new SteamFriends.FriendGameInfo();
        ApplicationController.getInstance().getSteamFriends().getFriendGamePlayed(steamID, info);

        // Getting rich context
        String richPresence = ApplicationController.
                getInstance().
                getSteamFriends().
                getFriendRichPresence(steamID, "steam_display");

        // Adding to dota client set.
        if (info.getGameID() == ApplicationController.getInstance().DOTA_2_ID) {
            ApplicationController.getInstance().addToClientSet(steamID);
        } else {
            ApplicationController.getInstance().removeFromClientSet(steamID);
            ApplicationController.getInstance().removeFromInGameSet(steamID);
        }

        // If "hero-select" is enabled
        if (richPresence.contains("select")) {
            ApplicationController.getInstance().addToInGameSet(steamID);
        }
        if (richPresence.contains("Main Menu")) {
            ApplicationController.getInstance().removeFromInGameSet(steamID);
        }
    }

    @Override
    public void onGameRichPresenceJoinRequested(SteamID steamID, String s) {

    }

    @Override
    public void onGameServerChangeRequested(String s, String s1) {

    }

}
