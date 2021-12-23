package Messages;

import API_Interactions.DotaAPI;
import API_Interactions.SpectatorAPI;
import ResponseObjects.Player;
import ResponseObjects.RealtimeStats;
import ResponseObjects.Team;
import com.codedisaster.steamworks.SteamID;

import java.util.List;

/**
 * @Author Bryan Hill
 */
public class MessageFactory {

    private static MessageFactory messageFactory;
    private DotaAPI dotaAPI;
    private SpectatorAPI spectatorAPI;

    private MessageFactory() {
        dotaAPI = new DotaAPI();
        spectatorAPI = new SpectatorAPI();
    }

    /**
     * Method gets message to be sent to players on game load.
     * @param steamID the SteamID of the message recipient.
     * @return
     */
    public String getPreGameMessage(SteamID steamID) {

        String steam_server_id = spectatorAPI.requestSteamServerID(steamID);
        RealtimeStats stats = dotaAPI.getPlayersFromServer(steam_server_id);
        String message = "Players in lobby:\n";

        if (stats == null) {
            return "Dota 2 Game Coordinator is unresponsive";
        }

        for (Team team : stats.getTeams()) {
            for (Player player : team.getPlayers()) {
                message += "Player: " + player.getName() + ", " +
                        player.getAccountid() + "\n";
            }
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
