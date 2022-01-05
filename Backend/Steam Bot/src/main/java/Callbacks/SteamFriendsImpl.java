package Callbacks;

import Steam_Controllers.ProcessQueue;
import com.codedisaster.steamworks.*;

/**
 * @Author Bryan Hill
 */

public class SteamFriendsImpl implements SteamFriendsCallback {

    public SteamFriendsImpl() {}

    @Override
    public void onSetPersonaNameResponse(boolean b, boolean b1, SteamResult steamResult) {}

    @Override
    public void onPersonaStateChange(SteamID steamID, SteamFriends.PersonaChange personaChange) {}

    @Override
    public void onGameOverlayActivated(boolean b) {}

    @Override
    public void onGameLobbyJoinRequested(SteamID steamID, SteamID steamID1) {}

    @Override
    public void onAvatarImageLoaded(SteamID steamID, int i, int i1, int i2) {}

    @Override
    public void onFriendRichPresenceUpdate(SteamID steamID, int i) {
        ProcessQueue.getInstance().addToProcessQueue(steamID);
    }

    @Override
    public void onGameRichPresenceJoinRequested(SteamID steamID, String s) {}

    @Override
    public void onGameServerChangeRequested(String s, String s1) {}
}
