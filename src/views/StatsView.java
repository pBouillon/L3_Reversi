package views;

import reversi.Reversi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class StatsView extends JPanel implements Observer {

    protected Reversi reversi ;
    private JTextArea gameStat ;

    private final Font displayFont = new Font("Serif", Font.PLAIN, 23) ;

    StatsView(Reversi _reversi){
        super() ;

        reversi = _reversi ;
        setPreferredSize(new Dimension(1153 / 3, 50 * Reversi.SIZE_BOARD)) ;
        setBorder(new EmptyBorder(100, 50, 10, 10));

        setOpaque(false) ;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //initialisation
        setStatistics() ;
        setText();

        // add to observable
        reversi.addObserver(this);
    }

    private void setStatistics() {
        gameStat = new JTextArea() ;
        gameStat.setFont(displayFont) ;
        gameStat.setOpaque(false);
        gameStat.setForeground(Color.white) ;
        add(gameStat) ;
    }

    private void setText() {
        gameStat.setText(
                String.join ("\n",
                        "Player: " + reversi.getCurrentPlayer().getName(),
                        "Current color: "+ reversi.getCurrentPlayer().getColor() + "\n",
                        "Statistics: ",
                        "    Current  Score: " + reversi.getCurrentGameState().getCurrentPlayerScore(),
                        "    Opponent Score: " + reversi.getCurrentGameState().getOpponentScore()
                )
        ) ;
    }

    @Override
    public void update(Observable o, Object arg) {
        setText();
    }
}
