package views;

import reversi.GameColor;
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
        GameColor[][] board = reversi.getBoard() ;
        Image img ;
        try {
            for (int x = 0; x < tilesNb; ++x) {
                for (int y = 0; y < tilesNb; ++y) {
                    tiles[x][y] = new JButton() ;
                    img = ImageIO.read(getClass().getResource("resources/img/"+board[x][y]+"_tile.png"));
                    tiles[x][y].setIcon (new ImageIcon(img));

                    int X = x ;
                    int Y = y ;
                    tiles[x][y].addActionListener(e ->
                            reversi.getCurrentPlayer().play(X, Y)
                    ) ;
                }
            }
        } catch (IOException e) {
            e.printStackTrace() ;
        }

        for (int x = 0; x < tilesNb * tilesNb; ++x) add (tiles[x / tilesNb][x % tilesNb]) ;

    }

     private void updateTiles() {

        GameColor[][] board = reversi.getBoard() ;

        Image img ;
        try {
            for (int x = 0; x < board.length; ++x) {
                for (int y = 0; y < board[0].length; ++y) {
                    img = ImageIO.read(getClass().getResource("resources/img/"+board[x][y]+"_tile.png"));
                    tiles[x][y].setIcon (new ImageIcon(img));
                }
            }
        } catch (IOException e) {
            e.printStackTrace() ;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateTiles();

    }
}
