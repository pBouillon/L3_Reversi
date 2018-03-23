package views;

import reversi.Reversi;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GameView extends JPanel implements Observer {

    protected BoardView grid ;
    protected StatView stat ;
    protected Reversi reversi ;

    public GameView(Reversi _reversi){
        super() ;

        reversi = _reversi ;

        // views initialization
        setLayout(new BorderLayout()) ;
        grid = new BoardView(reversi) ;
        add (grid, BorderLayout.CENTER) ;
        stat = new StatView(reversi) ;
        add (stat, BorderLayout.WEST) ;

        // add to observable
        reversi.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon image_background
                = new ImageIcon (
                getClass()
                        .getResource("resources/img/background.jpg")
        ) ;

        g.drawImage (
                image_background.getImage(),
                0,
                0,
                getWidth(),
                getHeight(),
                this
        ) ;
    }
}