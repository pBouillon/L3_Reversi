package gamestate;

import player.Player;
import reversi.GameColor;

import java.util.ArrayList;

public class ReversiGS extends GameState {
    private GameColor[][] board ;
    private ArrayList<GameState> successors ;

    public ReversiGS(Player _currentPlayer, GameColor[][] _board) {
        super(_currentPlayer) ;
        board = _board ;
        successors = new ArrayList<>() ;
    }

    private void genSuccessors() {

    }

    private boolean isFinal() {
        return false ;
    }

    private GameColor getOpponentColor() {
        return null ;
    }

    public GameColor[][] getBoard() {
        return board;
    }

    private GameColor[][] cloneBoard() {
        return board.clone() ;
    }

    private void swap() {

    }

    private boolean isMoveOk() {
        return false ;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReversiGS)) return false ;
        if (!(((ReversiGS) o).getCurrentPlayer() == getCurrentPlayer())) return false ;
        for (int x = 0; x < board.length; ++x){
            for (int y = 0; y < board.length; ++y) {
                if (board[x][y] != ((ReversiGS) o).getBoard()[x][y]) {
                    return false ;
                }
            }
        }
        return false ;
    }
}
