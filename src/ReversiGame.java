import reversi.Reversi;
import views.*;

import javax.swing.*;

import java.awt.*;
import java.util.Arrays;

public class ReversiGame extends JFrame {

    private ReversiGame() {
        super ("Reversi") ;

        Reversi reversi ;

        // Reversi environment
        String[] choices = {
                "Player vs Player",
                "Player vs AI",
                "AI vs AI"
        } ;
        String input = null ;
        while (input == null) {
            input = (String) JOptionPane.showInputDialog(
                    this,
                    "Game mode: ",
                    "Reversi initialization",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[0]
            ) ;
        }

        String p1 = null, p2 = null ;
        reversi = new Reversi() ;

        switch (input) {
            case "Player vs Player":
                while (p1 == null || p1.length() < 1) {
                    p1 = JOptionPane.showInputDialog(
                            this,
                            "Player name # 1 (black)"
                    ) ;
                }

                while (p2 == null || p2.length() < 1) {
                    p2 = JOptionPane.showInputDialog(
                            this,
                            "Player name # 2 (white)"
                    );
                    reversi = new Reversi(p1, p2) ;
                }
                break ;
            case "Player vs AI":
                while (p1 == null || p1.length() < 1) {
                    p1 = JOptionPane.showInputDialog(
                            this,
                            "Player name # 1 (black)"
                    ) ;
                }
                reversi = new Reversi(p1) ;
                break ;
        }

        // initialization
        setSize(new Dimension(1153, 750)) ;

        // components
        JMenuBar jmb = new JMenuBar()  ;
        jmb.add (new GameMenu(reversi)) ;

        add (
                new GameView(reversi),
                BorderLayout.CENTER
        ) ;
        add (
                jmb,
                BorderLayout.NORTH
        ) ;


        setLocationRelativeTo(null) ;
        setVisible(true) ;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE) ;

        if (reversi.currentPlayerIsAi()) {
            reversi.getCurrentPlayer().play(0, 0) ;
        }
    }

    public static void main(String[] args){
        new ReversiGame() ;
    }
}
