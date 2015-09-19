package es.coding.harrypotterrolegame;

import javax.jms.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Properties;
import java.util.Random;

//Añadido

/**
 * Created by Enri on 24/8/15.
 */
public class GameGUI extends JFrame{

    // Refactored
    ConnectionManager conexionTopic;  //variable que se encarga del paso de mensajes
    Configuration config = Configuration.getInstance();
    Game game = Game.getInstance();
    Player myPlayer = game.myPlayer;
    Player[] gamePlayers = game.gamePlayers;
    Player [] creatures = game.getWorld().getCreatures();
    Player [] teachers = game.getWorld().getTeachers();
    World world = game.world;

    //int [][][] mapa = new int[9][numCeldas[0]][numCeldas[1]];

    Map[] mundito=new Map[9]; //variable que guarda los mapas
    //int nummap=9; //numeros de mapas del juego
    String [] datos = new String[3]; //variable usada para pasar datos a la hora combatir

    Properties properties = new Properties();

    private static GameGUI ourInstance = new GameGUI();

    public static GameGUI getInstance() {
        return ourInstance;
    }

    //muestra el mapa y la posicion del jugador
    public void drawPlayerPos(){
        MapNum.setText("Mapa " + myPlayer.pos[0]);
        MapCoor.setText("("+myPlayer.pos[1]+","+myPlayer.pos[2]+")");
    }

    void refresh(){ //procedimiento que refresca el escenario

        if(!myPlayer.isFighting){ //si el jugador no se encuentra en batalla
            //volvemos a pintar el mapa y al resto de game.gamePlayers
            mundito[myPlayer.pos[0]].drawMap(jContentPane.getGraphics(), 180, 20);
            drawOtherPlayers();
            myPlayer.drawPlayer(jContentPane.getGraphics());
        }
        else{
            if((!myPlayer.isFightingVersusCriature)&&(!myPlayer.isFightingVersusProfessor)){//si esta luchando
                //Pintamos todos los labels afectados en la batalla
                switch(cursor){
                    case 0:
                        guion1.setVisible(false);
                        guion1.setVisible(true);
                        break;
                    case 1:
                        guion2.setVisible(false);
                        guion2.setVisible(true);
                        break;
                    case 2:
                        guion3.setVisible(false);
                        guion3.setVisible(true);
                        break;
                    case 3:
                        guion4.setVisible(false);
                        guion4.setVisible(true);
                        break;
                    case 4:
                        guion5.setVisible(false);
                        guion5.setVisible(true);
                        break;
                }
                accioncombate.setVisible(false);
                Atacar.setVisible(false);
                Pocion.setVisible(false);
                Veneno.setVisible(false);
                huir.setVisible(false);
                Maldición_Imperdonable.setVisible(false);

                accioncombate.setVisible(true);
                Atacar.setVisible(true);
                Pocion.setVisible(true);
                Veneno.setVisible(true);
                huir.setVisible(true);
                Maldición_Imperdonable.setVisible(true);

                vida_rival.setVisible(false);
                exp_rival.setVisible(false);

                vida_rival.setVisible(true);
                exp_rival.setVisible(true);

                //Volvemos a pintar el fondo de la batalla y a los game.gamePlayers combatientes
                drawBackground("./images/background/duelo.gif");
                gamePlayers[myPlayer.enemyId].drawPlayer(jContentPane.getGraphics(), 2, 300, 50);
                myPlayer.drawPlayer(jContentPane.getGraphics(), 1, 70, 70);
            }
            if(myPlayer.isFightingVersusCriature){//si esta luchando con una criatura hacemos lo mismo que anteriormente
                switch(cursor){
                    case 0:
                        guion1.setVisible(false);
                        guion1.setVisible(true);
                        break;
                    case 1:
                        guion2.setVisible(false);
                        guion2.setVisible(true);
                        break;
                    case 2:
                        guion3.setVisible(false);
                        guion3.setVisible(true);
                        break;
                    case 3:
                        guion4.setVisible(false);
                        guion4.setVisible(true);
                        break;
                    case 4:
                        guion5.setVisible(false);
                        guion5.setVisible(true);
                        break;
                }
                accioncombate.setVisible(false);
                Atacar.setVisible(false);
                Pocion.setVisible(false);
                Veneno.setVisible(false);
                huir.setVisible(false);
                Maldición_Imperdonable.setVisible(false);

                accioncombate.setVisible(true);
                Atacar.setVisible(true);
                Pocion.setVisible(true);
                Veneno.setVisible(true);
                huir.setVisible(true);
                Maldición_Imperdonable.setVisible(true);

                vida_rival.setVisible(false);
                exp_rival.setVisible(false);

                vida_rival.setVisible(true);
                exp_rival.setVisible(true);

                jContentPane.getGraphics().clearRect(Integer.parseInt(
                        Configuration.getInstance().getConfig("x")),
                        Integer.parseInt(Configuration.getInstance().getConfig("y")),
                        world.getMaps()[myPlayer.pos[0]].getPixelWidth(),//mapaTam[0],
                        world.getMaps()[myPlayer.pos[0]].getPixelHeight());//mapaTam[1]);
                creatures[myPlayer.enemyId].drawPlayer(jContentPane.getGraphics(), 2, 300, 50);
                myPlayer.drawPlayer(jContentPane.getGraphics(), 1, 70, 70);
            }
        }
    }

    //######################
    public void drawBackground(String str){
        ImageIcon imagen = new ImageIcon(str);
        Image img = imagen.getImage();
        int anchoImagen = imagen.getIconWidth();
        int altoImagen = imagen.getIconHeight();
        jContentPane.getGraphics().drawImage(img,180+30, 20+15,anchoImagen,altoImagen,imagen.getImageObserver());
    }

    int i=0;

    //funcion que nos indica si un personaje ya esta insertado en nuestro vector
    private boolean esta(String nombre){
        int aux=0;
        System.out.println("esta: "+nombre+ "--> Lo que le entra al esta");
        while(aux < game.gamePlayersLength && !gamePlayers[aux].getName().equals(nombre))
        {
            aux++;
            System.out.println(aux+gamePlayers[aux].getName());
        }
        return aux < game.gamePlayersLength;
    }

    //procedimiento que busca al enemy y carga sus datos
    void buscarImagen(String nombre_rival){
        int aux=0;
        //buscamos al enemy
        while(!gamePlayers[aux].getName().equals(nombre_rival))
        {
            aux++;
        }
        //dibujamos a los game.gamePlayers en el area de combate
        gamePlayers[aux].drawPlayer(jContentPane.getGraphics(), 2, 300, 50);
        myPlayer.drawPlayer(jContentPane.getGraphics(), 1, 70, 70);
        //cargamos los datos convenientes
        myPlayer.enemy = gamePlayers[aux].getName();

        myPlayer.enemyId = aux;
        char []r6 =datos[0].toCharArray();
        gamePlayers[aux].setFaction((int)r6[0]-48);

        char []r7 =datos[2].toCharArray();
        int a=1;
        for(int i=0; i< r7.length-1; i++)
        {
            a = a*10;
        }

        gamePlayers[aux].health=0;
        for(int i=0; i< r7.length; i++)
        {
            int w = (int)r7[i]- 48;
            gamePlayers[aux].health += a * w;
            a = a / 10;
        }

        char []r8=datos[1].toCharArray();
        gamePlayers[aux].level= (int)r8[0]-48;

        //preparamos los labels implicados en el combate
        vida_rival.setText("Vida: "+gamePlayers[aux].health);
        exp_rival.setText("Nivel: "+gamePlayers[aux].level);
        vida_rival.setVisible(true);
        exp_rival.setVisible(true);

    }

    //procedimiento que prepara el escenario para un combate entre dos game.gamePlayers
    void combate(String rival){
        //pintamos el fondo del combate
        jContentPane.getGraphics().clearRect(
                Integer.parseInt(Configuration.getInstance().getConfig("x")),
                Integer.parseInt(Configuration.getInstance().getConfig("y")),
                world.getMaps()[myPlayer.pos[0]].getPixelWidth(),
                world.getMaps()[myPlayer.pos[0]].getPixelHeight());
        drawBackground("./images/background/duelo.gif");
        //activamos los recursos del combate
        cursor=0;
        accioncombate.setVisible(true);
        guion1.setVisible(true);
        Atacar.setVisible(true);
        Pocion.setVisible(true);
        Veneno.setVisible(true);
        huir.setVisible(true);
        Maldición_Imperdonable.setVisible(true);
        //llamamos a imprimir imagen para dibujar a los combatientes
        buscarImagen(rival);

    }

    //	procedimiento que prepara el escenario para un combate contra criaturas
    void combatVersusCriature(int rival){
        
        //decimos que estamos peleando contra uan criatura
        myPlayer.isFighting =true;
        myPlayer.isFightingVersusCriature =true;
        creatures[rival].isFighting =true;
        //habilitamos las funciones del combate
        cursor=0;
        accioncombate.setVisible(true);
        guion1.setVisible(true);
        Atacar.setVisible(true);
        Pocion.setVisible(true);
        Veneno.setVisible(true);
        huir.setVisible(true);
        Maldición_Imperdonable.setVisible(true);

        jContentPane.getGraphics().clearRect(
                Integer.parseInt(Configuration.getInstance().getConfig("x")),
                Integer.parseInt(Configuration.getInstance().getConfig("y")),
                world.getMaps()[myPlayer.pos[0]].getPixelWidth(),//mapaTam[0],
                world.getMaps()[myPlayer.pos[0]].getPixelHeight());//mapaTam[1]);
        //pintamos a la criatura y al personaje
        creatures[rival].drawPlayer(jContentPane.getGraphics(), 2, 300, 50);
        myPlayer.drawPlayer(jContentPane.getGraphics(), 1, 70, 70);
        //guardamos sus datos e imprimos su vida y su nivel
        myPlayer.enemy =creatures[rival].getName();
        myPlayer.enemyId =rival;

        vida_rival.setText("Vida: " + creatures[rival].health);
        exp_rival.setText("Nivel: "+creatures[rival].level);
        vida_rival.setVisible(true);
        exp_rival.setVisible(true);


    }

    //vector que guarda las preguntas
    String []preguntas = {"¿Cúal es el deporte de los magos\n  1.Fútbol\n  2.Quidditch\n  3.Lanzamiento de varita\n",
            "¿Librería usada para el paso de mensajes?  1.JMS\n  2.Socket\n  3.Topics\n",
            "¿Transformada de Laplace de la funcion escalón\n 1.s+2/s 2.1/s+1 3.1/s"};
    //vector que guarda las respuestas correctas de las preguntas
    int [] soluciones = {2, 1, 3};

    //procedimiento que se encarga de la interaccion con los profesores
    void askProfessor(int rival){

        //decimos que estamos hablando con un profesor
        myPlayer.isFighting =true;
        myPlayer.isFightingVersusProfessor =true;
        teachers[rival].isFighting =true;
        //imprimimos las opciones de las preguntas
        cursor=0;
        opcion1.setVisible(true);
        guion1.setVisible(true);
        opcion2.setVisible(true);
        opcion3.setVisible(true);
        accioncombate.setVisible(true);


        jContentPane.getGraphics().clearRect(
                Integer.parseInt(Configuration.getInstance().getConfig("x")),
                Integer.parseInt(Configuration.getInstance().getConfig("y")),
                world.getMaps()[myPlayer.pos[0]].getPixelWidth(),//mapaTam[0],
                world.getMaps()[myPlayer.pos[0]].getPixelHeight());//mapaTam[1]);

        //profesores[enemy].drawPlayer(jContentPane.getGraphics(),2,300,50);
        //myPlayer.drawPlayer(jContentPane.getGraphics(),1,70,70);
        myPlayer.enemy = teachers[rival].getName();
        myPlayer.enemyId = rival;

        Random randomGenerator = new Random();
        //escogemos aleatoriamente una pregunta y la imprimimos
        int randomInt = randomGenerator.nextInt(2);
        if(rival<8){
            pregunta.setText(preguntas[randomInt]);
            myPlayer.pregunta= randomInt;
            pregunta.setVisible(true);
        }
        else{//si se trata de dumblerdore o voldemort imprimimos una pregunta dificil
            pregunta.setText(preguntas[2]);
            myPlayer.pregunta= 2;
            pregunta.setVisible(true);
        }

    }

    //procedimiento que controla si nos encontramos con alguien en el mapa
    void encuentro(){

        //vemos si nos encontramos con algun jugador
        for (int i=0; i<game.gamePlayersLength; i++){
            if((gamePlayers[i].pos[0]== myPlayer.pos[0])&&(gamePlayers[i].pos[1]== myPlayer.pos[1])&&(gamePlayers[i].pos[2]== myPlayer.pos[2])){
                //si nos encontramos con alguien le mandamos el mensaje de combate
                conexionTopic.sendMessage("combate-"+gamePlayers[i].getName()+"*"+ myPlayer.getName()+"$"+ myPlayer.getFaction()+ myPlayer.level+"%"+ myPlayer.health);
                //combate(game.gamePlayers[i].name);
            }
        }
        //comprobamos si nos encontramos con alguna criatura
        for (int i=0; i<10; i++){
            if((creatures[i].pos[0]== myPlayer.pos[0])&&(creatures[i].pos[1]== myPlayer.pos[1])&&(creatures[i].pos[2]== myPlayer.pos[2])){
                combatVersusCriature(i);
            }
        }
        //comprobamos si nos encontramos con algun profesor y si es de nuestra casa y bando para hablar con el
        for (int i=0; i<10; i++){
            if((teachers[i].pos[0]== myPlayer.pos[0])&&(teachers[i].pos[1]== myPlayer.pos[1])&&(teachers[i].pos[2]== myPlayer.pos[2])){
                if(((teachers[i].getHouse() == myPlayer.getHouse())||(teachers[i].getHouse() == -1))&&(teachers[i].getFaction() == myPlayer.getFaction())){
                    askProfessor(i);
                }
                else{
                    console.setText("No puedes hablar con este profesor no es de tu casa");
                }

            }
        }
    }

    //funcion que actualiza las posiciones de los demas game.gamePlayers
    void actualiza(String nombre, int coordMapa, int coordX, int coordY){
        Player myPlayer = Game.getInstance().myPlayer;

        int i = 0;
        //buscamos al personaje que se movio
        while (!gamePlayers[i].getName().equals(nombre)){
            i++;
        }
        //una vez encontrado lo borramos de su posicion actual
        if ((myPlayer.pos[0]==gamePlayers[i].pos[0])&&(!myPlayer.isFighting))
            gamePlayers[i].recoverLayer(jContentPane.getGraphics());
        //actualizamos su posicion
        gamePlayers[i].pos[0]= coordMapa;
        gamePlayers[i].pos[1]= coordX;
        gamePlayers[i].pos[2]= coordY;
        //volvemos a pintar al jugador en su nueva posicion
        if ((myPlayer.pos[0]==gamePlayers[i].pos[0])&&(!myPlayer.isFighting))
            gamePlayers[i].drawPlayer(jContentPane.getGraphics());
    }

    //clase que controla los mensajes de movimiento y de conexion
    class TestTh extends Thread {
        public void run() {
            try {
                //start the connection to enable message delivery
                config.connection.start();
                MessageConsumer receiver = config.session.createConsumer(config.destination);
                receiver.setMessageListener(new MessageListener() {
                    public void onMessage(Message message) {
                        try{
                            TextMessage text = (TextMessage) message;
                            System.out.println(message);
                            String textoRecibido=text.getText();

                            //si se recibe conectar es que un nuevo usuario a entrado al juego
                            int indice = textoRecibido.indexOf("conectar");
                            if(indice != -1){
                                indice = textoRecibido.indexOf("-");
                                String receivedName = textoRecibido.substring(indice+1, textoRecibido.indexOf("*"));
                                //Comprobamos que el mensaje no lo haya enviado yo
                                if (!myPlayer.getName().equals(receivedName)){
                                    if(!esta(receivedName)){
                                        //si no soy yo y no esta ya agregado obtengo sus datos
                                        String Mapa= textoRecibido.substring(textoRecibido.indexOf("*")+1,textoRecibido.indexOf("*")+3);
                                        String X= textoRecibido.substring(textoRecibido.indexOf("*")+3,textoRecibido.indexOf("*")+5);
                                        String Y= textoRecibido.substring(textoRecibido.indexOf("*")+5,textoRecibido.indexOf("*")+7);
                                        String img= textoRecibido.substring(textoRecibido.indexOf("*")+7,textoRecibido.indexOf("*")+9);
                                        //pasamos a enteros los necesarios para poder guardarlos
                                        char []r=Mapa.toCharArray();
                                        int coordMapa= (int)r[0]*10+(int)r[1]-528;
                                        char []r1=X.toCharArray();
                                        int coordX= (int)r1[0]*10+(int)r1[1]-528;
                                        char []r2=Y.toCharArray();
                                        int coordY= (int)r2[0]*10+(int)r2[1]-528;
                                        char []r3=img.toCharArray();
                                        int imgnum= (int)r3[0]*10+(int)r3[1]-528;
                                        //creamos en la posicion adecuado al nuevo player
                                        gamePlayers[game.gamePlayersLength] = new Player(receivedName, coordMapa, coordX, coordY, imgnum);
                                        System.out.println("Prueba: "+gamePlayers[game.gamePlayersLength].getName());
                                        String charX="0";
                                        String charY="0";
                                        String charMapa="0";
                                        String charimg="0";

                                        if(myPlayer.pos[0] > 9) charMapa="";
                                        if(myPlayer.pos[1] > 9) charX="";
                                        if(myPlayer.pos[2] > 9) charY="";
                                        if(myPlayer.getSprite() > 9)charimg="";

                                        //le enviamos un mensaje con mis datos para que me agregue
                                        conexionTopic.sendMessage("conectar-"+ myPlayer.getName()+"*"+charMapa+ myPlayer.pos[0]+charX+ myPlayer.pos[1]+charY+ myPlayer.pos[2]+charimg+ myPlayer.getSprite());
                                        //pintamos al nuevo jugador para poder verlo
                                        if (myPlayer.pos[0]==gamePlayers[game.gamePlayersLength].pos[0]){
                                            gamePlayers[game.gamePlayersLength].drawPlayer(jContentPane.getGraphics());
                                        }
                                        game.gamePlayersLength++; //incrementamos la posicion del vector de game.gamePlayers ya que ahora hay uno mas
                                    }
                                }
                            }
                            else{
                                indice = textoRecibido.indexOf("pos");
                                //si se recibe pos indica que un jugador se ha movido
                                if (indice != -1){
                                    String xino = myPlayer.getName();
                                    //si no soy yo recojo los datos enviados
                                    if (!xino.equals(textoRecibido.substring(textoRecibido.indexOf("-")+1,textoRecibido.indexOf("*")))){
                                        String Nombre=textoRecibido.substring(textoRecibido.indexOf("-")+1,textoRecibido.indexOf("*"));
                                        String Mapa= textoRecibido.substring(textoRecibido.indexOf("*")+1,textoRecibido.indexOf("*")+3);
                                        String X= textoRecibido.substring(textoRecibido.indexOf("*")+3,textoRecibido.indexOf("*")+5);
                                        String Y= textoRecibido.substring(textoRecibido.indexOf("*")+5,textoRecibido.indexOf("*")+7);
                                        char []r=Mapa.toCharArray();
                                        int coordMapa= (int)r[0]*10+(int)r[1]-528;
                                        char []r1=X.toCharArray();
                                        int coordX= (int)r1[0]*10+(int)r1[1]-528;
                                        char []r2=Y.toCharArray();
                                        int coordY= (int)r2[0]*10+(int)r2[1]-528;
                                        //llamamos a actualizar para modificar los datos
                                        actualiza(Nombre, coordMapa, coordX, coordY);
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

    //procedimiento que dibuja a los demas personajes, criaturas y profesores
    void drawOtherPlayers() {
        for(int i=0; i < game.gamePlayersLength; i++)
        {
            if(gamePlayers[i].pos[0]== myPlayer.pos[0])
                gamePlayers[i].drawPlayer(jContentPane.getGraphics());
        }
        for(int i=0; i<10; i++)
        {
            if(creatures[i].pos[0]== myPlayer.pos[0])
                creatures[i].drawPlayer(jContentPane.getGraphics());
        }
        for(int i=0; i<10; i++)
        {
            if(teachers[i].pos[0]== myPlayer.pos[0])
                teachers[i].drawPlayer(jContentPane.getGraphics());
        }

    }

    //moviemiento hacia arriba
    void PlayerUp(){
        //Comprobamos si cambiará de mapa
        if (myPlayer.pos[2]==1){
            if(myPlayer.pos[0]==0 || myPlayer.pos[0]==1|| myPlayer.pos[0]==2)//comprobamos que no nos chocamos
                console.setText("PlayerUP(): Llegó al límite superior de un mapa");
            else if(!mundito[myPlayer.pos[0]-3].matrix[myPlayer.pos[1]][myPlayer.pos[2]].isBlocked){
                //cambiamos de mapa
                myPlayer.pos[0]-=3;
                myPlayer.pos[2]=(world.getMaps()[myPlayer.pos[0]].getCellHeight()-1);//eSTABA EN -1
                console.setText("Estamos en el mapa:" + myPlayer.pos[0]);
                jContentPane.getGraphics().clearRect(180,20,200,200);
                mundito[myPlayer.pos[0]].drawMap(
                        jContentPane.getGraphics(),
                        Integer.parseInt(Configuration.getInstance().getConfig("x")),
                        Integer.parseInt(Configuration.getInstance().getConfig("y")));
                myPlayer.drawPlayer(jContentPane.getGraphics());

                drawPlayerPos();
            }
            //si hay un obstaculo en el otro mapa no puedes pasar
            else {
                console.setText("PlayerUP(): hay un obstáculo en el otro mapa");}
        }
        // Si no hay obstáculo nos movemos
        else if (!mundito[myPlayer.pos[0]].matrix[myPlayer.pos[1]][myPlayer.pos[2]-1].isBlocked){
            console.setText("");
            myPlayer.recoverLayer(jContentPane.getGraphics());//recoverLayer();
            myPlayer.pos[2]-=1;
            myPlayer.drawPlayer(jContentPane.getGraphics());
            drawPlayerPos();
        }
        else {//sino decimos que nos chocamos
            console.setText("PlayerUP(): Se chocó");
        }
    }

    void PlayerDown(){
        //Comprobamos si cambiará de mapa
        if ((myPlayer.pos[2])==(world.getMaps()[myPlayer.pos[0]].getCellHeight())-1){
            if(myPlayer.pos[0]==6 || myPlayer.pos[0]==7|| myPlayer.pos[0]==8)//comprobamos que no estamos en el limite
                console.setText("PlayerUP(): Llegó al límite inferior de un mapa");
            else if(!mundito[myPlayer.pos[0]+3].matrix[myPlayer.pos[1]][0].isBlocked && !mundito[myPlayer.pos[0]+3].matrix[myPlayer.pos[1]][1].isBlocked){ //Antes no estaba el &&
                //				Cambiamos de mapa
                myPlayer.pos[0]+=3;
                myPlayer.pos[2]=1;
                console.setText("Estamos en el mapa:" + myPlayer.pos[0]);
                jContentPane.getGraphics().clearRect(180,20,200,200);
                mundito[myPlayer.pos[0]].drawMap(
                        jContentPane.getGraphics(),
                        Integer.parseInt(Configuration.getInstance().getConfig("x")),
                        Integer.parseInt(Configuration.getInstance().getConfig("y")));
                myPlayer.drawPlayer(jContentPane.getGraphics());
                drawPlayerPos();
            }
            //			si hay un obstaculo en el otro mapa no puedes pasar
            else {
                console.setText("PlayerDown(): hay un obstáculo en el otro mapa");}
        }
        //si no hay obstaculo nos movemos
        else if (!mundito[myPlayer.pos[0]].matrix[myPlayer.pos[1]][myPlayer.pos[2]+1].isBlocked){//Bedebia ser JugPos[2]+1
            console.setText("");
            myPlayer.recoverLayer(jContentPane.getGraphics());
            myPlayer.pos[2]+=1;
            myPlayer.drawPlayer(jContentPane.getGraphics());
            drawPlayerPos();
        }
        //sino decimos que nos chocamos
        else console.setText("PlayerDown(): Se chocó");
    }

    void PlayerRight(){
        //Comprobamos si cambiará de mapa
        if (myPlayer.pos[1]==(world.getMaps()[myPlayer.pos[0]].getCellWidth()-1)){
            if(myPlayer.pos[0]==2 || myPlayer.pos[0]==5|| myPlayer.pos[0]==8)//comprobamos que hay mas mapas
                console.setText("PlayerUP(): Llegó al límite derecho del mundo");
            else if(!mundito[myPlayer.pos[0]+1].matrix[0][myPlayer.pos[2]].isBlocked){
                //Cambiamos de mapa
                myPlayer.pos[0]+=1;
                myPlayer.pos[1]=0;
                console.setText("Estamos en el mapa:" + myPlayer.pos[0]);
                jContentPane.getGraphics().clearRect(180,20,200,200);
                mundito[myPlayer.pos[0]].drawMap(
                        jContentPane.getGraphics(),
                        Integer.parseInt(Configuration.getInstance().getConfig("x")),
                        Integer.parseInt(Configuration.getInstance().getConfig("y")));
                myPlayer.drawPlayer(jContentPane.getGraphics());
                drawPlayerPos();
            }
            else {
                console.setText("PlayerRight(): hay un obstáculo en el otro mapa");}
            // Comprobamos si puede ir a una mapa válido

        }//sin no hay obstaculo nos movemos
        else if (!mundito[myPlayer.pos[0]].matrix[myPlayer.pos[1]+1][myPlayer.pos[2]].isBlocked && !mundito[myPlayer.pos[0]].matrix[myPlayer.pos[1]+1][myPlayer.pos[2]+1].isBlocked) {//mIRAMOS LOS PIES
            console.setText("");
            myPlayer.recoverLayer(jContentPane.getGraphics());
            myPlayer.pos[1]+=1;
            myPlayer.drawPlayer(jContentPane.getGraphics());
            drawPlayerPos();
        }
        else//si hay obstaculo le decimos que ha chocado
            console.setText("PlayerRight(): Se chocó");
    }

    void PlayerLeft(){
        //Comprobamos si cambiará de mapa
        if (myPlayer.pos[1]==0){
            if(myPlayer.pos[0]==0 || myPlayer.pos[0]==3|| myPlayer.pos[0]==6)//Se mira si hay otro mapa
                console.setText("PlayerUP(): Llegó al límite inzquierdo del mundo");
            else if(!mundito[myPlayer.pos[0]-1].matrix[(world.getMaps()[myPlayer.pos[0]].getCellWidth()-1)][myPlayer.pos[2]].isBlocked){
                //				//cambiamos de mapa
                myPlayer.pos[0]-=1;
                myPlayer.pos[1]=(world.getMaps()[myPlayer.pos[0]].getCellHeight()-1);
                console.setText("Estamos en el mapa:" + myPlayer.pos[0]);
                jContentPane.getGraphics().clearRect(180,20,200,200);
                mundito[myPlayer.pos[0]].drawMap(
                        jContentPane.getGraphics(),
                        Integer.parseInt(Configuration.getInstance().getConfig("x")),
                        Integer.parseInt(Configuration.getInstance().getConfig("y")));
                myPlayer.drawPlayer(jContentPane.getGraphics());
                drawPlayerPos();
            }
            else {
                console.setText("PlayerLeft(): hay un obstáculo en el otro mapa");}
            // Comprobamos si puede ir a una mapa válido

        }//si no hay obstaculo nos movemos
        else if (!mundito[myPlayer.pos[0]].matrix[myPlayer.pos[1]-1][myPlayer.pos[2]].isBlocked && !mundito[myPlayer.pos[0]].matrix[myPlayer.pos[1]-1][myPlayer.pos[2]-1].isBlocked){
            console.setText("");
            myPlayer.recoverLayer(jContentPane.getGraphics());
            myPlayer.pos[1]-=1;
            myPlayer.drawPlayer(jContentPane.getGraphics());
            drawPlayerPos();
        }
        else console.setText("PlayerLeft(): Se chocó");//sino le decimos que no puede continuar
    }

    private static final long serialVersionUID = 1L;

    JPanel jContentPane = null;

    JButton jButton = null;

    private JLabel MapNum = null;
    private JButton jButton1 = null;
    private JLabel MapCoor = null;
    private JButton ButUP = null;
    private JButton ButRight = null;
    private JButton ButDown = null;
    private JButton ButLeft = null;
    JTextField consoleCommands = null;
    JEditorPane console = null;

    private JButton jButton3 = null;  //  @jve:decl-index=0:visual-constraint="332,46"

    private JButton Enviar = null;
    private JButton jButton32 = null;
    private JLabel nombre1 = null;
    JTextField nameInput = null;
    /**
     * This is the default constructor
     */
    public GameGUI() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(655, 643);
        this.setContentPane(getJContentPane());
        this.setTitle("Harry Potter - El juego de Rol");
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                switch (c){
                    case 'W':
                        PlayerUp();
                        break;
                }
            }
        });
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) {


            }
        });
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) {
                System.out.println("windowClosed()"); // TODO Auto-generated Event stub windowClosed()
            }
        });
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            pregunta = new JLabel();
            pregunta.setBounds(new Rectangle(165, 31, 453, 184));
            pregunta.setText("");
            opcion3 = new JLabel();
            opcion3.setBounds(new Rectangle(284, 403, 70, 18));
            opcion3.setText("Opcion 3");
            opcion3.setVisible(false);
            opcion2 = new JLabel();
            opcion2.setBounds(new Rectangle(285, 375, 67, 16));
            opcion2.setText("Opcion 2");
            opcion2.setVisible(false);
            opcion1 = new JLabel();
            opcion1.setBounds(new Rectangle(285, 343, 64, 15));
            opcion1.setText("Opcion 1");
            opcion1.setVisible(false);
            exp_rival = new JLabel();
            exp_rival.setBounds(new Rectangle(474, 401, 77, 15));
            exp_rival.setText("");
            exp_rival.setVisible(false);
            vida_rival = new JLabel();
            vida_rival.setBounds(new Rectangle(474, 374, 75, 17));
            vida_rival.setText("");
            vida_rival.setVisible(false);
            experience = new JLabel();
            experience.setBounds(new Rectangle(23, 279, 90, 16));
            experience.setText("");
            experience.setVisible(false);
            level = new JLabel();
            level.setBounds(new Rectangle(23, 255, 89, 15));
            level.setText("");
            level.setVisible(false);
            life = new JLabel();
            life.setBounds(new Rectangle(23, 302, 90, 15));
            life.setText("");
            life.setVisible(false);
            guion5 = new JLabel();
            guion5.setBounds(new Rectangle(241, 463, 22, 16));
            guion5.setText(">>>");
            guion5.setVisible(false);
            huir = new JLabel();
            huir.setBounds(new Rectangle(285, 464, 153, 16));
            huir.setText("Huir");
            huir.setVisible(false);
            guion4 = new JLabel();
            guion4.setBounds(new Rectangle(241, 434, 21, 16));
            guion4.setText(">>>");
            guion4.setVisible(false);
            Maldición_Imperdonable = new JLabel();
            Maldición_Imperdonable.setBounds(new Rectangle(285, 434, 114, 17));
            Maldición_Imperdonable.setText("Lanzar Maldición");
            Maldición_Imperdonable.setVisible(false);
            Veneno = new JLabel();
            Veneno.setBounds(new Rectangle(285, 403, 91, 18));
            Veneno.setText("Lanzar Veneno");
            Veneno.setVisible(false);
            Pocion = new JLabel();
            Pocion.setBounds(new Rectangle(285, 375, 91, 16));
            Pocion.setText("Beber Pocion");
            Pocion.setVisible(false);
            Atacar = new JLabel();
            Atacar.setBounds(new Rectangle(286, 343, 90, 16));
            Atacar.setText("Atacar");
            Atacar.setVisible(false);
            guion3 = new JLabel();
            guion3.setBounds(new Rectangle(239, 403, 22, 16));
            guion3.setText(">>>");
            guion3.setVisible(false);
            guion2 = new JLabel();
            guion2.setBounds(new Rectangle(240, 373, 24, 15));
            guion2.setText(">>>");
            guion2.setVisible(false);
            guion1 = new JLabel();
            guion1.setBounds(new Rectangle(240, 344, 21, 14));
            guion1.setText(">>>");
            guion1.setVisible(false);
            limagenreg = new JLabel();
            limagenreg.setBounds(new Rectangle(195, 166, 84, 16));
            limagenreg.setText("Imagen");
            limagenreg.setVisible(false);
            lcasareg = new JLabel();
            lcasareg.setBounds(new Rectangle(194, 135, 86, 16));
            lcasareg.setText("Casa");
            lcasareg.setVisible(false);
            lbandoreg = new JLabel();
            lbandoreg.setBounds(new Rectangle(197, 108, 83, 16));
            lbandoreg.setText("Bando");
            lbandoreg.setVisible(false);
            lsexoreg = new JLabel();
            lsexoreg.setBounds(new Rectangle(197, 78, 83, 14));
            lsexoreg.setText("Sexo");
            lsexoreg.setVisible(false);
            lnombrereg = new JLabel();
            lnombrereg.setBounds(new Rectangle(199, 48, 80, 12));
            lnombrereg.setText("Nombre");
            lnombrereg.setVisible(false);
            nombre1 = new JLabel();
            nombre1.setBounds(new Rectangle(301, 107, 106, 17));
            nombre1.setText("Usuario");
            MapCoor = new JLabel();
            MapCoor.setBounds(new Rectangle(588, 475, 44, 23));
            MapCoor.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
            MapCoor.setText("Coor#");
            MapNum = new JLabel();
            MapNum.setBounds(new Rectangle(534, 475, 44, 23));
            MapNum.setText("Map#");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.setFont(new Font("Dialog", Font.PLAIN, 12));
            jContentPane.add(getJButton(), null);
            jContentPane.add(MapNum, null);
            jContentPane.add(getJButton1(), null);
            jContentPane.add(MapCoor, null);
            jContentPane.add(getButUP(), null);
            jContentPane.add(getButRight(), null);
            jContentPane.add(getButDown(), null);
            jContentPane.add(getButLeft(), null);
            jContentPane.add(getConsoleCommands(), null);
            jContentPane.add(getConsole(), null);
            jContentPane.add(getEnviar(), null);
            jContentPane.add(getJButton32(), null);
            jContentPane.add(nombre1, null);
            jContentPane.add(getNameInput(), null);
            jContentPane.add(getJButton2(), null);
            jContentPane.add(getNombrereg(), null);
            jContentPane.add(lnombrereg, null);
            jContentPane.add(lsexoreg, null);
            jContentPane.add(lbandoreg, null);
            jContentPane.add(lcasareg, null);
            jContentPane.add(limagenreg, null);
            jContentPane.add(getSexoreg(), null);
            jContentPane.add(getBandoreg(), null);
            jContentPane.add(getCasareg(), null);
            jContentPane.add(getImagenreg(), null);
            jContentPane.add(getCrear(), null);
            jContentPane.add(guion1, null);
            jContentPane.add(guion2, null);
            jContentPane.add(guion3, null);
            jContentPane.add(Atacar, null);
            jContentPane.add(Pocion, null);
            jContentPane.add(Veneno, null);
            jContentPane.add(Maldición_Imperdonable, null);
            jContentPane.add(guion4, null);
            jContentPane.add(huir, null);
            jContentPane.add(guion5, null);
            jContentPane.add(life, null);
            jContentPane.add(getAccioncombate(), null);
            jContentPane.add(level, null);
            jContentPane.add(experience, null);
            jContentPane.add(vida_rival, null);
            jContentPane.add(exp_rival, null);
            jContentPane.add(opcion1, null);
            jContentPane.add(opcion2, null);
            jContentPane.add(opcion3, null);
            jContentPane.add(pregunta, null);
            jContentPane.add(getPrueba(), null);
            jContentPane.add(getRefrescar(), null);
            jContentPane.addKeyListener(new java.awt.event.KeyAdapter() {

                public void keyPressed(java.awt.event.KeyEvent e) {
                    //					if(e==java.awt.event.KeyEvent.VK_W)
                    int c = e.getKeyCode();
                    switch (c){
                        case java.awt.event.KeyEvent.VK_W:
                            PlayerUp();
                            break;
                        case java.awt.event.KeyEvent.VK_S:
                            PlayerDown();
                            break;
                        case java.awt.event.KeyEvent.VK_A:
                            PlayerLeft();
                            break;
                        case java.awt.event.KeyEvent.VK_D:
                            PlayerRight();
                            break;
                    }

                }
            });
        }
        return jContentPane;
    }

    /**
     * This method initializes jButton
     *
     * @return javax.swing.JButton
     */

    private JButton getJButton() {
        if (jButton == null) {
            jButton = new JButton();
            jButton.setBounds(new Rectangle(17, 19, 98, 28));
            jButton.setText("Empezar");
            jButton.setName("");
            jButton.setVisible(false);
            jButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    //Boton que inicia el juego
                    enlace.interrupt();
                    jButton1.setVisible(true);

                    //·························lanzar hilo general·······························
                    System.out.println("Boton Cliqueado"); // TODO Auto-generated Event stub mouseClicked()

                    TestTh Hilo = new TestTh();
                    Hilo.start();
                    //·························fin lanzar hilo·····························
                    //·························lanzar hilo chateo·······························
                    Chateo Chat = new Chateo();
                    Chat.start();
                    //·························fin lanzar hilo chateo·····························
                    //					·························lanzar hilo combate·······························
                    Combat pelea = new Combat();
                    pelea.start();
                    //·························fin lanzar hilo combate·····························
                }
            });

        }
        return jButton;
    }

    /**
     * This method initializes jButton1
     *
     * @return javax.swing.JButton
     */
    private JButton getJButton1() {
        if (jButton1 == null) {
            jButton1 = new JButton();
            jButton1.setBounds(new Rectangle(18, 62, 96, 26));
            jButton1.setText("Jugar");
            jButton1.setVisible(false);
            jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

					/*PAlexis = new Alexis();

					PAlexis.start();*/
                    //Boton jugar
                    // Activamos los botones para el control del movimiento
                    ButUP.setVisible(true);
                    ButDown.setVisible(true);
                    ButRight.setVisible(true);
                    ButLeft.setVisible(true);
                    prueba.setVisible(true);
                    refrescar.setVisible(true);

                    Graphics g=jContentPane.getGraphics();


                    mundito[myPlayer.pos[0]].drawMap(jContentPane.getGraphics(), 180, 20);
                    myPlayer.drawPlayer(jContentPane.getGraphics());
                    //·························fin probar mapa···························
                    String charX="0";
                    String charY="0";
                    String charMapa="0";
                    String charimg="0";

                    if(myPlayer.pos[0]>9)charMapa="";
                    if(myPlayer.pos[1]>9)charX="";
                    if(myPlayer.pos[2]>9)charY="";
                    if(myPlayer.getSprite()>9)charimg="";
                    //mandar mis datos a los demas jugadores porque me conecte
                    conexionTopic.sendMessage("conectar-"+ myPlayer.getName()+"*"+charMapa+ myPlayer.pos[0]+charX+ myPlayer.pos[1]+charY+ myPlayer.pos[2]+charimg+ myPlayer.getSprite());
                    life.setVisible(true);
                    experience.setVisible(true);
                    level.setVisible(true);


                }
            });
        }
        return jButton1;
    }

    /**
     * This method initializes ButUP
     *
     * @return javax.swing.JButton
     */
    int cursor = 0;
    private JButton getButUP() {
        if (ButUP == null) {
            ButUP = new JButton();
            ButUP.setBounds(new Rectangle(45, 215, 17, 17));
            ButUP.setVisible(false);
            ButUP.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    //boton para ir para arriba
                    if(myPlayer.isFighting){//moverse sobre las opciones del combate
                        switch(cursor){
                            case 0:
                                guion1.setVisible(false);

                                if(myPlayer.isFightingVersusProfessor){//si esta en las preguntas solo hay tres opciones
                                    guion3.setVisible(true);
                                    cursor = 2;
                                }
                                else{
                                    guion5.setVisible(true);
                                    cursor=4;
                                }
                                break;
                            case 1:
                                guion2.setVisible(false);
                                guion1.setVisible(true);
                                cursor=0;
                                break;
                            case 2:
                                guion3.setVisible(false);
                                guion2.setVisible(true);
                                cursor=1;
                                break;
                            case 3:
                                guion4.setVisible(false);
                                guion3.setVisible(true);
                                cursor=2;
                                break;
                            case 4:
                                guion5.setVisible(false);
                                guion4.setVisible(true);
                                cursor=3;
                                break;
                        }
                    }
                    else{//nos movemos hacia arriba
                        PlayerUp();
                        String charX="0";
                        String charY="0";
                        String charMapa="0";

                        if(myPlayer.pos[0]>9)charMapa="";
                        if(myPlayer.pos[1]>9)charX="";
                        if(myPlayer.pos[2]>9)charY="";
                        //mandamos nuestra nueva posicion
                        conexionTopic.sendMessage("pos-"+ myPlayer.getName()+"*"+charMapa+ myPlayer.pos[0]+charX+ myPlayer.pos[1]+charY+ myPlayer.pos[2]);
                        drawOtherPlayers();
                        //Comprobamos si hay encuentro
                        encuentro();
                    }
                }
            });
        }
        return ButUP;
    }

    /**
     * This method initializes ButRight
     *
     * @return javax.swing.JButton
     */
    private JButton getButRight() {
        if (ButRight == null) {
            ButRight = new JButton();
            ButRight.setLocation(new Point(64, 233));
            ButRight.setSize(new Dimension(17, 17));
            ButRight.setVisible(false);
            ButRight.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    //conexionTopic.StartConnection();
                    if(!myPlayer.isFighting){//si no estoy en lucha
                        PlayerRight(); //nos movemos hacia la derecha
                        String charX="0";
                        String charY="0";
                        String charMapa="0";

                        if(myPlayer.pos[0]>9)charMapa="";
                        if(myPlayer.pos[1]>9)charX="";
                        if(myPlayer.pos[2]>9)charY="";
                        //envio las nuevas posiciones
                        conexionTopic.sendMessage("pos-"+ myPlayer.getName()+"*"+charMapa+ myPlayer.pos[0]+charX+ myPlayer.pos[1]+charY+ myPlayer.pos[2]);
                        drawOtherPlayers();
                        //comprobamos que no haya encuentro
                        encuentro();
                    }
                }
            });
        }
        return ButRight;
    }

    /**
     * This method initializes ButDown
     *
     * @return javax.swing.JButton
     */
    private JButton getButDown() {
        if (ButDown == null) {
            ButDown = new JButton();
            ButDown.setLocation(new Point(45, 233));
            ButDown.setSize(new Dimension(17, 17));
            ButDown.setVisible(false);
            ButDown.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    //Es lo mismo que en el boton ButUP pero en sentido contrario el movimiento
                    if(myPlayer.isFighting){
                        switch(cursor){
                            case 0:
                                guion1.setVisible(false);
                                guion2.setVisible(true);
                                cursor=1;
                                break;
                            case 1:
                                guion2.setVisible(false);
                                guion3.setVisible(true);
                                cursor=2;
                                break;
                            case 2:
                                guion3.setVisible(false);
                                if(myPlayer.isFightingVersusProfessor){
                                    guion1.setVisible(true);
                                    cursor=0;
                                }
                                else{
                                    guion4.setVisible(true);
                                    cursor=3;
                                }
                                break;
                            case 3:
                                guion4.setVisible(false);
                                guion5.setVisible(true);
                                cursor=4;
                                break;
                            case 4:
                                guion5.setVisible(false);
                                guion1.setVisible(true);
                                cursor=0;
                                break;
                        }
                    }
                    else{
                        PlayerDown();
                        String charX="0";
                        String charY="0";
                        String charMapa="0";

                        if(myPlayer.pos[0]>9)charMapa="";
                        if(myPlayer.pos[1]>9)charX="";
                        if(myPlayer.pos[2]>9)charY="";
                        conexionTopic.sendMessage("pos-"+ myPlayer.getName()+"*"+charMapa+ myPlayer.pos[0]+charX+ myPlayer.pos[1]+charY+ myPlayer.pos[2]);
                        drawOtherPlayers();
                        encuentro();
                    }
                }
            });
        }
        return ButDown;
    }

    /**
     * This method initializes ButLeft
     *
     * @return javax.swing.JButton
     */
    private JButton getButLeft() {
        if (ButLeft == null) {
            ButLeft = new JButton();
            ButLeft.setLocation(new Point(26, 233));
            ButLeft.setSize(new Dimension(17, 17));
            ButLeft.setVisible(false);
            ButLeft.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    //Esto es lo mismo que el boton ButRight pero en sentido contrario
                    if(!myPlayer.isFighting){
                        PlayerLeft();
                        String charX="0";
                        String charY="0";
                        String charMapa="0";

                        if(myPlayer.pos[0]>9)charMapa="";
                        if(myPlayer.pos[1]>9)charX="";
                        if(myPlayer.pos[2]>9)charY="";
                        conexionTopic.sendMessage("pos-"+ myPlayer.getName()+"*"+charMapa+ myPlayer.pos[0]+charX+ myPlayer.pos[1]+charY+ myPlayer.pos[2]);
                        drawOtherPlayers();
                        encuentro();
                    }
                }
            });
        }
        return ButLeft;
    }

    /**
     * This method initializes ConsolaComandos
     *
     * @return javax.swing.JTextField
     */
    //consola donde se escribe los mensajes a mandar
    private JTextField getConsoleCommands() {
        if (consoleCommands == null) {
            consoleCommands = new JTextField();
            consoleCommands.setBounds(new Rectangle(22, 579, 519, 19));
        }
        return consoleCommands;
    }

    /**
     * This method initializes console
     *
     * @return javax.swing.JEditorPane
     */
    //consola donde se imprime los mensajes del chat
    private JEditorPane getConsole() {
        if (console == null) {
            console = new JEditorPane();
            console.setBounds(new Rectangle(23, 509, 607, 65));
            console.setFont(new Font("Dialog", Font.PLAIN, 12));
            console.setEditable(false);
        }
        return console;
    }

    /**
     * This method initializes Enviar
     *
     * @return javax.swing.JButton
     */
    //boton para enviar un texto al chat
    private JButton getEnviar() {
        if (Enviar == null) {
            Enviar = new JButton();
            Enviar.setBounds(new Rectangle(555, 578, 76, 19));
            Enviar.setName("enviar");
            Enviar.setText("Enviar");
            Enviar.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    conexionTopic.sendMessage("chat-"+ myPlayer.getName()+"*"+consoleCommands.getText());
                }
            });
        }
        return Enviar;
    }

    /**
     * This method initializes jButton32
     *
     * @return javax.swing.JButton
     */
    DataBase enlace;
    private JButton jButton2 = null;
    private JTextField nombrereg = null;
    private JLabel lnombrereg = null;
    private JLabel lsexoreg = null;
    private JLabel lbandoreg = null;
    private JLabel lcasareg = null;
    private JLabel limagenreg = null;
    private JComboBox sexoreg = null;
    private JComboBox bandoreg = null;
    private JComboBox casareg = null;
    private JComboBox imagenreg = null;
    private JButton Crear = null;
    JLabel guion1 = null;
    JLabel guion2 = null;
    JLabel guion3 = null;
    JLabel Atacar = null;
    JLabel Pocion = null;
    JLabel Veneno = null;
    JLabel Maldición_Imperdonable = null;
    JLabel guion4 = null;
    JLabel huir = null;
    JLabel guion5 = null;
    JLabel life = null;
    JButton accioncombate = null;
    JLabel level = null;
    JLabel experience = null;
    JLabel vida_rival = null;
    JLabel exp_rival = null;
    private JButton getJButton32() {
        if (jButton32 == null) {
            jButton32 = new JButton();
            jButton32.setBounds(new Rectangle(301, 181, 105, 28));
            jButton32.setText("Conectar");
            jButton32.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    conexionTopic = new ConnectionManager();
                    conexionTopic.startConnection();//iniciamos la coneccion
                    jButton32.setVisible(false);
                    nameInput.setVisible(false);
                    nombre1.setVisible(false);
                    jButton2.setVisible(false);
                    console.setText("conectando");
                    conexionTopic.sendMessage("bd-"+nameInput.getText());
                    enlace = new DataBase();
                    enlace.start();//lanzamos el hilo que recibe los datos de la bd
                }
            });
        }
        return jButton32;
    }

    /**
     * This method initializes nombre
     *
     * @return javax.swing.JTextField
     */
    private JTextField getNameInput() {
        if (nameInput == null) {
            nameInput = new JTextField();
            nameInput.setBounds(new Rectangle(300, 142, 108, 22));
        }
        return nameInput;
    }

    /**
     * This method initializes jButton2
     *
     * @return javax.swing.JButton
     */
    //boton que te lleva a crear el personaje
    private JButton getJButton2() {
        if (jButton2 == null) {
            jButton2 = new JButton();
            jButton2.setText("Registrar");
            jButton2.setBounds(new Rectangle(461, 141, 97, 28));
            jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    jButton32.setVisible(false);
                    nameInput.setVisible(false);
                    nombre1.setVisible(false);
                    jButton2.setVisible(false);

                    lnombrereg.setVisible(true);
                    lsexoreg.setVisible(true);
                    lbandoreg.setVisible(true);
                    lcasareg.setVisible(true);
                    //limagenreg.setVisible(true);
                    nombrereg.setVisible(true);
                    sexoreg.setVisible(true);
                    bandoreg.setVisible(true);
                    casareg.setVisible(true);
                    //imagenreg.setVisible(true);

                    Crear.setVisible(true);

                }
            });
        }
        return jButton2;
    }

    /**
     * This method initializes nombrereg
     *
     * @return javax.swing.JTextField
     */
    private JTextField getNombrereg() {
        if (nombrereg == null) {
            nombrereg = new JTextField();
            nombrereg.setBounds(new Rectangle(298, 46, 99, 22));
            nombrereg.setVisible(false);
        }
        return nombrereg;
    }

    /**
     * This method initializes sexoreg
     *
     * @return javax.swing.JComboBox
     */
    private JComboBox getSexoreg() {
        if (sexoreg == null) {
            sexoreg = new JComboBox();
            sexoreg.setBounds(new Rectangle(300, 78, 96, 15));
            sexoreg.setSelectedIndex(-1);
            sexoreg.setVisible(false);
            sexoreg.addItem("Chico");
            sexoreg.addItem("Chica");
        }
        return sexoreg;
    }

    /**
     * This method initializes bandoreg
     *
     * @return javax.swing.JComboBox
     */
    private JComboBox getBandoreg() {
        if (bandoreg == null) {
            bandoreg = new JComboBox();
            bandoreg.setBounds(new Rectangle(301, 106, 94, 20));
            bandoreg.setVisible(false);
            bandoreg.addItem("Auror");
            bandoreg.addItem("Mortífago");
        }
        return bandoreg;
    }
    /**
     * This method initializes casareg
     *
     * @return javax.swing.JComboBox
     */
    private JComboBox getCasareg() {
        if (casareg == null) {
            casareg = new JComboBox();
            casareg.setBounds(new Rectangle(300, 136, 95, 21));
            casareg.setVisible(false);
            casareg.addItem("Gryffindor");
            casareg.addItem("Slytherin");
            casareg.addItem("Hufflepuf");
            casareg.addItem("Ravenclaw");
        }
        return casareg;
    }
    /**
     * This method initializes imagenreg
     *
     * @return javax.swing.JComboBox
     */
    private JComboBox getImagenreg() {
        if (imagenreg == null) {
            imagenreg = new JComboBox();
            imagenreg.setBounds(new Rectangle(299, 168, 98, 18));
            imagenreg.setVisible(false);
            imagenreg.addItem("Harry");
            imagenreg.addItem("Malfoy");
            imagenreg.addItem("Ron");
            imagenreg.addItem("Hermione");
            imagenreg.addItem("Cedric");
            imagenreg.addItem("Lunática");
        }
        return imagenreg;
    }

    int [][] imagenes_jug = new int[4][2];
    private JLabel opcion1 = null;
    private JLabel opcion2 = null;
    private JLabel opcion3 = null;
    private JLabel pregunta = null;
    private JButton prueba = null;
    private JButton refrescar = null;
    /**
     * This method initializes Crear
     *
     * @return javax.swing.JButton
     */
    //boton para crear el personaje con las acciones seleccionas
    private JButton getCrear() {
        if (Crear == null) {
            Crear = new JButton();
            Crear.setBounds(new Rectangle(300, 212, 100, 23));
            Crear.setText("Crear");
            Crear.setVisible(false);
            Crear.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    conexionTopic = new ConnectionManager();
                    conexionTopic.startConnection();

                    System.out.println("newbd-"+nombrereg.getText()+"*"+sexoreg.getSelectedIndex()+bandoreg.getSelectedIndex()+casareg.getSelectedIndex()+imagenreg.getSelectedIndex());
                    //matriz que te dice la casa
                    imagenes_jug[0][0] = 0;
                    imagenes_jug[0][1] = 1;
                    imagenes_jug[1][0] = 2;
                    imagenes_jug[1][1] = 3;
                    imagenes_jug[2][0] = 4;
                    imagenes_jug[2][1] = 5;
                    imagenes_jug[3][0] = 6;
                    imagenes_jug[3][1] = 7;

                    //se le envia a la bd los datos para su agregacion
                    conexionTopic.sendMessage("newbd-"+nombrereg.getText()+"*"+sexoreg.getSelectedIndex()+bandoreg.getSelectedIndex()+casareg.getSelectedIndex()+imagenes_jug[casareg.getSelectedIndex()][sexoreg.getSelectedIndex()]);

                    jButton32.setVisible(true);
                    nameInput.setVisible(true);
                    nombre1.setVisible(true);
                    jButton2.setVisible(true);

                    lnombrereg.setVisible(false);
                    lsexoreg.setVisible(false);
                    lbandoreg.setVisible(false);
                    lcasareg.setVisible(false);
                    limagenreg.setVisible(false);
                    nombrereg.setVisible(false);
                    sexoreg.setVisible(false);
                    bandoreg.setVisible(false);
                    casareg.setVisible(false);
                    //imagenreg.setVisible(false);
                    Crear.setVisible(false);
                }
            });
        }
        return Crear;
    }
    /**
     * This method initializes accioncombate
     *
     * @return javax.swing.JButton
     */
    //procedimiento cuando se pierde contra una criatura actua igual que con perder_combate
    void perdercombate_Criatura(){
        if (myPlayer.getFaction()==0){
            myPlayer.pos[0]=3;
        }
        else{
            myPlayer.pos[0]=5;
        }
        myPlayer.pos[1]=7;
        myPlayer.pos[2]=7;
        myPlayer.health=100;

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

        mundito[myPlayer.pos[0]].drawMap(jContentPane.getGraphics(), 180, 20);
        drawOtherPlayers();
        myPlayer.drawPlayer(jContentPane.getGraphics());
        conexionTopic.sendMessage("pos-"+ myPlayer.getName()+"*"+"0"+ myPlayer.pos[0]+"0"+ myPlayer.pos[1]+"0"+ myPlayer.pos[2]);
        //conexionTopic.SendMessage("accion-"+myPlayer.enemy+"*"+6);
        life.setText("Vida: " + myPlayer.health);

        creatures[myPlayer.enemyId].isFighting = false;
        myPlayer.isFighting = false;
        myPlayer.isFightingVersusCriature = false;


    }

    //	procedimiento cuando se gana contra una criatura actua igual que con ganar_combate
    void ganarcombate_Criatura(){
        myPlayer.experience+=5*creatures[myPlayer.enemyId].level;
        if (myPlayer.experience>50* myPlayer.level* myPlayer.level){
            myPlayer.level++;
            myPlayer.experience=0;
        }

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

        mundito[myPlayer.pos[0]].drawMap(jContentPane.getGraphics(), 180, 20);
        drawOtherPlayers();
        myPlayer.drawPlayer(jContentPane.getGraphics());
        experience.setText("Exp: "+ myPlayer.experience);
        level.setText("Nivel: "+ myPlayer.level);
        creatures[myPlayer.enemyId].health = 50;
        creatures[myPlayer.enemyId].isFighting = false;
        myPlayer.isFighting = false;
        myPlayer.isFightingVersusCriature = false;

    }

    //procedimiento que te da el objeto si aciertas la pregunta
    void acertar(){
        console.setText("Has acertado la pregunta");

        Random randomGenerator = new Random();

        int azar = randomGenerator.nextInt(2);

        if(myPlayer.enemyId < 8){
            if (azar == 0){
                myPlayer.setPotions(myPlayer.getPotions()+1);
                //myPlayer.objetos[1]++;
                console.setText(console.getText()+" Ganas una pocion");
            }
            else{
                myPlayer.setPoison(myPlayer.getPoison()+1);
                //myPlayer.objetos[2]++;
                console.setText(console.getText()+" Ganas un veneno");
            }
        }
        else{

            if (myPlayer.level>10){
                myPlayer.setUnforgivableCurses(myPlayer.getUnforgivableCurses()+1);
                //myPlayer.objetos[3]++;
                console.setText(console.getText()+" Ganas maldición");
            }
            else{
                console.setText(console.getText()+" No tienes el poder suficiente para poseer una maldición");
            }
        }

        accioncombate.setVisible(false);
        opcion1.setVisible(false);
        guion1.setVisible(false);
        opcion2.setVisible(false);
        opcion3.setVisible(false);
        guion2.setVisible(false);
        guion3.setVisible(false);
        pregunta.setVisible(false);

        teachers[myPlayer.enemyId].isFighting = false;
        myPlayer.isFighting = false;
        myPlayer.isFightingVersusProfessor = false;

        mundito[myPlayer.pos[0]].drawMap(jContentPane.getGraphics(), 180, 20);
        drawOtherPlayers();
        myPlayer.drawPlayer(jContentPane.getGraphics());

    }
    //	procedimiento si fallas la pregunta
    void fallar(){
        console.setText("Has fallado la pregunta");

        accioncombate.setVisible(false);
        opcion1.setVisible(false);
        guion1.setVisible(false);
        opcion2.setVisible(false);
        opcion3.setVisible(false);
        guion2.setVisible(false);
        guion3.setVisible(false);
        pregunta.setVisible(false);

        mundito[myPlayer.pos[0]].drawMap(jContentPane.getGraphics(), 180, 20);
        drawOtherPlayers();
        myPlayer.drawPlayer(jContentPane.getGraphics());

        teachers[myPlayer.enemyId].isFighting = false;
        myPlayer.isFighting = false;
        myPlayer.isFightingVersusProfessor = false;

    }
    //boton que selecciona la accion que se ejecuta
    private JButton getAccioncombate() {
        if (accioncombate == null) {
            accioncombate = new JButton();
            accioncombate.setBounds(new Rectangle(29, 385, 126, 14));
            accioncombate.setText("Enviar Acción");
            accioncombate.setVisible(false);
            accioncombate.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if(myPlayer.isFightingVersusCriature){//si se lucha contra una criatura
                        switch (cursor)
                        {
                            case 0://se ataca a la criatura y se le resta vida
                                creatures[myPlayer.enemyId].health-= myPlayer.level+5;
                                vida_rival.setText("Vida: "+creatures[myPlayer.enemyId].health);
                                break;

                            case 1://Tomas pocion y aumenta tu vida
                                if(myPlayer.getPotions() > 0){
                                    myPlayer.health+=10;
                                    life.setText("Vida: " + myPlayer.health);
                                    //myPlayer.objetos[1]--;
                                    myPlayer.setPotions(myPlayer.getPotions()-1);
                                }
                                else{
                                    console.setText("No te quedan pociones");
                                }
                                break;

                            case 2://Veneno
                                if(myPlayer.getPoison() > 0){
                                    creatures[myPlayer.enemyId].health-= 20;
                                    vida_rival.setText("Vida: "+creatures[myPlayer.enemyId].health);
                                    //myPlayer.objetos[2]--;
                                    myPlayer.setPoison(myPlayer.getPoison()-1);
                                }
                                else{
                                    console.setText("No te queda veneno");
                                }
                                break;
                            case 3://Maldicion imperdonable
                                if(myPlayer.getUnforgivableCurses() > 0){
                                    creatures[myPlayer.enemyId].health=0;
                                    //myPlayer.objetos[3]--;
                                    myPlayer.setUnforgivableCurses(myPlayer.getUnforgivableCurses()-1);
                                }
                                else{
                                    console.setText("No tienes poder para lanzar la maldición");
                                }
                                break;
                            case 4:
                                //si eliges hiur sales del combate y vuelves al escenario
                                myPlayer.isFighting =false;
                                myPlayer.isFightingVersusCriature =false;
                                creatures[myPlayer.enemyId].isFighting =false;
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

                                mundito[myPlayer.pos[0]].drawMap(jContentPane.getGraphics(), 180, 20);
                                drawOtherPlayers();
                                myPlayer.drawPlayer(jContentPane.getGraphics());
                                break;
                        }
                        //ataque de la criatura
                        System.out.println("Acción enviada: "+cursor);
                        if (creatures[myPlayer.enemyId].health > 0){
                            console.setText("Te han atacado");
                            myPlayer.health-=(creatures[myPlayer.enemyId].level+5);
                            life.setText("Salud: " + myPlayer.health);

                        }
                        //sino le queda vida ganas
                        else {
                            ganarcombate_Criatura();

                        }
                        //si no te queda a ti pierdes
                        if(myPlayer.health <=0){
                            perdercombate_Criatura();

                        }

                    }
                    else{
                        if(myPlayer.isFightingVersusProfessor){ //opciones cuando responde a la pregunta
                            switch (cursor)
                            {
                                //segun la solucion y la elelccion vemos si acierta o falla
                                case 0:
                                    if (soluciones[myPlayer.pregunta]==1){
                                        acertar();

                                    }
                                    else{
                                        fallar();

                                    }

                                    break;

                                case 1:
                                    if (soluciones[myPlayer.pregunta]==2){
                                        acertar();

                                    }
                                    else{
                                        fallar();

                                    }
                                    break;

                                case 2:
                                    if (soluciones[myPlayer.pregunta]==3){
                                        acertar();

                                    }
                                    else{
                                        fallar();

                                    }
                                    break;
                            }
                        }
                        else{//combate contra otro usuario
                            if((cursor != 3)||(cursor==3 && myPlayer.level>10)){//si no tienes el nivel para ejecutar maldicion no entras
                                conexionTopic.sendMessage("accion-"+ myPlayer.enemy +"*"+cursor);//envias la accion
                                switch (cursor)
                                {
                                    case 0://si es cero restas la vida del contrario
                                        game.gamePlayers[myPlayer.enemyId].health-= myPlayer.level+5;
                                        vida_rival.setText("Vida: "+game.gamePlayers[myPlayer.enemyId].health);
                                        break;

                                    case 1://si es uno bebes pocion siempre que tengas
                                        if(myPlayer.getPotions() > 0){
                                            myPlayer.health+=10;
                                            life.setText("Vida: " + myPlayer.health);
                                            //myPlayer.objetos[1]--;
                                            myPlayer.setPotions(myPlayer.getPotions()-1);
                                        }
                                        else{
                                            console.setText("No te quedan pociones");
                                        }
                                        break;

                                    case 2:////si es dos lanzas veneno siempre que tengas
                                        if(myPlayer.getPoison() > 0){
                                            game.gamePlayers[myPlayer.enemyId].health-= 20;
                                            vida_rival.setText("Vida: "+game.gamePlayers[myPlayer.enemyId].health);
                                            //myPlayer.objetos[2]--;
                                            myPlayer.setPoison(myPlayer.getPoison()-1);
                                        }
                                        else{
                                            console.setText("No te queda veneno");
                                        }
                                        break;
                                    case 3://lanzastes Maldicion imperdonable si puedes
                                        if(myPlayer.getUnforgivableCurses() > 0){
                                            game.gamePlayers[myPlayer.enemyId].health=0;
                                            //myPlayer.objetos[3]--;
                                            myPlayer.setUnforgivableCurses(myPlayer.getUnforgivableCurses()-1);
                                        }
                                        else{
                                            console.setText("No tienes poder para lanzar la maldición");
                                        }
                                        break;
                                    case 4:
                                        //opcion de huir vuelves al escenario principal
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

                                        mundito[myPlayer.pos[0]].drawMap(jContentPane.getGraphics(), 180, 20);
                                        drawOtherPlayers();
                                        myPlayer.drawPlayer(jContentPane.getGraphics());
                                        myPlayer.isFighting =false;


                                        break;
                                }
                                System.out.println("Acción enviada: "+cursor);
                            }
                            else console.setText("No puedes enviar una maldicion imperdonable");
                        }
                    }
                }
            });
        }
        return accioncombate;
    }
    /**
     * This method initializes prueba
     *
     * @return javax.swing.JButton
     */
    private JButton getPrueba() {
        if (prueba == null) {
            prueba = new JButton();
            prueba.setBounds(new Rectangle(24, 485, 85, 17));
            prueba.setVisible(false);
            prueba.setText("Guardar");
            prueba.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    //boton que guarda el estado actual del personaje
                    String charMapa="0";
                    String charX="0";
                    String charY="0";
                    String charvida1="0";
                    String charvida2="0";
                    String charvida3="0";
                    String charnivel="0";

                    if(myPlayer.pos[0] > 9) charMapa = "";
                    if(myPlayer.pos[1] > 9) charX = "";
                    if(myPlayer.pos[2] > 9) charY = "";
                    if(myPlayer.health > 9) charvida1 = "";
                    if(myPlayer.health > 99) charvida2 = "";
                    if(myPlayer.health > 999) charvida3 = "";
                    if(myPlayer.level > 9) charnivel = "";
                    //enviamos los datos a la base de datos
                    conexionTopic.sendMessage("desconectar-"+ myPlayer.getName()+"*"+charMapa+ myPlayer.pos[0]+charX+ myPlayer.pos[1]+charY+ myPlayer.pos[2]+ myPlayer.getFaction()+charvida3+charvida2+charvida1+ myPlayer.health+charnivel+ myPlayer.level+ myPlayer.getWand()+ myPlayer.getPotions()+ myPlayer.getPoison()+ myPlayer.getUnforgivableCurses()+ myPlayer.experience);
                }
            });
        }
        return prueba;
    }

    /**
     * This method initializes refrescar
     *
     * @return javax.swing.JButton
     */
    private JButton getRefrescar() {
        if (refrescar == null) {
            refrescar = new JButton();
            refrescar.setBounds(new Rectangle(20, 451, 109, 16));
            refrescar.setText("Refrescar");
            refrescar.setVisible(false);
            refrescar.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    //boton para refrescar la imagen
                    refresh();
                }
            });
        }
        return refrescar;
    }


}  //  @jve:decl-index=0:visual-constraint="18,2"
