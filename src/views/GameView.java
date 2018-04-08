package views;

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
        if (reversi.getStucked() && !reversi.getCurrentPlayer().isAi()) {
            JOptionPane.showMessageDialog (
                    null,
                    reversi.getCurrentPlayer().getName() + ", you are stucked ! \n\n\t "+
                            reversi.getOpponentPlayer().getName() + ", it's your turn ! ",
                    "OUUUUPS !",
                    JOptionPane.PLAIN_MESSAGE
            ) ;
            reversi.stucked() ;

            if (reversi.isStuckedGame()){
                JOptionPane.showMessageDialog (
                        null,
                        "All players are stucked ! \n It's the end of this game !",
                        "OOOOOH NOOOO !",
                        JOptionPane.PLAIN_MESSAGE
                ) ;
            }
        } else if(reversi.isFinished()) {
            switch (reversi.isWin()) {

                case WIN :
                    JOptionPane.showMessageDialog (
                            null,
                            reversi.getCurrentPlayer().getName() +", you win this game ! \n\n\t "+
                                    reversi.getOpponentPlayer().getName()+" loose ! ",
                            "CONGRATULATIONS !",
                            JOptionPane.PLAIN_MESSAGE
                    ) ;
                    break;
                case EQUALS :
                    JOptionPane.showMessageDialog (
                            null,
                            "There is a perfect equality, thanks for playing "+reversi.getCurrentPlayer().getName()+
                            " and " +reversi.getOpponentPlayer().getName()+" !",
                            "EQUALITY !",
                            JOptionPane.PLAIN_MESSAGE
                    ) ;
                    break;
                case LOOSE :
                    JOptionPane.showMessageDialog (
                            null,
                            reversi.getCurrentPlayer().getName() +", you loose this game ! \n\n\t "+
                                    reversi.getOpponentPlayer().getName()+" won ! ",
                            "SORRY !",
                            JOptionPane.PLAIN_MESSAGE
                    ) ;
                    break;
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