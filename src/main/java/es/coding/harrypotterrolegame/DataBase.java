package es.coding.harrypotterrolegame;

import javax.jms.*;

/**
 * Created by Enri on 25/8/15.
 */
//Hilo que se comunica con la base de datos
public class DataBase extends Thread { // class BaseDatos extends Runnable,
    public void run() {
        try {
            MessageConsumer receiver = Configuration.getInstance().session.createConsumer(Configuration.getInstance().destination);
            receiver.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try{
                        TextMessage text = (TextMessage) message;	//recibimos el mensaje

                        String textoRecibido=text.getText();

                        int indice = textoRecibido.indexOf("okbd"); //comprobamos que es el mensaje que nos interesa

                        if(indice != -1){
                            indice = textoRecibido.indexOf("-");
                            //si el mensaje es para mi
                            String nombre = textoRecibido.substring(indice+1, textoRecibido.indexOf("*"));
                            if (nombre.getText().equals(nombre)){
                                //obtengo los datos pasados en el mensaje
                                //funcion substring corta ristras segun las posiciones que nos interesan
                                String Mapa= textoRecibido.substring(textoRecibido.indexOf("*")+1,textoRecibido.indexOf("*")+3);
                                String X= textoRecibido.substring(textoRecibido.indexOf("*")+3,textoRecibido.indexOf("*")+5);
                                String Y= textoRecibido.substring(textoRecibido.indexOf("*")+5,textoRecibido.indexOf("*")+7);
                                String img= textoRecibido.substring(textoRecibido.indexOf("*")+7,textoRecibido.indexOf("*")+9);
                                String casa = textoRecibido.substring(textoRecibido.indexOf("*")+9,textoRecibido.indexOf("*")+10);
                                String sexo = textoRecibido.substring(textoRecibido.indexOf("*")+10,textoRecibido.indexOf("*")+11);
                                String bando =textoRecibido.substring(textoRecibido.indexOf("*")+11,textoRecibido.indexOf("*")+12);
                                String vida = textoRecibido.substring(textoRecibido.indexOf("*")+12,textoRecibido.indexOf("*")+16);
                                String nivel = textoRecibido.substring(textoRecibido.indexOf("*")+16,textoRecibido.indexOf("*")+18);
                                String objeto1 = textoRecibido.substring(textoRecibido.indexOf("*")+18,textoRecibido.indexOf("*")+19);
                                String objeto2 =textoRecibido.substring(textoRecibido.indexOf("*")+19,textoRecibido.indexOf("*")+20);
                                String objeto3 =textoRecibido.substring(textoRecibido.indexOf("*")+20,textoRecibido.indexOf("*")+21);
                                String objeto4 =textoRecibido.substring(textoRecibido.indexOf("*")+21,textoRecibido.indexOf("*")+22);
                                String exp =textoRecibido.substring(textoRecibido.indexOf("*")+22);
                                System.out.println("exp: "+exp);

                                //Conversion de string a entero para poder guardar los datos correctamente
                                char []r=Mapa.toCharArray();
                                int coordMapa= (int)r[0]*10+(int)r[1]-528;
                                char []r1=X.toCharArray();
                                int coordX= (int)r1[0]*10+(int)r1[1]-528;
                                char []r2=Y.toCharArray();
                                int coordY= (int)r2[0]*10+(int)r2[1]-528;
                                char []r3=img.toCharArray();
                                int imgnum= (int)r3[0]*10+(int)r3[1]-528;
                                char []r4 =casa.toCharArray();
                                int casanum = (int)r4[0]-48;
                                char []r5 =sexo.toCharArray();
                                int sexonum = (int)r5[0]-48;
                                char []r6 =bando.toCharArray();
                                int bandonum = (int)r6[0]-48;
                                char []r7 =vida.toCharArray();
                                int vidanum = (int)r7[0]*1000+(int)r7[1]*100+(int)r7[2]*10+(int)r7[3]-53328;//POr la cara
                                char []r8=nivel.toCharArray();
                                int nivelnum= (int)r8[0]*10+(int)r8[1]-528;
                                char []r9 =objeto1.toCharArray();
                                int objeto1num = (int)r9[0]-48;
                                char []r10 =objeto2.toCharArray();
                                int objeto2num = (int)r10[0]-48;
                                char []r11 =objeto3.toCharArray();
                                int objeto3num = (int)r11[0]-48;
                                char []r12 =objeto4.toCharArray();
                                int objeto4num = (int)r12[0]-48;
                                char []r13 =exp.toCharArray();

                                int a=1;
                                int i;
                                for(i=0; i< r13.length-1; i++)
                                {
                                    a = a*10;
                                }

                                int expnum =0;

                                for(i=0; i< r13.length; i++)
                                {
                                    int w = (int)r13[i]- 48;
                                    expnum += a * w;
                                    a = a / 10;
                                }
                                //creamos a nuestro jugador con los datos pasados por la base de datos
                                Game.getInstance().setMyPlayer(new Player(nombre,
                                        coordMapa,
                                        coordX,
                                        coordY,
                                        imgnum,
                                        casanum,
                                        sexonum,
                                        bandonum,
                                        vidanum,
                                        nivelnum,
                                        objeto1num,
                                        objeto2num,
                                        objeto3num,
                                        objeto4num,
                                        expnum));

                                //mostramos en la pantalla algunos datos relevantes
                                Vida.setText("Vida: "+ vidanum);
                                Nivel.setText("Nivel: "+ nivelnum);
                                Exp.setText("Exp: "+ expnum);
                                jButton.setVisible(true);
                            }

                        }
                        else{
                            //si no estoy en la base de datos nos manda un error
                            indice = textoRecibido.indexOf("Error");
                            if(indice!=-1)
                            {
                                Consola.setText("No estas registrado");
                            }
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
