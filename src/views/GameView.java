package views;

import reversi.Reversi;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GameView extends JPanel implements Observer {

    protected GridView grid ;
    protected StatView stat ;
    protected Reversi reversi ;

    public GameView(Reversi _reversi){
        super() ;

        reversi = _reversi ;

        //initialisation views
        setBackground(Color.RED);
        grid = new GridView(reversi) ;
        add(grid, BorderLayout.WEST) ;
        stat = new StatView(reversi) ;
        add(stat, BorderLayout.EAST) ;


        // add to observable
        reversi.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
