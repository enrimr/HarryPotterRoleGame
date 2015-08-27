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
     String [] playerSprites= {"./imagenes/jugadores/gryffindor_tio.gif",
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
