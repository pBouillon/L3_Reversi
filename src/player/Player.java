package player;

import reversi.GameColor;
import reversi.Reversi;

public abstract class Player {
    private GameColor color ;
    private Reversi game ;
    private String name ;

    public Player (String _name, GameColor _color, Reversi _game) {
        color = _color ;
        game  = _game  ;
        name  = _name  ;
    }

    public abstract void play () ;

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
        return ((Player) obj).getColor() == getColor()
                && ((Player) obj).getName().equals(getName()) ;
    }
}
