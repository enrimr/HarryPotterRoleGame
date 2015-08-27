package es.coding.harrypotterrolegame;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Session;
import javax.naming.Context;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by Enri on 25/8/15.
 */
public class Configuration {

     // Paso de mensajes
     Hashtable properties;
     Context context;
     ConnectionFactory factory;
     javax.jms.Connection connection;
     Session session;
     Destination destination;

     //matriz de imagenes de todos los personajes que hay en el juego
     String [] playerSprites= {
            "./images/player/gryffindor_tio.gif",
            "./images/player/gryffindor_tia.gif",
            "./images/player/slytherin_tio.gif",
            "./images/player/slytherin_tia.gif",
            "./images/player/hufflepuff_tio.gif",
            "./images/player/hufflepuff_tia.gif",
            "./images/player/ravenclaw_tio.gif",
            "./images/player/ravenclaw_tia.gif",
            "./images/player/harrypotter.gif",
            "./images/player/snape.gif",
            "./images/player/nagini.gif",//criatura
            "./images/player/mcgonagall.gif", //Mc
            "./images/player/Sprout.gif",
            "./images/player/flit.gif",
            "./images/player/lucius.gif",
            "./images/player/bellatrix.gif",
            "./images/player/colagusano.gif",
            "./images/player/umbrig.gif",
            "./images/player/dumbler.gif",
            "./images/player/volde.gif",
            "./images/player/lloron.gif",
            "./images/player/trol.gif"};

    Properties config = new Properties();

    private static Configuration ourInstance = new Configuration();

    public static Configuration getInstance() {
        return ourInstance;
    }

    private Configuration() {

        this.config = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            // load a properties file
            this.config.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Returns the value for a property key
    public String getConfig(String key){
        return this.config.getProperty(key);
    }
}
