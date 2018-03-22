package views;

import reversi.Reversi;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class StatView extends JPanel implements Observer {

    protected Reversi reversi ;
    protected JPanel player ;
    protected JTextField gameStat ;

    public StatView(Reversi _reversi){
        super() ;

        reversi = _reversi ;
        setPreferredSize(new Dimension(400, 550));

        //initialisation
        setPlayer() ;
        setStatistics() ;

        // add to observable
        reversi.addObserver(this);
    }

    private void setPlayer() {
        player = new JPanel();
        player.add(new JTextField("Nick : " )) ;
        //player.add(new ImageIcon("")) ;
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
