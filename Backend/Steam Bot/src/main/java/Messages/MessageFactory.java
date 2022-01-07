package Messages;

import API_Interactions.DatabaseAPI;
import API_Interactions.DotaAPI;
import API_Interactions.SpectatorAPI;
import ResponseObjects.Player;
import ResponseObjects.RealtimeStats;
import ResponseObjects.Team;
import Steam_Controllers.SteamKitController;
import com.codedisaster.steamworks.SteamID;

/**
 * @Author Bryan Hill
 */
public class MessageFactory {

    private static MessageFactory messageFactory;
    private DotaAPI dotaAPI;
    private SpectatorAPI spectatorAPI;
    private DatabaseAPI databaseAPI;

    private MessageFactory() {
        dotaAPI = new DotaAPI();
        spectatorAPI = new SpectatorAPI();
        databaseAPI = new DatabaseAPI();
    }

    /**
     * Method is currently a stub.
     */
    public void sendPreGameMessage(SteamID id) {
        System.out.println("Sending Message to: " + id);
        String message = getPreGameMessage(id);
        SteamKitController.getInstance().sendMessage(id, message);
    }

    /**
     * Method is currently a stub.
     */
    public void sendPostGameMessage(SteamID id) {
        String message = getPostGameMessage(id);
        SteamKitController.getInstance().sendMessage(id, message);
    }

    /**
     * Method gets message to be sent to players on game load.
     * @param steamID the SteamID of the message recipient.
     * @return
     */
    public String getPreGameMessage(SteamID steamID) {

        String steam_server_id = spectatorAPI.requestSteamServerID(steamID);
        RealtimeStats stats = dotaAPI.getPlayersFromServer(steam_server_id);
        String message = "Your notes on players in-game as well as hero/match-up notes " +
                         " can be found at: ";


        if (stats == null) {
            return "Dota 2 Game Coordinator is unresponsive";
        }

        // Generating unique link based off of matchID, random number, and server_steam_id.
        String key = (stats.getMatch().getMatch_id() +
                     (Math.random() * 1000000) +
                     stats.getMatch().getServer_steam_id()).hashCode() + "f"
                     + steamID.toString().hashCode();

        // Putting into database.
        boolean success = databaseAPI.addLink(stats, steamID, key);

        if (success) {
            message += "www.dotaNotes.com/" + key;
        } else {
            return "Error connecting to database.";
        }

        return message;
    }

    /**
     * Message is currently a stub.
     * @return steamID the SteamID of the recipient.
     */
    public String getPostGameMessage(SteamID steamID) {
        return "Nice job: " + steamID;
    }

    /**
     * Singleton method for getting the factory instance.
     * @return MessageFactory for getting the single factory instance.
     */
    public static MessageFactory getInstance() {
        if (messageFactory == null) {
            messageFactory = new MessageFactory();
        }
        return messageFactory;
    }

}
