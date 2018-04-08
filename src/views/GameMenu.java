package views;

import reversi.Reversi;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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
    private GameView mainFrame ;

    public GameMenu(Reversi _reversi, GameView gv){
        super("Game") ;
        reversi = _reversi ;
        mainFrame = gv ;
        setItems() ;
    }

    private void setItems() {
        JMenuItem quit    = genItem ("Quit", "Quit game") ;
        JMenuItem restart = genItem ("Restart", "Restart a new game") ;
        JMenuItem rules   = genItem ("Rules", "Show game's rules") ;
        JMenuItem more   = genItem  ("More", "Get on the public repository") ;
        JMenuItem ai   = genItem    ("AI depth", "Change the AI difficlty") ;

        quit.addActionListener (e-> System.exit(0)
        ) ;
        restart.addActionListener (e-> {
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
            } catch (Exception ex) {
                JOptionPane.showMessageDialog (
                        mainFrame,
                        "Reversi initialization",
                        "Bad input, grid size is invalid",
                        JOptionPane.ERROR_MESSAGE
                ) ;
                System.exit(1);
            }
            if (board % 2 != 0 || board < 4 || board > 16) {
                JOptionPane.showMessageDialog (
                        mainFrame,
                        "Reversi initialization",
                        "Bad input, grid size is invalid",
                        JOptionPane.ERROR_MESSAGE
                ) ;
                System.exit(1);
            }
            reversi.restart(board) ;
        }) ;
        rules.addActionListener (e->
            JOptionPane.showMessageDialog (
                mainFrame,
                rulesText,
                "Rules",
                JOptionPane.INFORMATION_MESSAGE
        )) ;
        more.addActionListener(e -> {
            Object[] answers = new Object[]{"Yes get me to the repository", "No thanks"} ;
            int res = JOptionPane.showOptionDialog (
                    mainFrame,
                    "You are about to open an external link, proceed anyway?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    answers,
                    answers[0]
            ) ;

            if (res == JOptionPane.YES_OPTION) {
                try {
                    Desktop.getDesktop()
                            .browse (
                                    new URL (
                                            "https://github.com/pBouillon/L3_Reversi")
                                            .toURI()
                            ) ;
                } catch (Exception ex) {
                    ex.printStackTrace() ;
                }
            }
        }) ;

        ai.addActionListener(e -> {
            String size = JOptionPane.showInputDialog (
                    this,
                    "New AI depth > 0 (higher number will slow down the game): ",
                    "New AI depth",
                    JOptionPane.QUESTION_MESSAGE
            ) ;

            int depth = 0 ;
            try {
                depth = Integer.parseInt(size) ;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog (
                        mainFrame,
                        "Bad depth, aborting",
                        "New AI depth",
                        JOptionPane.ERROR_MESSAGE
                ) ;
            }
            if (depth < 0 || depth > 10) {
                JOptionPane.showMessageDialog (
                        mainFrame,
                        "Bad depth, aborting",
                        "New AI depth",
                        JOptionPane.ERROR_MESSAGE
                ) ;
            }
            reversi.changeAIDepth(depth) ;
        });

        add(restart) ;
        add(rules) ;
        addSeparator() ;
        add(ai) ;
        addSeparator() ;
        add(more) ;
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
