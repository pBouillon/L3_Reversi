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
    private int currentPlayerIndex;

    public Reversi (String nameP1, String nameP2) {
        players = new Player[2] ;
        players[0] = new HumanPlayer(nameP1, GameColor.White, this) ;
        players[1] = new HumanPlayer(nameP2, GameColor.Black, this) ;
    }

    public Reversi (String nameP1) {
        players = new Player[2] ;
        players[0] = new HumanPlayer(nameP1, GameColor.White, this) ;
        players[1] = new AIPlayer("AI", GameColor.Black, this) ;
    }

    public Reversi () {
        players = new Player[2] ;
        players[0] = new AIPlayer("AI 1", GameColor.White, this) ;
        players[1] = new AIPlayer("AI 2", GameColor.Black, this) ;
    }

    public void play(int x, int y) {
        players[currentPlayerIndex].play(x, y) ;
        nextPlayer() ;
    }

    private void nextPlayer() {
        ++currentPlayerIndex ;
        if (currentPlayerIndex == players.length) currentPlayerIndex = 0 ;
    }

    public void update(){
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public GameColor[][] getBoard() {
        return board ;
    }
}
