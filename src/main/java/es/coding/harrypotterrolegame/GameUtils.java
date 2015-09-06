package es.coding.harrypotterrolegame;

/**
 * Created by Enri on 1/9/15.
 */
public class GameUtils {

    static String [] suelohogwarts1 = {"./images/floor/hogwarts000.gif",
            "./images/floor/hogwarts001.gif",
            "./images/floor/hogwarts002.gif"};
    static String [] suelohogwarts2 = {"./images/floor/hogwarts011.gif",
            "./images/floor/hogwarts012.gif",
            "./images/floor/hogwarts010.gif"};
    static String [] suelobosqueverde = {"./images/floor/bosque1.gif",
            "./images/floor/bosque2.gif"};
    static String [] suelobosquemarron = {"./images/floor/tierra1.gif",
            "./images/floor/tierra2.gif",
            "./images/floor/tierra3.gif"};
    static String [] suelotenebrosa1 = {"./images/floor/tenebrosa000.gif",
            "./images/floor/tenebrosa001.gif",
            "./images/floor/tenebrosa002.gif"};
    static String [] suelotenebrosa2 = {"./images/floor/tenebrosa011.gif",
            "./images/floor/tenebrosa012.gif",
            "./images/floor/tenebrosa010.gif"};

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

    public static Map [] initMaps(){

        //Declaracion e inicializacion de los distintos escenarios

        int sizeWidth = Integer.parseInt(Configuration.getInstance().getConfig("map.sizel.width"));
        int sizeHeight = Integer.parseInt(Configuration.getInstance().getConfig("map.size.height"));
        int cellWidth = Integer.parseInt(Configuration.getInstance().getConfig("map.cell.width"));
        int cellHeight = Integer.parseInt(Configuration.getInstance().getConfig("map.cell.height"));

        Map map1 = new Map(
                sizeWidth,
                sizeHeight,
                cellWidth,
                cellHeight);
        Map map2 = new Map(
                sizeWidth,
                sizeHeight,
                cellWidth,
                cellHeight);
        Map mapbosque = new Map(
                sizeWidth,
                sizeHeight,
                cellWidth,
                cellHeight);
        Map mapbosquecamino = new Map(
                sizeWidth,
                sizeHeight,
                cellWidth,
                cellHeight);

        int control=0;
        for (int i=0;i<cellWidth;i++){
            for(int j=0;j<cellHeight;j++){
                if (i%2==0)
                    map1.matrix[j][i] = new MapCell(suelohogwarts1[control],false);
                else
                    map1.matrix[j][i] = new MapCell(suelohogwarts2[control],false);
                if (control==2) control=0;
                else control++;
            }
        }

        for (int i=0;i<cellWidth;i++){
            for(int j=0;j<cellHeight;j++){
                mapbosque.matrix[j][i] = new MapCell(suelobosqueverde[0],false);
                mapbosquecamino.matrix[j][i] = new MapCell(suelobosqueverde[1],false);
            }
        }
        //preguntar por enrik
        mapbosquecamino.matrix[0][7] = new MapCell(suelobosquemarron[1],false);
        mapbosquecamino.matrix[0][8] = new MapCell(suelobosquemarron[0],false);
        mapbosquecamino.matrix[1][7] = new MapCell(suelobosquemarron[0],false);
        mapbosquecamino.matrix[1][8] = new MapCell(suelobosquemarron[0],false);
        mapbosquecamino.matrix[2][7] = new MapCell(suelobosquemarron[1],false);
        mapbosquecamino.matrix[2][8] = new MapCell(suelobosquemarron[1],false);
        mapbosquecamino.matrix[3][7] = new MapCell(suelobosquemarron[2],false);
        mapbosquecamino.matrix[3][8] = new MapCell(suelobosquemarron[2],false);
        mapbosquecamino.matrix[4][8] = new MapCell(suelobosquemarron[2],false);
        mapbosquecamino.matrix[4][9] = new MapCell(suelobosquemarron[1],false);
        mapbosquecamino.matrix[5][10] = new MapCell(suelobosquemarron[2],false);
        mapbosquecamino.matrix[5][9] = new MapCell(suelobosquemarron[1],false);
        mapbosquecamino.matrix[6][10] = new MapCell(suelobosquemarron[2],false);
        mapbosquecamino.matrix[6][9] = new MapCell(suelobosquemarron[1],false);
        mapbosquecamino.matrix[7][10] = new MapCell(suelobosquemarron[2],false);
        mapbosquecamino.matrix[7][9] = new MapCell(suelobosquemarron[2],false);
        mapbosquecamino.matrix[8][9] = new MapCell(suelobosquemarron[1],false);
        mapbosquecamino.matrix[8][10] = new MapCell(suelobosquemarron[0],false);
        mapbosquecamino.matrix[9][10] = new MapCell(suelobosquemarron[0],false);
        mapbosquecamino.matrix[10][10] = new MapCell(suelobosquemarron[0],false);
        mapbosquecamino.matrix[11][10] = new MapCell(suelobosquemarron[0],false);
        mapbosquecamino.matrix[12][9] = new MapCell(suelobosquemarron[0],false);
        mapbosquecamino.matrix[12][10] = new MapCell(suelobosquemarron[0],false);
        mapbosquecamino.matrix[13][10] = new MapCell(suelobosquemarron[2],false);
        mapbosquecamino.matrix[13][11] = new MapCell(suelobosquemarron[2],false);
        mapbosquecamino.matrix[14][10] = new MapCell(suelobosquemarron[0],false);
        mapbosquecamino.matrix[14][11] = new MapCell(suelobosquemarron[1],false);

        control=0;
        for (int i=0;i<cellWidth;i++){
            for(int j=0;j<cellHeight;j++){
                if (i%2==0)
                    map2.matrix[j][i] = new MapCell(suelotenebrosa1[control],false);
                else
                    map2.matrix[j][i] = new MapCell(suelotenebrosa2[control],false);
                if (control==2) control=0;
                else control++;
            }
        }

        Map [] myWorld = new Map[9];
        // Pintamos Hogwarts
        myWorld[0]=map1;
        myWorld[3]=map1;
        myWorld[6]=map1;

        // Pintamos el bosque de en medio
        myWorld[1]=mapbosque;
        myWorld[4]=mapbosquecamino;
        myWorld[7]=mapbosque;

        // Pintamos la Ciudad Tenebrosa
        myWorld[2]=map2;
        myWorld[5]=map2;
        myWorld[8]=map2;

        return myWorld;
    }
}