package ResponseObjects;

import java.util.List;

public class Player {

    private int accountid;
    private int playerid;
    private String name;
    private int team;
    private int heroid;
    private int level;
    private int kill_count;
    private int death_count;
    private int assists_count;
    private int denies_count;
    private int lh_count;
    private int gold;
    private double x;
    private double y;
    private int net_worth;
    private List<Integer> abilities;
    private List<Integer> items;

    public Player() {

    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public int getPlayerid() {
        return playerid;
    }

    public void setPlayerid(int playerid) {
        this.playerid = playerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getHeroid() {
        return heroid;
    }

    public void setHeroid(int heroid) {
        this.heroid = heroid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getKill_count() {
        return kill_count;
    }

    public void setKill_count(int kill_count) {
        this.kill_count = kill_count;
    }

    public int getDeath_count() {
        return death_count;
    }

    public void setDeath_count(int death_count) {
        this.death_count = death_count;
    }

    public int getAssists_count() {
        return assists_count;
    }

    public void setAssists_count(int assists_count) {
        this.assists_count = assists_count;
    }

    public int getDenies_count() {
        return denies_count;
    }

    public void setDenies_count(int denies_count) {
        this.denies_count = denies_count;
    }

    public int getLh_count() {
        return lh_count;
    }

    public void setLh_count(int lh_count) {
        this.lh_count = lh_count;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getNet_worth() {
        return net_worth;
    }

    public void setNet_worth(int net_worth) {
        this.net_worth = net_worth;
    }

    public List<Integer> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Integer> abilities) {
        this.abilities = abilities;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }
}
