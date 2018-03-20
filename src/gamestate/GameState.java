package gamestate;

import player.Player;

public abstract class GameState {
    private Player currentPlayer ;

    public GameState(Player _currentPlayer) {
        currentPlayer = _currentPlayer ;
    }

    protected Player getCurrentPlayer() {
        return currentPlayer ;
    }
}
