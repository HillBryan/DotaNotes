package API_Interactions;

import com.codedisaster.steamworks.SteamID;

import java.net.http.HttpClient;
import java.time.Duration;

public abstract class BaseAPI {

    protected HttpClient client;

    public BaseAPI() {
        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    /**
     * Method will convert a SteamID to the 64bit Long representation as a string.
     * @param steamID the SteamID to be converted to a String.
     * @return String representation of the SteamID.
     */
    public String getSteamID64(SteamID steamID) {
        String idString = steamID.toString();
        Long idLong = Long.parseLong(idString, 16);
        return idLong.toString();
    }
}
