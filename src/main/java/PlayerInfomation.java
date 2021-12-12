import com.codedisaster.steamworks.SteamFriends;
import com.codedisaster.steamworks.SteamFriendsCallback;
import com.codedisaster.steamworks.SteamID;
import com.codedisaster.steamworks.SteamResult;

import java.util.HashSet;

public class PlayerInfomation implements SteamFriendsCallback {

    SteamFriends player;
    HashSet<SteamID> set;

    public PlayerInfomation(HashSet<SteamID> set) {
         player = new SteamFriends(this);
         this.set = set;
    }

    @Override
    public void onSetPersonaNameResponse(boolean b, boolean b1, SteamResult steamResult) {
        System.out.println("SET Persona Name!");
    }

    @Override
    public void onPersonaStateChange(SteamID steamID, SteamFriends.PersonaChange personaChange) {
        SteamFriends.FriendGameInfo info = new SteamFriends.FriendGameInfo();
        player.getFriendGamePlayed(steamID, info);

        if (personaChange == SteamFriends.PersonaChange.GamePlayed && info.getGameID() == 570) {
            set.add(steamID);
        }
//        else if (personaChange == SteamFriends.PersonaChange.Status && info.getGameID() != 570 && set.contains(steamID)) {
//            set.remove(steamID);
//        }

    }

    @Override
    public void onGameOverlayActivated(boolean b) {
        System.out.println("GAME OVERLAY!");
    }

    @Override
    public void onGameLobbyJoinRequested(SteamID steamID, SteamID steamID1) {
        System.out.println("Join Lobby Request!");
    }

    @Override
    public void onAvatarImageLoaded(SteamID steamID, int i, int i1, int i2) {
        System.out.println("Avatar Change!");
    }

    @Override
    public void onFriendRichPresenceUpdate(SteamID steamID, int i) {
        System.out.println("RichPresence Update: " + steamID + " " + i);
        System.out.println(player.getFriendPersonaName(steamID));
        String presence = player.getFriendRichPresence(steamID, "steam_display");
        System.out.println();
    }

    @Override
    public void onGameRichPresenceJoinRequested(SteamID steamID, String s) {
        System.out.println("Rich Presence Join Request!");
    }

    @Override
    public void onGameServerChangeRequested(String s, String s1) {
        System.out.println("GameServer Request!");
    }

}
