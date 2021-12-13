package Main;

import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamException;

import java.util.Timer;
import java.util.TimerTask;

public class Runner {


    public static void main(String[] args) {

        // Initializing Steam Libraries.
        initialize();
        System.out.println("Initializing Success!");

        // Controller object for all operations with the SteamWorks API.
        ApplicationController controller = ApplicationController.getInstance();

        // Initial parse of friend list state.
        controller.refreshClientSet();


        int matchSize = controller.getInDotaMatchSet().size();

        // Starting callback repeat timer.
        Timer t = new Timer();
        t.schedule(new TimerTask() {

            int clientSize = controller.getInDotaClientSet().size();

            public void run() {
                SteamAPI.runCallbacks();

                if (controller.getInDotaClientSet().size() != clientSize) {
                    System.out.println("New Friend has Joined Dota!");
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
    }
}
