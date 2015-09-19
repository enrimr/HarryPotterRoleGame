package es.coding.harrypotterrolegame;

import javax.jms.*;

/**
 * Created by Enri on 25/8/15.
 */
public     //Hilo que controla los combates
class Combat extends Thread {
    Player myPlayer = Game.getInstance().getMyPlayer();
    boolean quierehuir=false; //variable que indicara si un personaje ha huido
    //procedimiento que se ejecuta al ganar un combate
    void ganarcombate(){
        //Se aumenta la experiencia y el nivel si fuese oportuno
        myPlayer.experience+=5*Game.getInstance().gamePlayers[myPlayer.enemyId].level;
        if (myPlayer.experience>50*myPlayer.level*myPlayer.level){
            myPlayer.level++;
            myPlayer.experience=0;
        }
        //borramos los controles de la batalla
        // TODO sacar todo esto fuera del GUI
        GameGUI.getInstance().guion1.setVisible(false);
        GameGUI.getInstance().guion2.setVisible(false);
        GameGUI.getInstance().guion3.setVisible(false);
        GameGUI.getInstance().guion4.setVisible(false);
        GameGUI.getInstance().guion5.setVisible(false);
        GameGUI.getInstance().Atacar.setVisible(false);
        GameGUI.getInstance().Pocion.setVisible(false);
        GameGUI.getInstance().Veneno.setVisible(false);
        GameGUI.getInstance().huir.setVisible(false);
        GameGUI.getInstance().Maldición_Imperdonable.setVisible(false);
        GameGUI.getInstance().exp_rival.setVisible(false);
        GameGUI.getInstance().vida_rival.setVisible(false);
        GameGUI.getInstance().accioncombate.setVisible(false);

        //repintamos el mapa para poder seguir jugando por donde habiamo salido
        World.getInstance().getMaps()[myPlayer.pos[0]].drawMap(GameGUI.getInstance().jContentPane.getGraphics(), 180, 20);
        GameGUI.getInstance().drawOtherPlayers();
        myPlayer.drawPlayer(GameGUI.getInstance().jContentPane.getGraphics());
        GameGUI.getInstance().experience.setText("Exp: "+myPlayer.experience);
        GameGUI.getInstance().level.setText("Nivel: "+Game.getInstance().getMyPlayer().level);

    }
    //procedimiento que se ejecuta al perder un combate
    void perdercombate(){
        //se regresa al territorio del bando  oportuno y se le restaura la vida
        if (myPlayer.getFaction()==0){
            myPlayer.pos[0]=3;
        }
        else{
            myPlayer.pos[0]=5;
        }
        myPlayer.pos[1]=7;
        myPlayer.pos[2]=7;
        myPlayer.health=100;
        //borramos los campos de la batalla
        GameGUI.getInstance().guion1.setVisible(false);
        GameGUI.getInstance().guion2.setVisible(false);
        GameGUI.getInstance().guion3.setVisible(false);
        GameGUI.getInstance().guion4.setVisible(false);
        GameGUI.getInstance().guion5.setVisible(false);
        GameGUI.getInstance().Atacar.setVisible(false);
        GameGUI.getInstance().Pocion.setVisible(false);
        GameGUI.getInstance().Veneno.setVisible(false);
        GameGUI.getInstance().huir.setVisible(false);
        GameGUI.getInstance().Maldición_Imperdonable.setVisible(false);
        GameGUI.getInstance().exp_rival.setVisible(false);
        GameGUI.getInstance().vida_rival.setVisible(false);
        GameGUI.getInstance().accioncombate.setVisible(false);
        //repintamos el mapa para continuar la aventura
        World.getInstance().getMaps()[myPlayer.pos[0]].drawMap(GameGUI.getInstance().jContentPane.getGraphics(), 180, 20);
        GameGUI.getInstance().drawOtherPlayers();
        myPlayer.drawPlayer(GameGUI.getInstance().jContentPane.getGraphics());
        //mando la posicion para que los demas la actualizen
        conexionTopic.SendMessage("pos-"+myPlayer.getName()+"*"+"0"+myPlayer.pos[0]+"0"+myPlayer.pos[1]+"0"+myPlayer.pos[2]);
        //mando a mi enemy que he perdido el combate
        conexionTopic.SendMessage("accion-"+myPlayer.enemy+"*"+6);
        GameGUI.getInstance().life.setText("Vida: "+myPlayer.health);


    }
    //procedimiento que indica que yo he huido
    void huyocombate(){
        //aumentamos la experiencia y el nivel si fuese necesario
        myPlayer.experience+=2*Game.getInstance().gamePlayers[myPlayer.enemyId].level;
        if (myPlayer.experience>50*myPlayer.level*myPlayer.level){
            myPlayer.level++;
            myPlayer.experience=0;
        }
        //borramos los componentes de la batalla
        GameGUI.getInstance().guion1.setVisible(false);
        GameGUI.getInstance().guion2.setVisible(false);
        GameGUI.getInstance().guion3.setVisible(false);
        GameGUI.getInstance().guion4.setVisible(false);
        GameGUI.getInstance().guion5.setVisible(false);
        GameGUI.getInstance().Atacar.setVisible(false);
        GameGUI.getInstance().Pocion.setVisible(false);
        GameGUI.getInstance().Veneno.setVisible(false);
        GameGUI.getInstance().huir.setVisible(false);
        GameGUI.getInstance().Maldición_Imperdonable.setVisible(false);
        GameGUI.getInstance().exp_rival.setVisible(false);
        GameGUI.getInstance().vida_rival.setVisible(false);
        GameGUI.getInstance().accioncombate.setVisible(false);

        //volvemos a pintar el mapa
        World.getInstance().getMaps()[myPlayer.pos[0]].drawMap(GameGUI.getInstance().jContentPane.getGraphics(), 180, 20);
        GameGUI.getInstance().drawOtherPlayers();
        myPlayer.drawPlayer(GameGUI.getInstance().jContentPane.getGraphics());
        GameGUI.getInstance().experience.setText("Exp: "+myPlayer.experience);
        GameGUI.getInstance().level.setText("Nivel: "+myPlayer.level);

    }
    //cuerpo de tratamiento de los mensajes del combate
    public void run() {
        try {
            MessageConsumer receiver = Configuration.getInstance().session.createConsumer(Configuration.getInstance().destination);
            receiver.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try{
                        TextMessage text = (TextMessage) message;

                        String textoRecibido=text.getText();
                        //si recibimos combate daremos comienzo al combate
                        int indice = textoRecibido.indexOf("combate");

                        if(indice != -1){
                            indice = textoRecibido.indexOf("-");
                            //Vemos los jugadores participantes
                            String Nombre1=textoRecibido.substring(indice+1, textoRecibido.indexOf("*"));
                            String Nombre2=textoRecibido.substring(textoRecibido.indexOf("*")+1, textoRecibido.indexOf("$"));
                            //si soy yo me pongo luchando y le mando mis datos a mi oponente (bando, nivel, vida) y entro en combate
                            if ((Nombre1.equals(myPlayer.getName()))||(Nombre2.equals(myPlayer.getName()))){
                                if((Nombre1.equals(myPlayer.getName()))&&(!myPlayer.isFighting)){
                                    myPlayer.isFighting = true;
                                    String Bando=textoRecibido.substring(textoRecibido.indexOf("$")+1, textoRecibido.indexOf("$")+2);
                                    String Nivel=textoRecibido.substring(textoRecibido.indexOf("$")+2, textoRecibido.indexOf("%"));
                                    String Vida =textoRecibido.substring(textoRecibido.indexOf("%")+1);
                                    datos[0]=Bando;
                                    datos[1]=Nivel;
                                    datos[2]=Vida;
                                    conexionTopic.SendMessage("combate-"+Nombre2+"*"+myPlayer.getName()+"$"+myPlayer.getFaction()+myPlayer.level+"%"+myPlayer.health);
                                    GameGUI.getInstance().combate(Nombre2);
                                }
                                //else combate(Nombre1);
                            }

                        }
                        //si no se recibe combate se puede recibir accion que seria la opcion de mi oponente
                        else{
                            indice = textoRecibido.indexOf("accion");
                            if(indice!=-1){
                                indice = textoRecibido.indexOf("-");
                                //si mi nombre es el del mensaje es que la accion es para mi
                                String nombre=textoRecibido.substring(indice+1,textoRecibido.indexOf("*"));
                                if (myPlayer.getName().equals(nombre)){
                                    indice = textoRecibido.indexOf("*");
                                    //traduzco a entero la eleccion
                                    String eleccion=textoRecibido.substring(indice+1);
                                    char []r= new char[1];
                                    r=eleccion.toCharArray();
                                    int elecnum= (int)r[0]-48;
                                    //segun la eleccion me comporto de distintas formas
                                    switch(elecnum){
                                        case 0://si es cero es que me ha atacado y disminuyo mi vida
                                            GameGUI.getInstance().console.setText("Te han atacado");
                                            myPlayer.health-=(Game.getInstance().gamePlayers[myPlayer.enemyId].level+5);
                                            GameGUI.getInstance().life.setText("Salud: "+myPlayer.health);
                                            break;
                                        case 3://si te lanza una maldicion imperdonable me mata al isntante
                                            GameGUI.getInstance().console.setText("Has sufrido una maldición imperdonable!");
                                            myPlayer.health=0;
                                            break;
                                        case 4://si es 4 es que quiere hiur
                                            GameGUI.getInstance().console.setText("Intenta huir del combate!");
                                            quierehuir=true;
                                            break;
                                        case 6://si es 6 esta informandome que he ganado el combate
                                            myPlayer.isFighting=false;
                                            ganarcombate();
                                            break;
                                    }
                                    if (quierehuir){//si quiere huir llamo a huyocombate
                                        huyocombate();
                                        myPlayer.isFighting=false;
                                    }
                                    else if(myPlayer.health<=0){ //si yo perdi llamo a perder combate para avisarle
                                        perdercombate();
                                        myPlayer.isFighting=false;
                                    }
                                }
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
