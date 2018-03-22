package views;

import reversi.Reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class GridView extends JPanel implements Observer {

    protected Reversi reversi ;

    public GridView(Reversi _reversi){
        super() ;
        reversi = _reversi ;

        setPreferredSize(new Dimension(400, 400));
        // grid initialisation
        generateTiles(reversi.SIZE_BOARD);

        // add to observable
        reversi.addObserver(this);
    }



    private void generateTiles(int tilesNb) {
        setLayout(new GridLayout(tilesNb, tilesNb));

        for (int x = 0; x < tilesNb * tilesNb; ++x) {
            JButton tile = new JButton() ;
            tile.setIcon(new ImageIcon("resources/imgs/empty_tile.png"));
            tile.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {

                }});
            add(tile) ;
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
