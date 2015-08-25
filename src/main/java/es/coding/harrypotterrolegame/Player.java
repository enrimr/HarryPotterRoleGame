package es.coding.harrypotterrolegame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Enri on 24/8/15.
 */
public class Player{
    //atributos de la clase jugador
    private String name;
    private int sex; 	// 0=chico ^ 1=chica
    private int house; 	// Casa a la que pertenece:	0 Gryffindor
    //							1 Slytherin
    //							2 Hufflepuf
    //							3 Ravenclaw
    public int level; //nivel
    public int experience; //experiencia
    public int health;//vida
    public ImageIcon file;
    //matriz de imagenes de todos los personajes que hay en el juego
    private String [] imagenes= {"./imagenes/jugadores/gryffindor_tio.gif",
            "./imagenes/jugadores/gryffindor_tia.gif",
            "./imagenes/jugadores/slytherin_tio.gif",
            "./imagenes/jugadores/slytherin_tia.gif",
            "./imagenes/jugadores/hufflepuff_tio.gif",
            "./imagenes/jugadores/hufflepuff_tia.gif",
            "./imagenes/jugadores/ravenclaw_tio.gif",
            "./imagenes/jugadores/ravenclaw_tia.gif",
            "./imagenes/jugadores/harrypotter.gif",
            "./imagenes/jugadores/snape.gif",
            "./imagenes/jugadores/nagini.gif",//criatura
            "./imagenes/jugadores/mcgonagall.gif", //Mc
            "./imagenes/jugadores/Sprout.gif",
            "./imagenes/jugadores/flit.gif",
            "./imagenes/jugadores/lucius.gif",
            "./imagenes/jugadores/bellatrix.gif",
            "./imagenes/jugadores/colagusano.gif",
            "./imagenes/jugadores/umbrig.gif",
            "./imagenes/jugadores/dumbler.gif",
            "./imagenes/jugadores/volde.gif",
            "./imagenes/jugadores/lloron.gif",
            "./imagenes/jugadores/trol.gif"};

    private int imagen; //valor de la matriz donde esta su imagen
    private int [] objetos= new int[4]; //objetos que posee 0 Varita
    //					1 Pociones
    //					2 Veneno
    //					3 Maldicion
    private int bando; //0 bueno - 1 malo
    boolean fighting=false; //indica si esta combatiendo
    boolean fighting_Criatura=false; //indica si el combate es contra una criatura
    boolean fighting_Profesor=false; //indica si esta hablando con un profesor
    String rival="";  //nombre de su rival si esta combatiendo
    int rivalID; 	//posiocion en el vector de players si esta combatiendo
    int pregunta; 	//pregunta que se le ha formulado

    int [] pos={0,7,7}; //posicion (mapa, x y)

    // Constructor, asigna nombre, posicion e imagen
    public Player(String pName, int mundo, int x, int y, int image)
    {
        name=pName;
        pos[0]= mundo;
        pos[1]= x;
        pos[2]= y;
        imagen = image;
        file = new ImageIcon(imagenes[imagen]);
    }
    //Constructor que asigna todas las variables, usado cuando la base de datos le envia sus datos al empezar
    public Player(String pName, int mundo, int x, int y, int image, int casa, int sexo, int band, int vida, int nivel, int obj1,int obj2, int obj3, int obj4, int exp){

        name=pName;
        experience= exp;
        pos[0]= mundo;
        pos[1]= x;
        pos[2]= y;
        imagen = image;
        file = new ImageIcon(imagenes[imagen]);
        bando=band;
        house = casa;
        sex = sexo;
        level = nivel;
        health = vida;
        objetos[0]=obj1;
        objetos[1]=obj2;
        objetos[2]=obj3;
        objetos[3]=obj4;
    }
    //borra al personaje de su posicion debido a que se habra movido y pinta de nuevo la celda
    public void RecoverLayer(Graphics g){
        mundito[pos[0]].mapita[pos[1]][pos[2]].DrawCell(g, x+pos[1]*mapaTam[0]/numCeldas[0], y+pos[2]*mapaTam[1]/numCeldas[1]);
        mundito[pos[0]].mapita[pos[1]][pos[2]-1].DrawCell(g, x+pos[1]*mapaTam[0]/numCeldas[0], y+(pos[2]-1)*mapaTam[1]/numCeldas[1]);
    }
    //funcion que pinta la imagen del jugador en el mapa con paramtros por defecto
    public void DrawPlayer(Graphics g){
        Image img = file.getImage();
        int widthImage = file.getIconWidth();
        int heightImage = file.getIconHeight();
        g.drawImage(img,x+pos[1]*mapaTam[0]/numCeldas[0],y+pos[2]*mapaTam[1]/numCeldas[1]-(heightImage/escala-mapaTam[1]/numCeldas[1]),widthImage/escala,heightImage/escala,file.getImageObserver());
    }
    //funcion que pinta la imagen del jugador en el mapa con paramtros pasados por el usuario usado para dibujar los player en el combate
    public void DrawPlayer(Graphics g,int esc,int x1,int y1){
        Image img = file.getImage();
        int widthImage = file.getIconWidth();
        int heightImage = file.getIconHeight();
        g.drawImage(img,x+x1,y+y1,widthImage/esc,heightImage/esc,file.getImageObserver());
    }
    //muestra el mapa y la posicion del jugador
    public void DrawPlayerPos(){
        MapNum.setText("Mapa " + pos[0]);
        MapCoor.setText("("+pos[1]+","+pos[2]+")");
    }
}
