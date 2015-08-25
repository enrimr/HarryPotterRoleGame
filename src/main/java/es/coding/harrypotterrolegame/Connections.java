package es.coding.harrypotterrolegame;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

/**
 * Created by Enri on 25/8/15.
 */
public class Connections {
    private void StartConnection(){
        try{
            properties = new Hashtable();
            properties.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.exolab.jms.jndi.InitialContextFactory");
            properties.put(Context.PROVIDER_URL, "tcp://localhost:3035/");//ip donde se ha lanzado el server

            context = new InitialContext(properties);

            factory = (ConnectionFactory) context.lookup("ConnectionFactory");

            connection = factory.createConnection();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            destination = (Destination) context.lookup("topic1");

            connection.start();

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void SendMessage(int mapa, int x, int y){
        try{
            //funcion que envia un mensaje
            MessageProducer sender = session.createProducer(destination);
            TextMessage message = session.createTextMessage("Posicion: "+x+" "+y);
            sender.send(message);

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void SendMessage(String mensaje){
        try{
            //otra funcion que envia otro tipo de mensajes
            MessageProducer sender = session.createProducer(destination);
            TextMessage message = session.createTextMessage(mensaje);
            sender.send(message);

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
