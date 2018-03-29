import reversi.Reversi;
import views.*;

import javax.swing.*;

import java.awt.*;

public class ReversiGame extends JFrame{

    private ReversiGame(){
        super("Reversi");
        Reversi reversi ;

        //initialisation
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1153, 750));

        //players
        Object[] options = {"Oui","Non"};
        int n = JOptionPane.showOptionDialog(this,
                "Player 1 is an AI ?",
                "Players",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if(n == JOptionPane.YES_NO_OPTION){
            n = JOptionPane.showOptionDialog(this,
                    "Player 2 is an AI ?",
                    "Players",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if(n == JOptionPane.YES_NO_OPTION){
                reversi = new Reversi() ;
            }
            else{
                String retour = JOptionPane.showInputDialog(this,
                                "Player 2, what is your name ?",
                                "Player2");

                reversi = new Reversi(retour) ;
            }
        }else{
            String retour1 = JOptionPane.showInputDialog(this,
                    "Player 1, what is your name ?",
                    "Player1");

            String retour2 = JOptionPane.showInputDialog(this,
                    "Player 2, what is your name ?",
                    "Player2");

            reversi = new Reversi(retour1, retour2);

        }

        //view and menu
        add(new GameView(reversi) ,BorderLayout.CENTER) ;
        JMenuBar jmb = new JMenuBar() ;
        jmb.add(new GameMenu(reversi)) ;
        add(jmb ,BorderLayout.NORTH ) ;

        //pack() ;
        setVisible(true);

        if (reversi.currentPlayerIsAi()) {
            reversi.getCurrentPlayer().play(0, 0);
        }
    }

    public static void main(String[] a){
        new ReversiGame() ;
    }
}
