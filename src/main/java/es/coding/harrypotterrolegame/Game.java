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
        this.world.setTeachers(GameUtils.initTeachers());
        this.world.setCreatures(GameUtils.initCreatures());
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(Player myPlayer) {
        this.myPlayer = myPlayer;
    }

    public Player[] getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Player[] gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public int getGamePlayersLength() {
        return gamePlayersLength;
    }

    public void setGamePlayersLength(int gamePlayersLength) {
        this.gamePlayersLength = gamePlayersLength;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
