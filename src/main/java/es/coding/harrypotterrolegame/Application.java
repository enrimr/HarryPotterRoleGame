package es.coding.harrypotterrolegame;

/**
 * Created by Enri on 26/8/15.
 */
public class Application {


    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        System.out.print("HarryPotterRoleGame begin :D\n");
                        GameGUI.getInstance().setVisible(true);
                    }
                }
        );

    }

}
