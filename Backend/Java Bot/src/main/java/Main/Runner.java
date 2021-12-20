package Main;

import API_Interactions.DotaAPI;
import API_Interactions.SpectatorAPI;
import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamException;
import com.codedisaster.steamworks.SteamID;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author Bryan Hill
 */

public class Runner {


    public static void main(String[] args) {

        // Initializing Steam Libraries.
        initialize();

        // Controller object for all operations with the SteamWorks API.
        SteamWorksController controller = SteamWorksController.getInstance();

        // Initial parse of friend list state.
        controller.refreshClientSet();

        // Starting callback repeat timer.
        Timer t = new Timer();
        t.schedule(new TimerTask() {

            int clientSize = controller.getInDotaClientSet().size();

            public void run() {
                SteamAPI.runCallbacks();
                controller.processQueue();
                int currentSize = controller.getInDotaClientSet().size();

                if (currentSize != clientSize) {
                    if (currentSize < clientSize) System.out.print("Friend has Left Dota: ");
                    else System.out.print("Friend has Joined Dota: ");
                    System.out.println(controller.getInDotaClientSet());
                }
                clientSize = controller.getInDotaClientSet().size();
            }
        }, 0, 1000);
    }

    /**
     * Method initializes Steam libraries.
     * This is the first step to running this application.
     */
    public static void initialize() {
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
