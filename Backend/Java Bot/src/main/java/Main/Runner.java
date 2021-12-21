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

        //Printing for console formatting.
        printSpacer();

        // Controller object for all operations with the SteamWorks API.
        SteamWorksController controller = SteamWorksController.getInstance();

        //Printing for console formatting.
        printSpacer();

        // Initial parse of friend list state.
        controller.getFriendStateManager().refreshClientSet();

        // Starting callback repeat timer.
        Timer t = new Timer();
        t.schedule(new TimerTask() {

            int clientSize = getClientSetSize(controller);

            public void run() {
                SteamAPI.runCallbacks();
                controller.processQueue();

                int currentSize = getClientSetSize(controller);

                if (currentSize != clientSize) {
                    if (currentSize < clientSize) System.out.print("Friend has Left Dota: ");
                    else System.out.print("Friend has Joined Dota: ");
                    System.out.println(controller.getFriendStateManager().getInDotaClientSet());
                }
                clientSize = getClientSetSize(controller);
            }
        }, 0, 2000);
    }

    /**
     * Method for getting the size of the client set.
     * @param controller SteamWorksController for managing SteamWorks activities.
     * @return
     */
    public static int getClientSetSize(SteamWorksController controller) {
        return controller.getFriendStateManager().getInDotaClientSet().size();
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


    /**
     * Method used to improve console output.
     */
    public static void printSpacer() {
        System.out.println("\n**********************************\n");
    }
}
