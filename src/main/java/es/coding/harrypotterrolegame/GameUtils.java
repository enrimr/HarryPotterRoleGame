package es.coding.harrypotterrolegame;

/**
 * Created by Enri on 1/9/15.
 */
public class GameUtils {

    public static Player [] initCreatures(){

        Player [] creatures = new Player[10];
        creatures[0]=new Player("lloron", 1, 7, 7, 20, -1, 1, 1, 50, 1, 0, 0, 0, 0, 15);
        creatures[1]=new Player("lloron", 4, 5, 5, 20, -1, 1, 1, 50, 2, 0, 0, 0, 0, 2*5);
        creatures[2]=new Player("lloron", 7, 12, 12, 20, -1, 1, 1, 50, 3, 0, 0, 0, 0, 3*5);
        creatures[3]=new Player("lloron", 8, 10, 10, 20, -1, 1, 1, 50, 4, 0, 0, 0, 0, 4*5);
        creatures[4]=new Player("lloron", 4, 10, 10, 20, -1, 1, 1, 50, 5, 0, 0, 0, 0, 5*5);

        creatures[5]=new Player("trol", 0, 10, 10, 21, -1, -1, 1, 250, 5*2, 0, 0, 0, 0, 5*15);
        creatures[6]=new Player("trol", 2, 5, 5, 21, -1, -1, 1, 250, 6*2, 0, 0, 0, 0, 6*15);
        creatures[7]=new Player("trol", 6, 10, 10, 21, -1, -1, 1, 250, 7*2, 0, 0, 0, 0, 7*15);
        creatures[8]=new Player("trol", 8, 5, 5, 21, -1, -1, 1, 250, 8*2, 0, 0, 0, 0, 8*15);

        creatures[9]=new Player("nagini", 7, 7, 7, 10, 1, 0, 1, 550, 50, 0, 0, 0, 0, 9*20);

        return creatures;

    }

    public static Player [] initTeachers(){

        Player [] teachers = new Player[10];
        teachers[0]=new Player("McGonagal", 0, 7, 5, 11, 0, 1, 0, 500, 50, 0, 1, 1, 0, 1000);
        teachers[1]=new Player("Snape", 0, 7, 10, 9, 1, 0, 0, 500, 50, 0, 1, 1, 0, 1000);
        teachers[2]=new Player("Sprout", 6, 7, 5, 12, 2, 1, 0, 500, 50, 0, 1, 1, 0, 1000);
        teachers[3]=new Player("Flitwich", 6, 7, 10, 13, 3, 0, 0, 500, 50, 0, 1, 1, 0, 1000);

        teachers[4]=new Player("Lucius", 2, 7, 5, 14, 0, 0, 1, 500, 50, 0, 1, 1, 0, 1000);
        teachers[5]=new Player("Bellatrix", 2, 7, 10, 15, 1, 1, 1, 500, 50, 0, 1, 1, 0, 1000);
        teachers[6]=new Player("Colagusano", 8, 7, 5, 16, 2, 0, 1, 500, 50, 0, 1, 1, 0, 1000);
        teachers[7]=new Player("Umbrigde", 8, 7, 10, 17, 3, 1, 1, 500, 50, 0, 1, 1, 0, 1000);

        teachers[8]=new Player("Dumbledore", 3, 7, 13, 18, -1, 0, 0, 1000, 100, 0, 0, 0, 1, 10000);
        teachers[9]=new Player("Voldemort",  5, 7, 2, 19, -1, 0, 1, 1000, 100, 0, 0, 0, 1, 10000);

        return teachers;
    }
}