package API_Interactions;

import com.codedisaster.steamworks.SteamID;
import org.restlet.Response;
import org.restlet.resource.ClientResource;

import java.io.IOException;

public class SpectatorAPI {

    public SpectatorAPI() {

    }

    public String requestSteamServerID(SteamID steamID) {

        String steam64 = getSteamID64(steamID);
        ClientResource resource = new ClientResource("http://localhost:3000/livematch/" + steam64);

        try {
            Response response = resource.getResponse();

            if (response.getStatus().isSuccess()) {
                System.out.println("Success! " + response.getStatus());
                return (response.getEntity().getText());
            } else {
                System.out.println("ERROR! " + response.getStatus());
                throw new IOException("Could not query spectator api.");
            }
        } catch (Exception e) {
            System.out.println("Error connecting to API!");
        }

        // If one gets here then there is an error.
        return "ERROR";
    }

    public String getSteamID64(SteamID steamID) {
        String idString = steamID.toString();
        Long idLong = Long.parseLong(idString, 16);
        return idLong.toString();
    }
}
