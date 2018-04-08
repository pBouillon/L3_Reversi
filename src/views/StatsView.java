package views;

import reversi.Reversi;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class StatsView extends JPanel implements Observer {

    protected Reversi reversi ;
    private JTextField gameStat ;
    private JLabel currentPlayer ;

    StatsView(Reversi _reversi){
        super() ;

        reversi = _reversi ;
        setPreferredSize(new Dimension(1153 / 3, 50 * Reversi.SIZE_BOARD)) ;
        setOpaque(false) ;

        //initialisation
        setPlayer() ;
        setStatistics() ;

        // add to observable
        reversi.addObserver(this);
    }

    private void setPlayer() {
        currentPlayer = new JLabel("Player: " + reversi.getCurrentPlayer().getName()) ;

        currentPlayer.setForeground(Color.white) ;

        add (currentPlayer, BorderLayout.EAST) ;
    }

    private void setStatistics() {
        gameStat = new JTextField("Statistics : ") ;
        gameStat.setOpaque(false);
        gameStat.setForeground(Color.white) ;
        add(gameStat, BorderLayout.SOUTH) ;
    }

    @Override
    public void update(Observable o, Object arg) {
        currentPlayer.setText("Player: " + reversi.getCurrentPlayer().getName()) ;
    }
}
