package es.coding.harrypotterrolegame;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Session;
import javax.naming.Context;
import java.io.File;
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
            "./src/main/resources/images/player/gryffindor_tio.gif",
            "./src/main/resources/images/player/gryffindor_tia.gif",
            "./src/main/resources/images/player/slytherin_tio.gif",
            "./src/main/resources/images/player/slytherin_tia.gif",
            "./src/main/resources/images/player/hufflepuff_tio.gif",
            "./src/main/resources/images/player/hufflepuff_tia.gif",
            "./src/main/resources/images/player/ravenclaw_tio.gif",
            "./src/main/resources/images/player/ravenclaw_tia.gif",
            "./src/main/resources/images/player/harrypotter.gif",
            "./src/main/resources/images/player/snape.gif",
            "./src/main/resources/images/player/nagini.gif",//criatura
            "./src/main/resources/images/player/mcgonagall.gif", //Mc
            "./src/main/resources/images/player/Sprout.gif",
            "./src/main/resources/images/player/flit.gif",
            "./src/main/resources/images/player/lucius.gif",
            "./src/main/resources/images/player/bellatrix.gif",
            "./src/main/resources/images/player/colagusano.gif",
            "./src/main/resources/images/player/umbrig.gif",
            "./src/main/resources/images/player/dumbler.gif",
            "./src/main/resources/images/player/volde.gif",
            "./src/main/resources/images/player/lloron.gif",
            "./src/main/resources/images/player/trol.gif"};

    Properties config = new Properties();

    private static Configuration ourInstance = new Configuration();

    public static Configuration getInstance() {
        return ourInstance;
    }

    private Configuration() {

        this.config = new Properties();
        InputStream input = null;
        try {
            //System.out.println("File path: " + new File("Your file name").getAbsolutePath());
            input = new FileInputStream("./src/main/config/config.properties");
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
