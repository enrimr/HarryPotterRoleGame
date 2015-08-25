package es.coding.harrypotterrolegame;

import javax.jms.*;

/**
 * Created by Enri on 25/8/15.
 */
public     //Hilo que controla los combates
class Combat extends Thread {
    boolean quierehuir=false; //variable que indicara si un personaje ha huido
    //procedimiento que se ejecuta al ganar un combate
    void ganarcombate(){
        //Se aumenta la experiencia y el nivel si fuese oportuno
        MiJugador.experience+=5*jugadores[MiJugador.rivalID].level;
        if (MiJugador.experience>50*MiJugador.level*MiJugador.level){
            MiJugador.level++;
            MiJugador.experience=0;
        }
        //borramos los controles de la batalla
        guion1.setVisible(false);
        guion2.setVisible(false);
        guion3.setVisible(false);
        guion4.setVisible(false);
        guion5.setVisible(false);
        Atacar.setVisible(false);
        Pocion.setVisible(false);
        Veneno.setVisible(false);
        huir.setVisible(false);
        Maldición_Imperdonable.setVisible(false);
        exp_rival.setVisible(false);
        vida_rival.setVisible(false);
        accioncombate.setVisible(false);

        //repintamos el mapa para poder seguir jugando por donde habiamo salido
        mundito[MiJugador.pos[0]].DrawMap(jContentPane.getGraphics(), 180, 20);
        DibujarDemasJugadores();
        MiJugador.DrawPlayer(jContentPane.getGraphics());
        Exp.setText("Exp: "+MiJugador.experience);
        Nivel.setText("Nivel: "+MiJugador.level);

    }
    //procedimiento que se ejecuta al perder un combate
    void perdercombate(){
        //se regresa al territorio del bando  oportuno y se le restaura la vida
        if (MiJugador.bando==0){
            MiJugador.pos[0]=3;
        }
        else{
            MiJugador.pos[0]=5;
        }
        MiJugador.pos[1]=7;
        MiJugador.pos[2]=7;
        MiJugador.health=100;
        //borramos los campos de la batalla
        guion1.setVisible(false);
        guion2.setVisible(false);
        guion3.setVisible(false);
        guion4.setVisible(false);
        guion5.setVisible(false);
        Atacar.setVisible(false);
        Pocion.setVisible(false);
        Veneno.setVisible(false);
        huir.setVisible(false);
        Maldición_Imperdonable.setVisible(false);
        exp_rival.setVisible(false);
        vida_rival.setVisible(false);
        accioncombate.setVisible(false);
        //repintamos el mapa para continuar la aventura
        mundito[MiJugador.pos[0]].DrawMap(jContentPane.getGraphics(), 180, 20);
        DibujarDemasJugadores();
        MiJugador.DrawPlayer(jContentPane.getGraphics());
        //mando la posicion para que los demas la actualizen
        conexionTopic.SendMessage("pos-"+MiJugador.name+"*"+"0"+MiJugador.pos[0]+"0"+MiJugador.pos[1]+"0"+MiJugador.pos[2]);
        //mando a mi rival que he perdido el combate
        conexionTopic.SendMessage("accion-"+MiJugador.rival+"*"+6);
        Vida.setText("Vida: "+MiJugador.health);


    }
    //procedimiento que indica que yo he huido
    void huyocombate(){
        //aumentamos la experiencia y el nivel si fuese necesario
        MiJugador.experience+=2*jugadores[MiJugador.rivalID].level;
        if (MiJugador.experience>50*MiJugador.level*MiJugador.level){
            MiJugador.level++;
            MiJugador.experience=0;
        }
        //borramos los componentes de la batalla
        guion1.setVisible(false);
        guion2.setVisible(false);
        guion3.setVisible(false);
        guion4.setVisible(false);
        guion5.setVisible(false);
        Atacar.setVisible(false);
        Pocion.setVisible(false);
        Veneno.setVisible(false);
        huir.setVisible(false);
        Maldición_Imperdonable.setVisible(false);
        exp_rival.setVisible(false);
        vida_rival.setVisible(false);
        accioncombate.setVisible(false);

        //volvemos a pintar el mapa
        mundito[MiJugador.pos[0]].DrawMap(jContentPane.getGraphics(), 180, 20);
        DibujarDemasJugadores();
        MiJugador.DrawPlayer(jContentPane.getGraphics());
        Exp.setText("Exp: "+MiJugador.experience);
        Nivel.setText("Nivel: "+MiJugador.level);

    }
    //cuerpo de tratamiento de los mensajes del combate
    public void run() {
        try {
            MessageConsumer receiver = session.createConsumer(destination);
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
                            if ((Nombre1.equals(MiJugador.name))||(Nombre2.equals(MiJugador.name))){
                                if((Nombre1.equals(MiJugador.name))&&(!MiJugador.fighting)){
                                    MiJugador.fighting = true;
                                    String Bando=textoRecibido.substring(textoRecibido.indexOf("$")+1, textoRecibido.indexOf("$")+2);
                                    String Nivel=textoRecibido.substring(textoRecibido.indexOf("$")+2, textoRecibido.indexOf("%"));
                                    String Vida =textoRecibido.substring(textoRecibido.indexOf("%")+1);
                                    datos[0]=Bando;
                                    datos[1]=Nivel;
                                    datos[2]=Vida;
                                    conexionTopic.SendMessage("combate-"+Nombre2+"*"+MiJugador.name+"$"+MiJugador.bando+MiJugador.level+"%"+MiJugador.health);
                                    combate(Nombre2);
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
                                if (MiJugador.name.equals(nombre)){
                                    indice = textoRecibido.indexOf("*");
                                    //traduzco a entero la eleccion
                                    String eleccion=textoRecibido.substring(indice+1);
                                    char []r= new char[1];
                                    r=eleccion.toCharArray();
                                    int elecnum= (int)r[0]-48;
                                    //segun la eleccion me comporto de distintas formas
                                    switch(elecnum){
                                        case 0://si es cero es que me ha atacado y disminuyo mi vida
                                            Consola.setText("Te han atacado");
                                            MiJugador.health-=(jugadores[MiJugador.rivalID].level+5);
                                            Vida.setText("Salud: "+MiJugador.health);
                                            break;
                                        case 3://si te lanza una maldicion imperdonable me mata al isntante
                                            Consola.setText("Has sufrido una maldición imperdonable!");
                                            MiJugador.health=0;
                                            break;
                                        case 4://si es 4 es que quiere hiur
                                            Consola.setText("Intenta huir del combate!");
                                            quierehuir=true;
                                            break;
                                        case 6://si es 6 esta informandome que he ganado el combate
                                            MiJugador.fighting=false;
                                            ganarcombate();
                                            break;
                                    }
                                    if (quierehuir){//si quiere huir llamo a huyocombate
                                        huyocombate();
                                        MiJugador.fighting=false;
                                    }
                                    else if(MiJugador.health<=0){ //si yo perdi llamo a perder combate para avisarle
                                        perdercombate();
                                        MiJugador.fighting=false;
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
