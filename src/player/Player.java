package player;

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
 */

/**
 * class Player
 *
 * @author pBouillon
 * @author lhome1u
 */
public abstract class Player {
    private GameColor color ;
    private Reversi game ;
    private String name ;

    public Player (String _name, GameColor _color, Reversi _game) {
        color = _color ;
        game  = _game  ;
        name  = _name  ;
    }

    public abstract void play(int x, int y) ;

    public String getName () {
        return name ;
    }

    public GameColor getColor() {
        return color ;
    }

    public Reversi getGame() {
        return game ;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Player
                &&((Player) obj).getColor() == getColor()
                && ((Player) obj).getName().equals(getName()) ;
    }

    public abstract boolean isAi() ;
}
