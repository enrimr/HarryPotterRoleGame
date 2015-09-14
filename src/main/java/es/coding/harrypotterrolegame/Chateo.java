package es.coding.harrypotterrolegame;

import javax.jms.*;

/**
 * Created by Enri on 25/8/15.
 */
//Hilo que controla
public class Chateo extends Thread {
    public void run() {
        try {

            MessageConsumer receiver = Configuration.getInstance().session.createConsumer(Configuration.getInstance().destination);
            receiver.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try{
                        TextMessage text = (TextMessage) message;

                        String textoRecibido=text.getText();
                        //si se recibe chat es que se trata de un mensaje para todos los usuarios
                        int indice = textoRecibido.indexOf("chat");

                        if(indice != -1){
                            //se imprime en la consola lo que habia mas el nuevo mensaje y el jugador que lo manda
                            indice = textoRecibido.indexOf("*");
                            String mensaje =textoRecibido.substring(indice+1);
                            String manda = textoRecibido.substring(textoRecibido.indexOf("-")+1, textoRecibido.indexOf("*"));
                            GameGUI.getInstance().console.setText(manda+": "+mensaje+"\n"+GameGUI.getInstance().console.getText());
                            GameGUI.getInstance().consoleCommands.setText("");
                        }
                    }
                    catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch(JMSException e) {
            e.printStackTrace();
        }
    }
}
