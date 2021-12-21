package ResponseObjects;

import java.util.List;

public class RealtimeStats {

    private Match match;
    private List<Team> teams;
    private List<Building> buildings;
    private GraphData graph_data;
    private boolean delta_frame;

    public RealtimeStats() {

    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public GraphData getGraph_data() {
        return graph_data;
    }

    public void setGraph_data(GraphData graph_data) {
        this.graph_data = graph_data;
    }

    public boolean isDelta_frame() {
        return delta_frame;
    }

    public void setDelta_frame(boolean delta_frame) {
        this.delta_frame = delta_frame;
    }
}
