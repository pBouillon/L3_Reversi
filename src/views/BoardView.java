package views;

import reversi.Reversi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class BoardView extends JPanel implements Observer {

    protected Reversi reversi ;

    private JButton[][] tiles ;

    BoardView(Reversi _reversi){
        super() ;
        reversi = _reversi ;

        setMaximumSize(new Dimension((1153 / 3) * 2, 50 * Reversi.SIZE_BOARD)) ;
        setOpaque(false) ;

        // grid initialisation
        tiles = new JButton[Reversi.SIZE_BOARD][Reversi.SIZE_BOARD] ;
        generateTiles(Reversi.SIZE_BOARD);

        // add to observable
        reversi.addObserver(this);
    }


    private void generateTiles(int tilesNb) {
        setLayout (new GridLayout (tilesNb, tilesNb)) ;

        Image img ;
        try {
            img = ImageIO.read(getClass().getResource("resources/img/empty_tile.png"));
            for (int x = 0; x < tilesNb; ++x) {
                for (int y = 0; y < tilesNb; ++y) {
                    tiles[x][y] = new JButton() ;
                    tiles[x][y].setIcon (new ImageIcon(img));

                    int X = x ;
                    int Y = y ;
                    tiles[x][y].addActionListener(e ->
                            reversi.play(X, Y)
                    ) ;
                }
            }
        } catch (IOException e) {
            e.printStackTrace() ;
        }

        try {
            Image blackTile, whiteTile ;
            blackTile = ImageIO.read(getClass().getResource("resources/img/black_tile.png")) ;
            whiteTile = ImageIO.read(getClass().getResource("resources/img/white_tile.png")) ;

            int center = tilesNb / 2 ;
            tiles[center-1][ center ].setIcon (new ImageIcon(blackTile)) ;
            tiles[ center ][center-1].setIcon (new ImageIcon(blackTile)) ;
            tiles[center-1][center-1].setIcon (new ImageIcon(whiteTile)) ;
            tiles[ center ][ center ].setIcon (new ImageIcon(whiteTile)) ;
        } catch (IOException e) {
            e.printStackTrace() ;
        }

        for (int x = 0; x < tilesNb * tilesNb; ++x) add (tiles[x / tilesNb][x % tilesNb]) ;

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
