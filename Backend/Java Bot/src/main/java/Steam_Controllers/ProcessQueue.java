package Steam_Controllers;

import com.codedisaster.steamworks.SteamID;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: Bryan Hill
 */
public class ProcessQueue {

    private Queue<SteamID> presenceQueue;           // Queue for handling input.
    private static ProcessQueue instance;

    private FriendStateManager friendStateManager;  // FriendStateManager for managing friends in Dota.

    public ProcessQueue() {
        presenceQueue = new LinkedList<>();
        friendStateManager = new FriendStateManager();
    }

    /**
     * Method will handle the SteamIDs one at a time.
     */
    public void processQueue() {
        if (!presenceQueue.isEmpty()) {
            SteamID steamID = presenceQueue.poll();
            System.out.println("Popping id: " + steamID + " off the queue.");
            friendStateManager.handlePresenceChange(steamID);
        }
    }

    /**
     * Method adds steamID to processQueue if valid.
     * @param steamID to be added to queue.
     */
    public void addToProcessQueue(SteamID steamID) {
        if (friendStateManager.considerForQueue(steamID)) {
            presenceQueue.add(steamID);
        }
    }

    /**
     * Method for ProcessQueue Singleton.
     * @return the global instance of ProcessQueue.
     */
    public static ProcessQueue getInstance() {
        if (instance == null) {
            instance = new ProcessQueue();
        }
        return instance;
    }

    /**
     * Getter for the FriendStateManager instance.
     * @return FriendStateManager.
     */
    public FriendStateManager getFriendStateManager() {
        return friendStateManager;
    }
}
