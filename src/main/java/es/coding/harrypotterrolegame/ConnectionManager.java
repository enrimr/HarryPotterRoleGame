package es.coding.harrypotterrolegame;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

/**
 * Created by Enri on 25/8/15.
 */
public class ConnectionManager {

    Configuration config = Configuration.getInstance();

    public void startConnection(){
        try{
            config.properties = new Hashtable();
            config.properties.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.exolab.jms.jndi.InitialContextFactory");
            config.properties.put(Context.PROVIDER_URL, "tcp://localhost:3035/");//ip donde se ha lanzado el server

            config.context = new InitialContext(config.properties);

            config.factory = (ConnectionFactory) config.context.lookup("ConnectionFactory");

            config.connection = config.factory.createConnection();

            config.session = config.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            config.destination = (Destination) config.context.lookup("topic1");

            config.connection.start();

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void sendMessage(int mapa, int x, int y){
        try{
            //funcion que envia un mensaje
            MessageProducer sender = config.session.createProducer(config.destination);
            TextMessage message = config.session.createTextMessage("Posicion: "+x+" "+y);
            sender.send(message);

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void sendMessage(String mensaje){
        try{
            //otra funcion que envia otro tipo de mensajes
            MessageProducer sender = config.session.createProducer(config.destination);
            TextMessage message = config.session.createTextMessage(mensaje);
            sender.send(message);

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
