package es.coding.harrypotterrolegame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Enri on 24/8/15.
 */
public class MapCell {
    //clase celda del mapa
    // Atributos de la clase
    private ImageIcon imageSprite;		// Indica el fichero del gráfico de la casilla
    private boolean item=false;			// Indica si hay un objeto
    private int itemCode =0;				// Indica el código del objeto
    private ImageIcon itemSprite;			// Indica el fichero del gráfico del objeto
    public boolean isBlocked=false;	// Indica si se puede o no pisar encima

    // Constructor: file es una string que indica la ruta de la iamgen de la celda,
    //				blocked es un booleano que indica si está bloqueado(true)
    //						o si se puede pasar(false)
    public MapCell(String file, boolean blocked){
        imageSprite = new ImageIcon(file);
        isBlocked = blocked;
    }
    // DrawCell: dibuja la celda del mapa. Hay que indicar un Graphics y
    //			la posición x,y donde dibujarla
    //			escala indica entre cuanto dividimos el tamaño de la imagen
    public void drawCell(Graphics g, int x, int y){
        Image img = imageSprite.getImage();
        int widthImage = imageSprite.getIconWidth();
        int heightImage = imageSprite.getIconHeight();
        g.drawImage(img,
                x,
                y,
                widthImage / Integer.valueOf(Configuration.getInstance().getConfig("scale")),
                heightImage / Integer.valueOf(Configuration.getInstance().getConfig("scale")),
                imageSprite.getImageObserver());

        // Si hay objeto, se dibuja también
        if (item){
            Image img2 = itemSprite.getImage();
            int widthItem = itemSprite.getIconWidth();
            int heightItem = itemSprite.getIconHeight();
            g.drawImage(img2,
                    x,
                    y,
                    widthItem / Integer.valueOf(Configuration.getInstance().getConfig("scale")),
                    heightItem / Integer.valueOf(Configuration.getInstance().getConfig("scale")),
                    itemSprite.getImageObserver());
        }
    }

    // addItem: añade un objeto a la celda. Devuelve true si se añadió correctamente,
    //			o false si no se pudo añadir porque ya había uno anterior.
    public boolean addItem(int code, String file){
        if (item)
            return false;
        else {
            item = true;
            itemCode = code;
            itemSprite = new ImageIcon(file);
            return true;
        }
    }

    // ClearItem: elimina el objeto de la celda
    public void ClearItem(){
        item = false;
        itemCode = 0;
    }
}
