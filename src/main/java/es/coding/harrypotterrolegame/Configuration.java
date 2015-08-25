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

    Properties config = new Properties();

    private static Configuration ourInstance = new Configuration();

    public static Configuration getInstance() {
        return ourInstance;
    }

    public Configuration() {

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
