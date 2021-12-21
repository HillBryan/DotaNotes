package Messages;

import API_Interactions.DotaAPI;
import API_Interactions.SpectatorAPI;
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
        List<DotaAPI.PlayerInfo> players = dotaAPI.getPlayersFromServer(steam_server_id);
        String message = "Players in lobby:\n";

        if (players.size() == 0) {
            return "Dota 2 Game Coordinator is unresponsive";
        }


        for (int i = 0; i < players.size(); i++) {
            DotaAPI.PlayerInfo info = players.get(i);
            message += "Player #" + i + ": " +
                    info.steamID + ", " +
                    "Team: " + (i / 5) + ", " +
                    info.personaName + "\n";
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

    public static MessageFactory getInstance() {
        if (messageFactory == null) {
            messageFactory = new MessageFactory();
        }
        return messageFactory;
    }

}
