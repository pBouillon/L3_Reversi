package views;

import reversi.GameColor;
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
        setPreferredSize(new Dimension(1153 / 3, 50 * reversi.getBoardSize())) ;
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
        int whiteScore, blackScore ;
        if (reversi.getCurrentPlayer().getColor() == GameColor.White) {
            whiteScore = reversi.getCurrentGameState().getCurrentPlayerScore() ;
            blackScore = reversi.getCurrentGameState().getOpponentScore() ;
        } else {
            blackScore = reversi.getCurrentGameState().getCurrentPlayerScore() ;
            whiteScore = reversi.getCurrentGameState().getOpponentScore() ;
        }
        gameStat.setText(
                String.join ("\n",
                        "Overall:",
                        "    Player: " + reversi.getCurrentPlayer().getName(),
                        "    Current color: "+ reversi.getCurrentPlayer().getColor() + "\n",
                        "Statistics: ",
                        "    Black: " + blackScore,
                        "    White: " + whiteScore
                )
        ) ;
    }

    @Override
    public void update(Observable o, Object arg) {
        setText();
    }
}
