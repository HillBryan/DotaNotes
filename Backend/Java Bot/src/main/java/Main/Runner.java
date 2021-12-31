package Main;

import Steam_Controllers.ProcessQueue;
import Steam_Controllers.SteamKitController;
import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author Bryan Hill
 */

public class Runner {


    public static void main(String[] args) {

        // Initializing SteamWorks Libraries.
        initializeSteamWorks();

        //Printing for console formatting.
        printSpacer();

        // Initializing SteamKit Libraries.
        initializeSteamKit();

        //Printing for console formatting.
        printSpacer();

        // Controller object for all operations with the SteamWorks API.
        ProcessQueue queue = ProcessQueue.getInstance();

        // Initial parse of friend list state.
        queue.getFriendStateManager().refreshClientSet();

        // Starting callback repeat timer.
        Timer t = new Timer();
        t.schedule(new TimerTask() {

            int clientSize = getClientSetSize(queue);

            public void run() {
                SteamAPI.runCallbacks();
                queue.processQueue();

                int currentSize = getClientSetSize(queue);

                if (currentSize != clientSize) {
                    if (currentSize < clientSize) System.out.print("Friend has Left Dota: ");
                    else System.out.print("Friend has Joined Dota: ");
                    System.out.println(queue.getFriendStateManager().getInDotaClientSet());
                }
                clientSize = getClientSetSize(queue);
            }
        }, 0, 1000);
    }

    /**
     * Method for getting the size of the client set.
     * @param queue ProcessQueue for managing queued steamID updates.
     * @return
     */
    public static int getClientSetSize(ProcessQueue queue) {
        return queue.getFriendStateManager().getInDotaClientSet().size();
    }

    /**
     * Method initializes Steam libraries.
     * This is the first step to running this application.
     */
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

    public static void initializeSteamKit() {
        SteamKitController.getInstance();
    }


    /**
     * Method used to improve console output.
     */
    public static void printSpacer() {
        System.out.println("\n**********************************\n");
    }
}
