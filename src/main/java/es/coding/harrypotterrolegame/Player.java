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

    private int sprite; //valor de la matriz donde esta su imagen
    private int [] items= new int[4]; //objetos que posee
    //                  0 Varita
    //					1 Pociones
    //					2 Veneno
    //					3 Maldicion
    
    private int faction; //0 bueno - 1 malo
    boolean isFighting =false; //indica si esta combatiendo
    boolean isFightingVersusCriature =false; //indica si el combate es contra una criatura
    boolean isFightingVersusProfessor =false; //indica si esta hablando con un profesor
    String enemy ="";  //nombre de su enemy si esta combatiendo
    int enemyId; 	//posiocion en el vector de players si esta combatiendo
    int pregunta; 	//pregunta que se le ha formulado

    int [] pos={0,7,7}; //posicion (mapa, x y)

    // Constructor, asigna nombre, posicion e imagen
    public Player(String pName, int mundo, int x, int y, int sprite)
    {
        this.name=pName;
        this.pos[0]= mundo;
        this.pos[1]= x;
        this.pos[2]= y;
        this.sprite = sprite;
        this.file = new ImageIcon(Configuration.getInstance().playerSprites[sprite]);
    }
    //Constructor que asigna todas las variables, usado cuando la base de datos le envia sus datos al empezar
    public Player(String pName, int mundo, int x, int y, int sprite, int casa, int sexo, int band, int vida, int nivel, int obj1,int obj2, int obj3, int obj4, int exp){

        this.name=pName;
        this.experience= exp;
        this.pos[0]= mundo;
        this.pos[1]= x;
        this.pos[2]= y;
        this.sprite = sprite;
        this.file = new ImageIcon(Configuration.getInstance().playerSprites[sprite]);
        this.faction = band;
        this.house = casa;
        this.sex = sexo;
        this.level = nivel;
        this.health = vida;
        this.items[0]=obj1;
        this.items[1]=obj2;
        this.items[2]=obj3;
        this.items[3]=obj4;
    }
    //borra al personaje de su posicion debido a que se habra movido y pinta de nuevo la celda
    public void recoverLayer(Graphics g){
        Game.getInstance().world.maps[pos[0]].matrix[pos[1]][pos[2]].drawCell(
                g,
                Integer.parseInt(Configuration.getInstance().getConfig("x"))+pos[1]*World.getInstance().getMaps()[pos[0]].getPixelWidth()/World.getInstance().getMaps()[pos[0]].getCellWidth(),
                Integer.parseInt(Configuration.getInstance().getConfig("y"))+pos[2]*World.getInstance().getMaps()[pos[0]].getPixelHeight()/World.getInstance().getMaps()[pos[0]].getCellHeight());
        Game.getInstance().world.maps[pos[0]].matrix[pos[1]][pos[2]-1].drawCell(
                g,
                Integer.parseInt(Configuration.getInstance().getConfig("x"))+pos[1]*World.getInstance().getMaps()[pos[0]].getPixelWidth()/World.getInstance().getMaps()[pos[0]].getCellWidth(),
                Integer.parseInt(Configuration.getInstance().getConfig("y"))+(pos[2]-1)*World.getInstance().getMaps()[pos[0]].getPixelHeight()/World.getInstance().getMaps()[pos[0]].getCellHeight());
    }
    //funcion que pinta la imagen del jugador en el mapa con paramtros por defecto
    public void drawPlayer(Graphics g){
        Image img = file.getImage();
        int widthImage = file.getIconWidth();
        int heightImage = file.getIconHeight();
        g.drawImage(
                img,
                Integer.parseInt(Configuration.getInstance().getConfig("x"))+pos[1]*World.getInstance().getMaps()[pos[0]].getPixelWidth()/World.getInstance().getMaps()[pos[0]].getCellWidth(),
                Integer.parseInt(Configuration.getInstance().getConfig("y"))+pos[2]*World.getInstance().getMaps()[pos[0]].getPixelHeight()/World.getInstance().getMaps()[pos[0]].getCellHeight()-(heightImage/Integer.parseInt(Configuration.getInstance().getConfig("scale"))-World.getInstance().getMaps()[pos[0]].getPixelHeight()/World.getInstance().getMaps()[pos[0]].getCellHeight()),
                widthImage/Integer.parseInt(Configuration.getInstance().getConfig("scale")),
                heightImage/Integer.parseInt(Configuration.getInstance().getConfig("scale")),
                file.getImageObserver());
    }
    //funcion que pinta la imagen del jugador en el mapa con paramtros pasados por el usuario usado para dibujar los player en el combate
    public void drawPlayer(Graphics g,int esc,int x1,int y1){
        Image img = file.getImage();
        int widthImage = file.getIconWidth();
        int heightImage = file.getIconHeight();
        g.drawImage(
                img,
                Integer.parseInt(Configuration.getInstance().getConfig("x"))+x1,
                Integer.parseInt(Configuration.getInstance().getConfig("y"))+y1,
                widthImage/esc,
                heightImage/esc,
                file.getImageObserver());
    }

    public String getName() {
        return name;
    }

    public int getFaction() {
        return faction;
    }

    public void setFaction(int faction) {
        this.faction = faction;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }

    public void setWand(int wand){
        this.items[0] = wand;
    }
    public int getWand(){
        return items[0];
    }

    public void setPotions(int potions){
        items[1] = potions;
    }
    public int getPotions(){
        return items[1];
    }

    public void setPoison(int poison){
        items[2] = poison;
    }
    public int getPoison(){
        return items[2];
    }

    public void setUnforgivableCurses(int unforgivableCurses){
        items[3] = unforgivableCurses;
    }
    public int getUnforgivableCurses(){
        return items[3];
    }
}
