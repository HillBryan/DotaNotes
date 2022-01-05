package ResponseObjects;

public class Match {

    private String server_steam_id;
    private String match_id;
    private int timestamp;
    private int game_time;
    private int game_mode;
    private int league_id;
    private int league_node_id;
    private int game_state;

    public Match() {}

    public String getServer_steam_id() {
        return server_steam_id;
    }

    public void setServer_steam_id(String server_steam_id) {
        this.server_steam_id = server_steam_id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getGame_time() {
        return game_time;
    }

    public void setGame_time(int game_time) {
        this.game_time = game_time;
    }

    public int getGame_mode() {
        return game_mode;
    }

    public void setGame_mode(int game_mode) {
        this.game_mode = game_mode;
    }

    public int getLeague_id() {
        return league_id;
    }

    public void setLeague_id(int league_id) {
        this.league_id = league_id;
    }

    public int getLeague_node_id() {
        return league_node_id;
    }

    public void setLeague_node_id(int league_node_id) {
        this.league_node_id = league_node_id;
    }

    public int getGame_state() {
        return game_state;
    }

    public void setGame_state(int game_state) {
        this.game_state = game_state;
    }
}
