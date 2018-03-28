package views;

import reversi.Reversi;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class StatView extends JPanel implements Observer {

    protected Reversi reversi ;
    private JPanel player ;
    private JTextField gameStat ;

    StatView(Reversi _reversi){
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
        player = new JPanel();
        player.add(new JLabel("Player: " + reversi.getCurrentPlayer().getName())) ;
        add(player, BorderLayout.EAST) ;
    }

    private void setStatistics() {
        gameStat = new JTextField("Statistics : ") ;
        add(gameStat, BorderLayout.SOUTH) ;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
