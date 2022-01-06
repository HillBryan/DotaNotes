package API_Interactions;

import com.codedisaster.steamworks.SteamID;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.time.Duration;

/**
 * @Author Bryan Hill
 */
public abstract class BaseAPI {

    protected HttpClient client;
    protected ObjectMapper objectMapper;

    public BaseAPI() {
        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        objectMapper = new ObjectMapper();
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
