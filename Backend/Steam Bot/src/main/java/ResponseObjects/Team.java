package ResponseObjects;

import java.util.List;

public class Team {

    private int team_number;
    private int team_id;
    private String team_name;
    private String team_tag;
    private String team_logo;
    private int score;
    private int net_worth;
    private String team_logo_url;
    private List<Player> players;

    public Team() {}

    public int getTeam_number() {
        return team_number;
    }

    public void setTeam_number(int team_number) {
        this.team_number = team_number;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_tag() {
        return team_tag;
    }

    public void setTeam_tag(String team_tag) {
        this.team_tag = team_tag;
    }

    public String getTeam_logo() {
        return team_logo;
    }

    public void setTeam_logo(String team_logo) {
        this.team_logo = team_logo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNet_worth() {
        return net_worth;
    }

    public void setNet_worth(int net_worth) {
        this.net_worth = net_worth;
    }

    public String getTeam_logo_url() {
        return team_logo_url;
    }

    public void setTeam_logo_url(String team_logo_url) {
        this.team_logo_url = team_logo_url;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
