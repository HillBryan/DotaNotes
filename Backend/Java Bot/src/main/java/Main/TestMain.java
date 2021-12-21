package Main;

import API_Interactions.DotaAPI;
import ResponseObjects.Player;
import ResponseObjects.RealtimeStats;
import ResponseObjects.Team;

public class TestMain {

    public static void main(String[] args) {

        DotaAPI api = new DotaAPI();

        api.getPlayersFromServer("90154310707773451");

    }
}
