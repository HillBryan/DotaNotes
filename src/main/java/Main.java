import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamException;
import com.codedisaster.steamworks.SteamFriends;
import com.codedisaster.steamworks.SteamID;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {

        try {
            SteamAPI.loadLibraries();
            if (!SteamAPI.init()) {
                System.out.println("Error");
                // Steamworks initialization error, e.g. Steam client not running
            }
        } catch (SteamException e) {
            // Error extracting or loading native libraries
        }

        HashSet<SteamID> dotaIDSet = new HashSet<>();
        PlayerInfomation steamFriends = new PlayerInfomation(dotaIDSet);
        int friendCount = steamFriends.player.getFriendCount(SteamFriends.FriendFlags.Immediate);

        for (int i = 0; i < friendCount; i++) {
            SteamID id = steamFriends.player.getFriendByIndex(i, SteamFriends.FriendFlags.Immediate);
            SteamFriends.PersonaState state = steamFriends.player.getFriendPersonaState(id);
            if (state == SteamFriends.PersonaState.Online) {
                SteamFriends.FriendGameInfo info = new SteamFriends.FriendGameInfo();
                steamFriends.player.getFriendGamePlayed(id, info);
                if (info.getGameID() == 570) {
                    dotaIDSet.add(id);
                }
            }
        }

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                SteamAPI.runCallbacks();

                for (SteamID id : dotaIDSet) {
                    System.out.println(
                      steamFriends.player.getFriendPersonaName(id) + " " +
                      steamFriends.player.getFriendRichPresence(id, "steam_display")
                    );
                }

            }
        }, 0, 1000);
    }
}
