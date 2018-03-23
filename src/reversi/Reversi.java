package reversi;

/*
 * MIT License
 *
 * Copyright (c) 2018 Pierre Bouillon, Mathilde L'Homé
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import gamestate.ReversiGS;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;

import java.util.Observable;

/**
 * class Reversi
 *
 * @author pBouillon
 * @author lhome1u
 */
public class Reversi extends Observable{

    public static final int SIZE_BOARD = 8;
    private GameColor[][] board ;

    private Player[] players ;
    private int currentPlayerIndex ;
    private ReversiGS currentGameState ;

    public Reversi (String nameP1, String nameP2) {
        players = new Player[2] ;
        players[0] = new HumanPlayer(nameP1, GameColor.White, this) ;
        players[1] = new HumanPlayer(nameP2, GameColor.Black, this) ;
        createFirstGameState() ;
    }

    public Reversi (String nameP1) {
        players = new Player[2] ;
        players[0] = new AIPlayer("AI", GameColor.White, this) ;
        players[1] = new HumanPlayer(nameP1, GameColor.Black, this) ;
        createFirstGameState() ;
    }

    public Reversi () {
        players = new Player[2] ;
        players[0] = new AIPlayer("AI 1", GameColor.White, this) ;
        players[1] = new AIPlayer("AI 2", GameColor.Black, this) ;
        createFirstGameState() ;
    }

    private void createFirstGameState(){

        board = new GameColor[SIZE_BOARD][SIZE_BOARD] ;

        for (int x = 0; x < SIZE_BOARD * SIZE_BOARD ; ++x) {
            board[x / SIZE_BOARD][x % SIZE_BOARD] = GameColor.Empty ;
        }

        // initialize the grid with the central pattern
        int center = SIZE_BOARD / 2 ;
        board[center-1][ center ] = GameColor.Black ;
        board[ center ][center-1] = GameColor.Black ;
        board[center-1][center-1] = GameColor.White ;
        board[ center ][ center ] = GameColor.White ;

        // initialize first player
        currentPlayerIndex = 1 ;

        // initialize first GameState
        currentGameState = new ReversiGS(getCurrentPlayer(), getClonedBoard()) ;
        currentGameState.genSuccessors() ;

        System.out.println(currentGameState.getSuccessors().size());

    }

    public void play(int x, int y) {

        // changing board
        board[x][y] = players[currentPlayerIndex].getColor() ;

        // swap pieces
        currentGameState.swapFrom (x, y) ;

        board = currentGameState.getClonedBoard() ;

        //change player
        nextPlayer() ;

        // new state
        currentGameState = new ReversiGS (
                                getCurrentPlayer(),
                                getClonedBoard()
                            ) ;
        currentGameState.genSuccessors() ;

        update() ;
    }

    private void nextPlayer() {
        ++currentPlayerIndex ;
        if (currentPlayerIndex == players.length) currentPlayerIndex = 0 ;
    }

    public Player getCurrentPlayer(){ return players[currentPlayerIndex] ;}

    public void update(){
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public GameColor[][] getBoard() {
        return board ;
    }

    public boolean isMoveCorrect(int x, int y) {
        return currentGameState.moveAllowed(x, y) ;
    }

    private GameColor[][] getClonedBoard() {
        GameColor[][] clone = new GameColor[board.length][board.length] ;
        for (int x = 0; x < clone.length; ++x) {
            for (int y = 0; y < clone.length; ++y) {
                clone[x][y] = board[x][y] ;
            }
        }
        return clone ;
    }

}
