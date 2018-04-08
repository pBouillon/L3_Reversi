package player;

import gamestate.ReversiGS;
import reversi.GameColor;
import reversi.Reversi;

import java.util.concurrent.TimeUnit;

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
 *
 */

/**
 * class AIPlayer
 *
 * @author pBouillon
 * @author lhome1u
 */
public class AIPlayer extends Player {

    private static final int WON  = Integer.MAX_VALUE  ;
    private static final int LOOSE = Integer.MIN_VALUE ;
    private static final int DRAW = 0 ;

    private final int DEPTH = 3 ;

    public AIPlayer(String _name, GameColor _color, Reversi _game) {
        super(_name, _color, _game);
    }

    private int eval_final(ReversiGS g) {
        int currentScore  = 0 ;
        int opponentScore = 0 ;

        for (GameColor[] row : g.getBoard()) {
            for (GameColor tile : row) {
                if (tile == GameColor.Empty) continue ;

                if (tile == getColor()) ++currentScore  ;
                else ++opponentScore ;
            }
        }

        if (currentScore == opponentScore) return DRAW ;

        return (currentScore > opponentScore) ? WON
                : LOOSE ;
    }

    private int eval_0(ReversiGS g) {
        int currentScore  = 0 ;
        int opponentScore = 0 ;

        for (GameColor[] row : g.getBoard()) {
            for (GameColor tile : row) {
                if (tile == GameColor.Empty) continue ;

                if (tile == getColor()) ++currentScore  ;
                else ++opponentScore ;
            }
        }
        return  (currentScore - opponentScore) ;
    }

    private int eval_0_v2(ReversiGS g) {
        int currentScore  = 0 ;
        int opponentScore = 0 ;

        boolean isBadMove ;
        int boardSize  = g.getBoard().length ;
        int boardCenter = boardSize / 2 ;

        int curX, curY ;
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                if (g.getBoard()[i][j] == GameColor.Empty) continue ;

                curX = i % boardSize / 2 ;
                curY = j % boardSize / 2 ;

                /* if the move give access of the corner for the other player it's a bad move
                 * B = Bad
                 * O = Optimal
                 * K = ok
                 *
                 *  O | B | K | ...
                 * ---+---+---+
                 *  B | B | K | ...
                 * ---+---+---+
                 *  K | K | K | ...
                 * ... ... ...
                 */
                isBadMove =
                    // top left corner
                        i == 0 && j == 1
                        || i == 1 && j == 0
                        || i == 1 && j == 1
                    // bottom left corner
                        || i == boardSize - 1 && j == 1
                        || i == boardSize - 2 && j == 0
                        || i == boardSize - 2 && j == 1
                    // top right corner
                        || i == 0 && j == boardSize - 2
                        || i == 1 && j == boardSize - 1
                        || i == 1 && j == boardSize - 2
                    // bottom right corner
                        || i == boardSize - 1 && j == boardSize - 2
                        || i == boardSize - 2 && j == boardSize - 1
                        || i == boardSize - 2 && j == boardSize - 2 ;

                if (g.getBoard()[i][j] == getColor()) {
                    // applying a penality if the move is bad
                    if (isBadMove) currentScore -= boardSize ;
                    else {
                        currentScore += Math.abs(boardCenter - curX) ;
                        currentScore += Math.abs(boardCenter - curY) ;
                    }
                }
                else {
                    // applying a penality if the move is bad
                    if (isBadMove) currentScore -= boardSize ;
                    else {
                        opponentScore += Math.abs(boardCenter - curX) ;
                        opponentScore += Math.abs(boardCenter - curY) ;
                    }
                }
            }
        }

        return (currentScore - opponentScore) ;
    }

    private float eval_c(int c, ReversiGS g) {
        float scoreMax, scoreMin ;

        if (g.isFinal()) return eval_final (g) ;

//        if (c == 0) return eval_0(g) ;
        if (c == 0) return eval_0_v2(g) ;

        if (g.getCurrentPlayer().equals(this)){
            scoreMax = WON ;
            for (ReversiGS gs: g.getSuccessors()) {
                scoreMax = Math.max(scoreMax, eval_c(c-1, gs)) ;
            }
            return scoreMax ;
        }

        scoreMin = LOOSE ;
        for (ReversiGS gs: g.getSuccessors()) {
            scoreMin = Math.min(scoreMin, eval_c(c-1, gs)) ;
        }
        return scoreMin ;
    }

    private int eval_alpha_beta(int c, ReversiGS e, int alpha, int beta) {
        int evalTmp, scoreMax, scoreMin ;

        if (e.isFinal()) return eval_final (e) ;

//        if (c == 0) return eval_0 (e) ;
        if (c == 0) return eval_0_v2 (e) ;

        // our turn
        if (e.getCurrentPlayer() == this) {
            scoreMax = LOOSE ;

            for (ReversiGS s : e.getSuccessors()) {
                evalTmp = eval_alpha_beta(c - 1, s, alpha, beta) ;
                scoreMax = Math.max(scoreMax, evalTmp) ;

                if (scoreMax >= beta) return scoreMax ;
                alpha = Math.max(alpha, scoreMax) ;
            }
            return scoreMax ;
        }

        // other player's turn
        scoreMin = WON ;
        for (ReversiGS s : e.getSuccessors()) {
            evalTmp = eval_alpha_beta(c - 1, s, alpha, beta) ;
            scoreMin = Math.min(scoreMin, evalTmp) ;

            if (scoreMin <= alpha) return scoreMin ;
            beta = Math.min (beta, scoreMin) ;
        }

        return scoreMin ;
    }

    private ReversiGS minimax(int c, ReversiGS g) {
        float score, scoreMax ;

        scoreMax = LOOSE ;
        ReversiGS nextMove = null ;

        for (ReversiGS gs: g.getSuccessors()) {
//            score = eval_c(c, g) ;
            score = eval_alpha_beta(c, g, LOOSE, WON) ;
            if (score >= scoreMax) {
                nextMove = gs ;
                scoreMax = score ;
            }
        }
        return nextMove ;
    }

    @Override
    public void play(int x, int y) {
        try {
            TimeUnit.MILLISECONDS.sleep(250 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GameColor[][] board = minimax (
                DEPTH,
                getGame().getCurrentGameState()
        ).getBoard() ;

        int _x = -1 ;
        int _y = -1 ;
        for (int i = 0; i < getGame().getBoard().length; ++i) {
            for (int j = 0; j < getGame().getBoard().length; ++j) {
                if (board[i][j] != getGame().getBoard()[i][j]) {
                    _x = i ;
                    _y = j ;
                }
            }
        }
        getGame().play(_x, _y) ;
    }

    @Override
    public boolean isAi() {
        return true ;
    }
}
