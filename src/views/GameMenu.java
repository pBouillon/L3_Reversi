package views;

import reversi.Reversi;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JMenu {

    private String rulesText = "                    REVERSI RULES \n " +
            "## color \"powers\"\n" +
            "\t * Black always start\n" +
            "\t * White has the parity (win in case of equality)\n" +
            "\n" +
            "## in game rules\n" +
            "\t * Piece switch color when placed between two pieces of another color\n" +
            "\t * Color switches can be applied on columns, rows and diagonals\n" +
            "\t * A placed piece can switch mutliples columns, rows and diagonals\n" +
            "\t * The switched pieces doesn't change the status of their nearby paws\n" +
            "\n" +
            "## make a move\n" +
            "\t * A piece must touch one or more opponent's piece\n" +
            "\t * No move possible -> turn skipped (called a 'pass')\n" +
            "\t * If an opponent pass, the other opponent has the parity while the first is loosing it\n" +
            "\t * If both opponents pass, the game end\n" +
            "\n" +
            "## end\n" +
            "\t * Game stop on:\n" +
            "\t     * Gameboard full\n" +
            " \t    * None of the players can play\n" +
            "\t * The winner is the player who has the more pieces of his color \n";

    private Reversi reversi ;

    public GameMenu(Reversi _reversi){
        super("Game") ;
        reversi = _reversi ;
        setItems() ;
    }

    private void setItems() {
        JMenuItem quit    = genItem ("Quit", "Quit game") ;
        JMenuItem restart = genItem ("Restart", "Restart a new game") ;
        JMenuItem rules   = genItem ("Rules", "Show game's rules") ;

        quit.addActionListener (e->
            System.exit(0)
        ) ;
        restart.addActionListener (e-> {
            reversi.restart() ;
        }) ;
        rules.addActionListener (e->
            JOptionPane.showMessageDialog (
                this,
                rulesText,
                "Rules",
                JOptionPane.INFORMATION_MESSAGE
        )) ;

        add(restart) ;
        add(rules)   ;
        addSeparator() ;
        add(quit) ;
    }

    private JMenuItem genItem (String title, String infoText) {
        JMenuItem item = new JMenuItem(title) ;
        item.setToolTipText (infoText) ;
        item.setPreferredSize (new Dimension (75, 25)) ;

        return item ;
    }
}
