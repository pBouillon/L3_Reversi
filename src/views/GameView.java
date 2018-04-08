package views;

import reversi.GameColor;
import reversi.Reversi;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GameView extends JPanel implements Observer {

    private static final int WIN = 1;
    private static final int EQUALS = 0;
    private static final int LOOSE = -1;

    private BoardView grid ;
    private StatsView stat ;
    protected Reversi reversi ;

    public GameView(Reversi _reversi){
        super() ;

        reversi = _reversi ;

        // views initialization
        grid = new BoardView(reversi) ;
        stat = new StatsView(reversi) ;

        setLayout(new BorderLayout()) ;

        add (grid, BorderLayout.CENTER) ;
        add (stat, BorderLayout.WEST) ;

        // add to observable
        reversi.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (reversi.getStucked()) {
            if (!reversi.getCurrentPlayer().isAi()){
                JOptionPane.showMessageDialog (
                        null,
                        reversi.getCurrentPlayer().getName() + ", you are stucked ! \n\n\t "+
                                reversi.getOpponentPlayer().getName() + ", it's your turn ! ",
                        "Stuck",
                        JOptionPane.PLAIN_MESSAGE
                ) ;
            }
            reversi.stucked() ;

            if (reversi.isStuckedGame()){
                JOptionPane.showMessageDialog (
                        null,
                        "All players are stucked ! \n It's the end of this game !",
                        "End !",
                        JOptionPane.PLAIN_MESSAGE
                ) ;
            }
        } else if (reversi.isFinished()) {
            switch (reversi.isWin()) {
                case WIN :
                    JOptionPane.showMessageDialog (
                            this,
                            reversi.getCurrentPlayer().getName() +", you win this game ! \n\n\t "+
                                    reversi.getOpponentPlayer().getName()+" loose ! ",
                            "End !",
                            JOptionPane.PLAIN_MESSAGE
                    ) ;
                    break;
                case EQUALS :
                    String whitePlayerName = (reversi.getCurrentPlayer().getColor() == GameColor.White)
                            ? reversi.getCurrentPlayer().getName()
                            : reversi.getOpponentPlayer().getName() ;

                    JOptionPane.showMessageDialog (
                            this,
                            "This is a draw, " + whitePlayerName + " wins (white has parity, see rules for more)",
                            "Draw !",
                            JOptionPane.PLAIN_MESSAGE
                    ) ;
                    break;
                case LOOSE :
                    JOptionPane.showMessageDialog (
                            this,
                            reversi.getCurrentPlayer().getName() +", you loose this game ! \n\n\t "+
                                    reversi.getOpponentPlayer().getName()+" won ! ",
                            "End !",
                            JOptionPane.PLAIN_MESSAGE
                    ) ;
                    break ;
            }
        }
        if (reversi.isFinished() || reversi.isStuckedGame()) {
            Object[] answers = new Object[]{"Yes another one !", "No thanks"} ;
            int res = JOptionPane.showOptionDialog (
                    this,
                    "The game ended, do you want to play again?",
                    "End",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    answers,
                    answers[0]
            ) ;
            if (res == JOptionPane.YES_OPTION) {
                // Board size
                String size = JOptionPane.showInputDialog (
                        this,
                        "Board size (even number between 4 and 16): ",
                        "Reversi initialization",
                        JOptionPane.QUESTION_MESSAGE
                ) ;

                int board = 0 ;
                try {
                    board = Integer.parseInt(size) ;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog (
                            this,
                            "Reversi initialization",
                            "Bad input, grid size is invalid",
                            JOptionPane.ERROR_MESSAGE
                    ) ;
                    System.exit(1);
                }
                if (board % 2 != 0 || board < 4 || board > 16) {
                    JOptionPane.showMessageDialog (
                            this,
                            "Reversi initialization",
                            "Bad input, grid size is invalid",
                            JOptionPane.ERROR_MESSAGE
                    ) ;
                    System.exit(1);
                }
                reversi.restart(board) ;
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon image_background
                = new ImageIcon (
                getClass()
                        .getResource("resources/img/background.jpg")
        ) ;

        g.drawImage (
                image_background.getImage(),
                0,
                0,
                getWidth(),
                getHeight(),
                this
        ) ;
    }
}