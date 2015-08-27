package es.coding.harrypotterrolegame;

/**
 * Created by Enri on 27/8/15.
 */
public class Game {

    Player myPlayer;
    Player[] gamePlayers;
    int gamePlayersLength;      // indica la primera posicion vacia del vector de player
    World world;

    private static Game ourInstance = new Game();

    public static Game getInstance() {
        return ourInstance;
    }

    private Game() {
        this.gamePlayers = new Player[100]; //Vector de otro de jugadores
        this.gamePlayersLength = 0;
        this.world = World.getInstance();
    }

}
