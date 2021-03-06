package views;

import reversi.GameColor;
import reversi.Reversi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class BoardView extends JPanel implements Observer {

    protected Reversi reversi ;

    private JButton[][] tiles ;

    BoardView(Reversi _reversi){
        super() ;
        reversi = _reversi ;

        setLayout (new GridLayout (reversi.getBoardSize(), reversi.getBoardSize())) ;
        setOpaque(false) ;

        // grid initialisation
        tiles = new JButton[reversi.getBoardSize()][reversi.getBoardSize()] ;
        generateTiles(reversi.getBoardSize());

        // add to observable
        reversi.addObserver(this);
    }

    private void generateTiles(int tilesNb) {
        GameColor[][] board = reversi.getBoard() ;
        Image img ;
        try {
            for (int x = 0; x < tilesNb; ++x) {
                for (int y = 0; y < tilesNb; ++y) {
                    tiles[x][y] = new JButton() ;
                    img = ImageIO.read (
                            getClass().getResource (
                                    "resources/img/" + board[x][y] + "_tile.png"
                            )
                    ) ;
                    tiles[x][y].setIcon (new ImageIcon(img));

                    int X = x ;
                    int Y = y ;
                    tiles[x][y].addActionListener(e -> {
                            if (reversi.isMoveCorrect(X, Y)) {
                                reversi.getCurrentPlayer().play (X, Y) ;
                            }
                        }
                    ) ;
                }
            }

            img = ImageIO.read (
                    getClass().getResource(
                            "resources/img/possible_tile.png"
                    )
            ) ;
            for (int[] p : reversi.getPossibleTiles()){
                tiles[p[0]][p[1]].setIcon (new ImageIcon(img)) ;
            }
        } catch (IOException e) {
            e.printStackTrace() ;
        }

        for (int x = 0; x < tilesNb * tilesNb; ++x) {
            add (tiles[x / tilesNb][x % tilesNb]) ;
        }
    }

     private void updateTiles() {

        GameColor[][] board = reversi.getBoard() ;

        Image img ;

        try {
            for (int x = 0; x < board.length; ++x) {
                for (int y = 0; y < board[0].length; ++y) {
                    img = ImageIO.read (
                            getClass().getResource(
                                    "resources/img/" + board[x][y] + "_tile.png"
                            )
                    ) ;
                    tiles[x][y].setIcon (new ImageIcon(img)) ;
                }
            }

            img = ImageIO.read (
                    getClass().getResource(
                            "resources/img/possible_tile.png"
                    )
            ) ;
            for (int[] p : reversi.getPossibleTiles()){
                tiles[p[0]][p[1]].setIcon (new ImageIcon(img)) ;
            }
        } catch (IOException e) {
            e.printStackTrace() ;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateTiles() ;
    }
}
