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

    protected BoardView grid ;
    protected StatView stat ;
    protected Reversi reversi ;

    public GameView(Reversi _reversi){
        super() ;

        reversi = _reversi ;

        // views initialization
        setLayout(new BorderLayout()) ;
        grid = new BoardView(reversi) ;
        add (grid, BorderLayout.CENTER) ;
        stat = new StatView(reversi) ;
        add (stat, BorderLayout.WEST) ;

        // add to observable
        reversi.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(reversi.isStocked()){
            JOptionPane.showMessageDialog (
                    null,
                    reversi.getCurrentPlayer().getName() +", you are stocked ! \n\n\t "+
                            reversi.getOpponantPlayer().getName()+", it's your turn ! ",
                    "OUUUUPS !",
                    JOptionPane.ERROR_MESSAGE
            ) ;
            reversi.stocked() ;

            if(reversi.isStockedGame()){
                JOptionPane.showMessageDialog (
                        null,
                        "All players are stocked ! \n It's the end of this game !",
                        "OOOOOH NOOOO !",
                        JOptionPane.ERROR_MESSAGE
                ) ;
            }
        }else if(reversi.isFinished()) {
            switch (reversi.isWin()) {

                case WIN :
                    JOptionPane.showMessageDialog (
                            null,
                            reversi.getCurrentPlayer().getName() +", you win this game ! \n\n\t "+
                                    reversi.getOpponantPlayer().getName()+" won ! ",
                            "CONGRATULATIONS !",
                            JOptionPane.YES_OPTION
                    ) ;
                    break;
                case EQUALS :
                    JOptionPane.showMessageDialog (
                            null,
                            "There is a perfect equality, thanks for playing "+reversi.getCurrentPlayer().getName()+
                            " and " +reversi.getOpponantPlayer().getName()+" !",
                            "EQUALITY !",
                            JOptionPane.YES_OPTION
                    ) ;
                    break;
                case LOOSE :
                    JOptionPane.showMessageDialog (
                            null,
                            reversi.getCurrentPlayer().getName() +", you loose this game ! \n\n\t "+
                                    reversi.getOpponantPlayer().getName()+" won ! ",
                            "SORRY !",
                            JOptionPane.NO_OPTION
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