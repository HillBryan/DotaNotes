package API_Interactions;

import ResponseObjects.RealtimeStats;
import com.codedisaster.steamworks.SteamID;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DatabaseAPI extends BaseAPI{

    public DatabaseAPI() {

    }

    /**
     * Method will create POST request to the /link/add endpoint.
     * @param stats The stats object for the game.
     * @param steamID The steamID of the user.
     * @param link The generated link for the user to connect to the frontend.
     * @return True if sucessfull, false if otherwise.
     */
    public boolean addLink(RealtimeStats stats, SteamID steamID, String link) {

        String steamID64 = getSteamID64(steamID);
        HttpPost httpPost = new HttpPost("http://localhost:8080/rest/link/add");
        CloseableHttpClient client = HttpClients.createDefault();

        if (stats == null) {
            System.out.println("Stats was null");
            return false;
        }


        try {
            String statsJson = objectMapper.writeValueAsString(stats);
            statsJson = statsJson.replaceAll("\"", "\\\\\"");

            String json = "{" +
                    "\"match_details\": \"" + statsJson + "\"," +
                    "\"steam_id\": \"" + steamID64 + "\"," +
                    "\"hash_key\": \"" + link +
                    "\"}";

            System.out.println(json);

            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = client.execute(httpPost);
            client.close();

            System.out.println(response.getStatusLine().getStatusCode());
            return response.getStatusLine().getStatusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
