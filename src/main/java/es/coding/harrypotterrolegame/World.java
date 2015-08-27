package es.coding.harrypotterrolegame;

import java.util.List;

/**
 * Created by Enri on 26/8/15.
 */
public class World {

    int actualMap;// = 0;
    Map[] maps;// = new Map[9];
    int mapsCount;// = 9;

    private static World ourInstance = new World();

    public static World getInstance() {
        return ourInstance;
    }

    private World() {
        this.actualMap = 0;
        this.mapsCount = 9;
        this.maps = new Map[mapsCount];
    }
}
