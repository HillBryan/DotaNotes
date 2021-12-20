package API_Interactions;

import com.codedisaster.steamworks.SteamID;
import java.net.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpectatorAPI extends BaseAPI {

    public SpectatorAPI() {

    }

    /**
     * Method will return the server_steam_id of the game that the steamID is currently in.
     * @param steamID Of the player in game.
     * @return A string representation of the server_steam_id.
     */
    public String requestSteamServerID(SteamID steamID) {
        String steam64 = getSteamID64(steamID);
        HttpResponse response = null;

        System.out.println("Received SteamID: " + steamID)

        try {
            URI uri = URI.create("http://localhost:3000/livematch/" + steam64);
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Sending back server_steam_id: " + response.body().toString());

        return response.body().toString();
    }

}
