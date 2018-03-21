package gamestate;

import player.Player;
import reversi.GameColor;

import java.util.ArrayList;

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

/**
 * class ReversiGS
 *
 * @author pBouillon
 * @author lhome1u
 */
public class ReversiGS extends GameState {

    private final int AXES_DEC = -1 ;
    private final int AXES_EQU =  0 ;
    private final int AXES_INC =  1 ;

    private GameColor[][] board ;
    private ArrayList<ReversiGS> successors ;

    public ReversiGS(Player _currentPlayer, GameColor[][] _board) {
        super(_currentPlayer) ;
        board = _board ;
    }

    public ArrayList<ReversiGS> getSuccessors() {
        if (successors == null) genSuccessors() ;
        return successors ;
    }

    private void genSuccessors() {
        ReversiGS tmp ;
        successors = new ArrayList<>() ;

        GameColor color = getCurrentPlayer().getColor() ;

        // for each cells
        for (int x = 0; x < getBoard().length ; ++x) {
            for (int y = 0; y < getBoard()[0].length; ++y) {
                if (getBoard()[x][y] != GameColor.Empty) continue ;

                for (int stepX = AXES_DEC; stepX <= AXES_INC; ++stepX) {
                    for (int stepY = AXES_DEC; stepY <= AXES_INC; ++stepY) {

                        // if we are looking at the same tile without moving
                        if (stepX == AXES_EQU && stepY == AXES_EQU) continue ;

                        // else, check move and add it if it is OK
                        if (isMoveOk (x, y, stepX, stepY, color)) {
                            tmp = clone() ;
                            tmp.updateCell(x, y, getOpponentColor()) ;
                            successors.add (tmp) ;
                        }
                    }
                }
            }
        }
    }

    private void updateCell(int x, int y, GameColor color) {
        board[x][y] = color ;
    }

    public boolean isFinal() {
        return false ;
    }

    private GameColor getOpponentColor() {
        return null ;
    }

    public GameColor[][] getBoard() {
        return board;
    }

    private GameColor[][] getClonedBoard() {
        return board.clone() ;
    }

    private void swapFrom (int x, int y, GameColor currentColor, GameColor opponentColor) {
        for (int stepX = AXES_DEC; stepX <= AXES_INC; ++stepX) {
            for (int stepY = AXES_DEC; stepY <= AXES_INC; ++stepY) {
                swap (
                        x,
                        y,
                        stepX,
                        stepY,
                        currentColor,
                        toSwap (x, y, stepX, stepY, opponentColor)
                ) ;
            }
        }
    }

    private void swap(int x, int y, int stepX, int stepY, GameColor color, int toSwap) {
        GameColor[][] grid = getBoard() ;

        // starting from the next cell
        x += stepX ; y += stepY ;

        for (int i = 0; i < toSwap; ++i) {
            grid[x][y] = color ;
            x += stepX ; y += stepY ;
        }
    }

    private int toSwap (int x, int y, int stepX, int stepY, GameColor oppositColor) {
        GameColor[][] grid = getBoard() ;

        // opponent's pieces met
        int opponentMet = 0 ;

        // starting from the next cell
        x += stepX ; y += stepY ;

        // browsing axis
        while (x > -1 && y > -1
                && x < grid.length
                && y < grid[0].length) {

            // empty cells means an incorrect axe
            if (grid[x][y] == GameColor.Empty)  return 0 ;

            // if current cell is an opponent piece
            else if (grid[x][y] == oppositColor) {
                if ((x == 0 && stepX == AXES_DEC)
                        || (y == 0 && stepY == AXES_DEC)
                        || (x == grid.length -1 && stepX == AXES_INC)
                        || (y == grid[0].length -1 && stepY == AXES_INC)) {
                    opponentMet = 0 ;
                }
                else ++opponentMet ;
            }

            // search is done
            else break ;

            // looking at next cell
            x += stepX ; y += stepY ;
        }
        return opponentMet ;
    }

    private boolean isMoveOk (int x, int y, int stepX, int stepY, GameColor color) {
        GameColor[][] grid = getBoard() ;

        // opponent's pieces met
        int opponentMet = 0 ;

        // starting from the next cell
        x += stepX ; y += stepY ;

        // browsing axis
        while (x > -1 && y > -1
                && x < grid.length
                && y < grid[0].length) {

            // empty cells means an incorrect move
            if (grid[x][y] == GameColor.Empty) break ;

            // if current cell is an opponent piece
            else if (grid[x][y] == color) ++opponentMet ;

            // if we land on our color and opponentMet is more than 0
            // we are surrounding opponent's pieces : OK
            else return opponentMet > 0 ;

            // looking at next cell
            x += stepX ; y += stepY ;
        }
        return false ;
    }

    @Override
    public ReversiGS clone() {
        return new ReversiGS(getCurrentPlayer(), getClonedBoard());
    }

    @Override
    public boolean equals(Object o) {
        // if other object is not a ReversiGS: not equals
        if (!(o instanceof ReversiGS)) return false ;

        // if the player is different: not equals
        if (!(((ReversiGS) o).getCurrentPlayer() == getCurrentPlayer())) return false ;

        // checking tiles
        for (int x = 0; x < board.length; ++x){
            for (int y = 0; y < board.length; ++y) {

                // if tiles are different: not equals
                if (board[x][y] != ((ReversiGS) o).getBoard()[x][y]) return false ;
            }
        }

        return true ;
    }
}
