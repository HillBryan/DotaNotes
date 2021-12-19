package API_Interactions;

import com.codedisaster.steamworks.SteamID;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.*;

public class SpectatorAPI {

    HttpClient client;

    public SpectatorAPI() {
        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public String requestSteamServerID(SteamID steamID) {

        String steam64 = getSteamID64(steamID);
        HttpResponse response = null;

        try {
            URI uri = URI.create("http://localhost:3000/livematch/" + steam64);
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.body().toString();

    }

    public String getSteamID64(SteamID steamID) {
        String idString = steamID.toString();
        Long idLong = Long.parseLong(idString, 16);
        return idLong.toString();
    }
}
