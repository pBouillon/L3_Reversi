package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public GameMenu(){
        super("Game") ;
        items() ;
    }

    private void items() {

        // load game
        JMenuItem load = new JMenuItem("Load") ;
        load.setToolTipText("Load game");
        this.add(load) ;
        load.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {

            }});

        // quit
        JMenuItem quit = new JMenuItem("Quit") ;
        quit.setToolTipText("Quit game");
        this.add(quit) ;
        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }});

        // restart game
        JMenuItem restart = new JMenuItem("Restart") ;
        restart.setToolTipText("Restart a new game");
        this.add(restart) ;
        restart.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {

            }});

        // rules
        JMenuItem rules = new JMenuItem("Rules") ;
        rules.setToolTipText("show game's rules");
        this.add(rules) ;
        rules.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,rulesText, "Rules", JOptionPane.INFORMATION_MESSAGE);
            }});

        // save game
        JMenuItem save = new JMenuItem("Save") ;
        save.setToolTipText("Save this game");
        this.add(save) ;
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {

            }});


    }
}
