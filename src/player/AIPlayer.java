package player;

import gamestate.ReversiGS;
import reversi.GameColor;
import reversi.Reversi;

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

    public AIPlayer(String _name, GameColor _color, Reversi _game) {
        super(_name, _color, _game);
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

        if (currentScore == opponentScore) return DRAW ;

        return (currentScore > opponentScore) ? WON
                : LOOSE ;
    }

    private float eval_c(int c, ReversiGS g) {
        float scoreMax, scoreMin ;

        if (g.isFinal()) return eval_0 (g) ;

        if (c == 0) return eval_0(g) ;

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

        if (e.isFinal()) return eval_0 (e) ;

        if (c == 0) return eval_0 (e) ;

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

        scoreMax = WON ;
        ReversiGS nextMove = null ;

        for (ReversiGS gs: g.getSuccessors()) {
            score = eval_c(c, g) ;
            if (score >= scoreMax) {
                nextMove = gs ;
                scoreMax = score ;
            }
        }
        return nextMove ;
    }

    @Override
    public void play () {

    }
}
