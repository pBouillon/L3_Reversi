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

import java.util.ArrayList;
import java.util.Observable;

/**
 * class Reversi
 *
 * @author pBouillon
 * @author lhome1u
 */
public class Reversi extends Observable{

    private int boardSize ;

    private Player[] players ;
    private int currentPlayerIndex ;
    private ReversiGS currentGameState ;
    private int stucked = 0 ;

    private ArrayList<int[]> possibleTiles ;

    public Reversi (int size, String nameP1, String nameP2) {
        boardSize = size ;
        players = new Player[2] ;
        players[0] = new HumanPlayer(nameP1, GameColor.White, this) ;
        players[1] = new HumanPlayer(nameP2, GameColor.Black, this) ;
        createFirstGameState() ;
    }

    public Reversi (int size, String nameP1) {
        boardSize = size ;
        players = new Player[2] ;
        players[0] = new AIPlayer("AI", GameColor.White, this) ;
        players[1] = new HumanPlayer(nameP1, GameColor.Black, this) ;
        createFirstGameState() ;
    }

    public Reversi (int size) {
        boardSize = size ;
        players = new Player[2] ;
        players[0] = new AIPlayer("AI 1", GameColor.White, this) ;
        players[1] = new AIPlayer("AI 2", GameColor.Black, this) ;
        createFirstGameState() ;
    }

    public ReversiGS getCurrentGameState() {
        return currentGameState;
    }

    private void createFirstGameState(){
        GameColor[][] board = new GameColor[boardSize][boardSize] ;

        for (int x = 0; x < boardSize * boardSize; ++x) {
            board[x / boardSize][x % boardSize] = GameColor.Empty ;
        }

        // initialize the grid with the central pattern
        int center = boardSize / 2 ;
        board[center-1][ center ] = GameColor.Black ;
        board[ center ][center-1] = GameColor.Black ;
        board[center-1][center-1] = GameColor.White ;
        board[ center ][ center ] = GameColor.White ;

        // initialize first player
        currentPlayerIndex = 1 ;

        // initialize first GameState
        currentGameState = new ReversiGS(getCurrentPlayer(), board) ;
        possibleTiles = currentGameState.genSuccessors() ;

        update() ;
    }

    public void play(int x, int y) {
        // reboot stucked payer
        stucked = 0;

        // changing board
        currentGameState.updateCell (x, y, players[currentPlayerIndex].getColor()) ;

        // swap pieces
        currentGameState.swapFrom (x, y) ;

        //change player
        nextPlayer() ;

        // new state
        currentGameState = new ReversiGS (
                                getCurrentPlayer(),
                                currentGameState.getClonedBoard()
                            ) ;

        possibleTiles = currentGameState.genSuccessors() ;

        update() ;

        if (currentPlayerIsAi() && !isFinished()) {
            getCurrentPlayer().play(0, 0) ;
        }
    }

    private void nextPlayer() {
        ++currentPlayerIndex ;
        if (currentPlayerIndex == players.length) currentPlayerIndex = 0 ;
    }

    public Player getCurrentPlayer(){ return players[currentPlayerIndex] ;}

    private void update() {
        setChanged() ;
        notifyObservers() ;
        clearChanged() ;
    }

    public GameColor[][] getBoard() {
        return currentGameState.getBoard() ;
    }

    public boolean isMoveCorrect(int x, int y) {
        return currentGameState.moveAllowed (x, y) ;
    }

    public boolean isFinished() { return currentGameState.isFinished() ;}

    public int isWin() { return currentGameState.isWon() ;}

    public Player getOpponentPlayer() {
        return (currentPlayerIndex == 0)
            ? players[1]
            : players[0] ;
    }

    public boolean getStucked() { return currentGameState.isStocked() ;}

    public void stucked() {
        ++stucked ;

        //change player
        nextPlayer() ;

        // new state
        currentGameState = new ReversiGS (
                getCurrentPlayer(),
                currentGameState.getClonedBoard()
        ) ;

        possibleTiles = currentGameState.genSuccessors() ;

        update() ;
    }

    public boolean isStuckedGame() { return stucked == 2 ; }

    public boolean currentPlayerIsAi() {
        return getCurrentPlayer().isAi() ;
    }

    public void restart(int board) {
        boardSize = board ;
        createFirstGameState() ;
        update() ;
    }

    public ArrayList<int[]> getPossibleTiles() {
        return possibleTiles;
    }

    public int getBoardSize() {
        return boardSize ;
    }

    public void changeAIDepth(int depth) {
        if (players[0].isAi()) players[0].changeDepth(depth) ;
        if (players[1].isAi()) players[1].changeDepth(depth) ;
    }
}
