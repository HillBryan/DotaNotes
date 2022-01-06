package Main;

import API_Interactions.DatabaseAPI;
import API_Interactions.DotaAPI;
import ResponseObjects.RealtimeStats;
import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamException;
import com.codedisaster.steamworks.SteamID;

public class TestRunner {

    public static void main(String[] args) {

        // Initializing SteamWorks Libraries.
        initializeSteamWorks();

        DatabaseAPI databaseAPI = new DatabaseAPI();
        DotaAPI dotaAPI = new DotaAPI();


        SteamID id = SteamID.createFromNativeHandle(76561198062539476L);

        RealtimeStats stats = dotaAPI.getPlayersFromServer("90154778327979008");
        databaseAPI.addLink(stats, id, "test");

    }

    public static void initializeSteamWorks() {
        try {
            SteamAPI.loadLibraries();
            if (!SteamAPI.init()) {
                System.out.println("Error finding Steam Client");
            }
        } catch (SteamException e) {
            System.out.println("Steam auth Error: " + e);
        }
        System.out.println("\n**********************************\n");
        System.out.println("SteamWorks: Login Success!");
    }
}
