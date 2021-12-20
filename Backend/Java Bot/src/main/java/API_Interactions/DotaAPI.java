package API_Interactions;

import com.codedisaster.steamworks.SteamID;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DotaAPI extends BaseAPI{

    // TODO: Move to config file.
    private final String API_KEY = "0F87F835643DA4DA3744229F7918527F";

    public DotaAPI() {

    }

    /**
     * Method will return live stats from a steam server from its id.
     * @param server_steam_id the id of the steam server.
     * @return A List of players' steamIDs currently playing on this server.
     */
    public List<PlayerInfo> getPlayersFromServer(String server_steam_id) {

        System.out.println("Received server_steam_id: " + server_steam_id);

        URI endpoint = URI.create(getEndpoint(server_steam_id));
        HttpRequest request = HttpRequest.newBuilder().uri(endpoint).build();
        HttpResponse response = null;

        // Have to poll multiple times because endpoint can be unreliable.
        while (response == null || response.statusCode() != 200) {

            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<PlayerInfo> players = getPlayersFromBody(response.body().toString());

        System.out.println("Sending Back Players: " + printPlayers(players));
        return players;
    }

    /**
     * Method returns the endpoint for live stats from a steam server.
     * @param server_steam_id The server_id for which stats will be gathered.
     * @return A string representation of the entire URI.
     */
    public String getEndpoint(String server_steam_id) {
        return "https://api.steampowered.com/IDOTA2MatchStats_570/GetRealtimeStats/V001??"
                + "key=" + API_KEY
                + "&server_steam_id=" + server_steam_id;
    }

    /**
     * Method parses JSON response for accountIDs.
     * @param body The response body.
     * @return A list of SteamIDs (64 bit version).
     */
    public List<PlayerInfo> getPlayersFromBody(String body) {

        // Parsing body.
        List<PlayerInfo> players = new ArrayList<>();
        String[] lines = body.split("\n");

        // Going through each line for account_ids.
        // TODO: Speed this up!.

        SteamID temp = null;
        for (String line : lines) {
            if (line.contains("accountid")) {
                String id = line.substring(12, line.length() - 1);
                Long long_id = Long.parseLong(id) + 76561197960265728L;
                temp = SteamID.createFromNativeHandle(long_id);
            }
            if (line.contains("\"name\":")) {
                String personaName = line.substring(8, line.length() - 2);
                players.add(new PlayerInfo(temp, personaName));
            }
        }

        return players;
    }

    public void printPlayers(List<PlayerInfo> players) {
        System.out.print("Players: ");
        for (PlayerInfo info : players) {
            System.out.print(info.personaName + ",");
        }
        System.out.println();
    }

    public class PlayerInfo {
        public SteamID steamID;
        public String personaName;

        public PlayerInfo(SteamID steamID, String personaName) {
            this.steamID = steamID;
            this.personaName = personaName;
        }

    }
}
