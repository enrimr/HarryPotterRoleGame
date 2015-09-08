package es.coding.harrypotterrolegame;

import java.awt.*;

/**
 * Created by Enri on 24/8/15.
 */
public class Map {

    public MapCell [][] matrix;  //atributo matriz del mapa
    private int cellWidth; //numero de celdas en x del mapa
    private int cellHeight; //numero de celdas en x del mapa
    private int pixelWidth; //numero de celdas en x del mapa
    private int pixelHeight; //numero de celdas en x del mapa

    //constructor que inicializa el atributo al tamaño por defecto
    public Map(){
        this(Integer.valueOf(Configuration.getInstance().getConfig("map.size.width")),
                Integer.valueOf(Configuration.getInstance().getConfig("map.size.width")),
                Integer.valueOf(Configuration.getInstance().getConfig("map.cell.width")),
                Integer.valueOf(Configuration.getInstance().getConfig("map.cell.height")));
    }

    //constructor que inicializa el atributo con un tamaño proporcionado
    public Map(int pixelWidth, int pixelHeight, int cellWidth, int cellHeight){
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.matrix = initMapCells();
    }

    //Inicializa los mapas con una unica imagen
    private MapCell[][] initMapCells(){
        MapCell[][] cells = new MapCell[this.cellWidth][this.cellHeight];
        for (int i=0;i<this.cellWidth;i++){
            for(int j=0;j<this.cellHeight;j++){
                cells[i][j] = new MapCell("./images/floor/hogwarts000.gif",false);
            }
        }
        return cells;
    }

    //funcion que dibuja el mapa
    public void drawMap(Graphics g, int x, int y){
        // Pintamos el Rectangulo que delimita la superficie
        for (int i=0;i<this.cellWidth;i++){
            for (int j=0;j<this.cellHeight;j++){
                this.matrix[i][j].drawCell(g, x+i*this.pixelWidth/this.cellWidth, y+j*this.pixelHeight/this.cellHeight);
            }
        }
        g.drawRect(x, y, this.pixelWidth, this.pixelHeight);
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public int getPixelWidth() {
        return pixelWidth;
    }

    public int getPixelHeight() {
        return pixelHeight;
    }

}
