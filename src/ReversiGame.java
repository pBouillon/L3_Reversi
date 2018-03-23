import reversi.Reversi;
import views.*;

import javax.swing.*;

import java.awt.*;

public class ReversiGame extends JFrame{

    private ReversiGame(){
        super("Reversi");
        Reversi reversi = new Reversi("Mathilde", "Pierre") ;

        //initialisation
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1153, 750));

        //view and menu
        add(new GameView(reversi) ,BorderLayout.CENTER) ;
        JMenuBar jmb = new JMenuBar() ;
        jmb.add(new GameMenu()) ;
        add(jmb ,BorderLayout.NORTH ) ;

        pack() ;
        setVisible(true);
    }

    public static void main(String[] a){
        new ReversiGame() ;
    }
}
