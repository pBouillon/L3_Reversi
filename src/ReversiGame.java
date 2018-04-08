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
        String input = (String) JOptionPane.showInputDialog(
                    this,
                    "Game mode: ",
                    "Reversi initialization",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[0]
        ) ;
        if (input == null) System.exit(0) ;

        // Board size
        String size = JOptionPane.showInputDialog (
                this,
                "Board size (even number between 4 and 16): ",
                "Reversi initialization",
                JOptionPane.QUESTION_MESSAGE
        ) ;

        int board = 0 ;
        try {
            board = Integer.parseInt(size) ;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bad input, grid size is invalid",
                    "Reversi initialization",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }
        if (board % 2 != 0 || board < 4 || board > 16) {
            JOptionPane.showMessageDialog (
                    this,
                    "Bad input, grid size is invalid",
                    "Reversi initialization",
                    JOptionPane.ERROR_MESSAGE
            ) ;
            System.exit(1);
        }

        String p1 = null, p2 = null ;
        reversi = new Reversi(board) ;

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
                    reversi = new Reversi(board, p1, p2) ;
                }
                break ;
            case "Player vs AI":
                while (p1 == null || p1.length() < 1) {
                    p1 = JOptionPane.showInputDialog(
                            this,
                            "Player name # 1 (black)"
                    ) ;
                }
                reversi = new Reversi(board, p1) ;
                break ;
        }

        // initialization
        setSize(new Dimension(1150, 750)) ;
        setResizable(false);

        // components
        JMenuBar jmb = new JMenuBar()  ;
        GameView gv = new GameView(reversi) ;

        add (
                gv,
                BorderLayout.CENTER
        ) ;
        jmb.add (new GameMenu(reversi, gv)) ;
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
