package es.coding.harrypotterrolegame;

import java.util.List;

/**
 * Created by Enri on 26/8/15.
 */
public class World {

    int actualMap;// = 0;
    Map[] maps;// = new Map[9];
    int mapsCount;// = 9;
    Player [] teachers;
    Player [] creatures;

    private static World ourInstance = new World();

    public static World getInstance() {
        return ourInstance;
    }

    private World() {
        this.actualMap = 0;
        this.mapsCount = 9;
        this.maps = new Map[mapsCount];
        this.teachers = new Player[10];
        this.creatures = new Player[10];
    }

    public int getActualMap() {
        return actualMap;
    }

    public void setActualMap(int actualMap) {
        this.actualMap = actualMap;
    }

    public Map[] getMaps() {
        return maps;
    }

    public void setMaps(Map[] maps) {
        this.maps = maps;
    }

    public int getMapsCount() {
        return mapsCount;
    }

    public void setMapsCount(int mapsCount) {
        this.mapsCount = mapsCount;
    }

    public Player[] getTeachers() {
        return teachers;
    }

    public void setTeachers(Player[] teachers) {
        this.teachers = teachers;
    }

    public Player[] getCreatures() {
        return creatures;
    }

    public void setCreatures(Player[] creatures) {
        this.creatures = creatures;
    }
}
