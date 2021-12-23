package API_Interactions;

import Credentials.Config;
import ResponseObjects.Player;
import ResponseObjects.RealtimeStats;
import ResponseObjects.Team;
import com.codedisaster.steamworks.SteamID;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Bryan Hill
 */
public class DotaAPI extends BaseAPI{

    private ObjectMapper objectMapper;

    public DotaAPI() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Method will return live stats from a steam server from its id.
     * @param server_steam_id the id of the steam server.
     * @return A List of players' steamIDs currently playing on this server.
     */
    public RealtimeStats getPlayersFromServer(String server_steam_id) {

        URI endpoint = URI.create(getEndpoint(server_steam_id));
        HttpRequest request = HttpRequest.newBuilder().uri(endpoint).build();
        HttpResponse response = null;
        RealtimeStats responseStats = null;

        // Have to poll multiple times because endpoint can be unreliable.
        for (int i = 0; i < 15; i++) {
            System.out.println("Polling API");
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) break;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            responseStats = objectMapper.readValue(response.body().toString(), RealtimeStats.class);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return responseStats;
    }

    /**
     * Method returns the endpoint for live stats from a steam server.
     * @param server_steam_id The server_id for which stats will be gathered.
     * @return A string representation of the entire URI.
     */
    public String getEndpoint(String server_steam_id) {
        return "https://api.steampowered.com/IDOTA2MatchStats_570/GetRealtimeStats/V001??"
                + "key=" + Config.API_KEY
                + "&server_steam_id=" + server_steam_id;
    }
}
