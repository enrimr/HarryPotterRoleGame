package es.coding.harrypotterrolegame;

import java.awt.*;

/**
 * Created by Enri on 24/8/15.
 */
public class Maps {

    public MapCell [][] mapita;  //atributo matriz del mapa
    private int numCellx=numCeldas[0]; //numero de celdas en x del mapa
    private int numCelly=numCeldas[1]; //numero de celdas en x del mapa

    //constructor que inicializa el atributo al tamaño por defecto
    public Maps(int tamx,int tamy){
        mapita = new MapCell[numCellx][numCelly];
    }
    //constructor que inicializa el atributo con un tamaño proporcionado
    public Maps(int tamx,int tamy,int numCellxx,int numCellyy){
        mapita = new MapCell[numCellxx][numCellyy];
        numCellx = numCellxx;
        numCelly = numCellyy;
    }
    //Inicializa los mapas con una unica imagen
    public void InitRandom(){
        for (int i=0;i<numCellx;i++){
            for(int j=0;j<numCelly;j++){
                mapita[i][j] = new MapCell("./imagenes/suelo/hogwarts000.gif",false);
            }
        }
    }
    //funcion que dibuja el mapa
    public void DrawMap(Graphics g,int x, int y){
        // Pintamos el Rectangulo que delimita la superficie
        //g.drawRect(x, y, mapaTam[0], mapaTam[1]);
        for (int i=0;i<numCeldas[0];i++){
            for (int j=0;j<numCeldas[1];j++){
                mapita[i][j].DrawCell(g, x+i*mapaTam[0]/numCeldas[0], y+j*mapaTam[1]/numCeldas[1]);
            }
        }
        g.drawRect(x, y, mapaTam[0], mapaTam[1]);
    }
}
