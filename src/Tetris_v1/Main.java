package Tetris_v1;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(Main::myRun);
    }
    public static void myRun(){
        Game game = new Game();
        Display display = new Display(game);

        //Main Game Loop
        new Timer(Vals.FRAME_TIME, e->{
            try {
                display.setGameArea(game.getBoard());
                display.setNextBox(game.getNextBox());
                display.setScoreBox(game.getScore());
            }
            catch(RuntimeException ex){
                if ("Game Over".equals(ex.getMessage())) {
                    ((Timer) e.getSource()).stop();
                    System.out.println("Game Over");
                }
                else
                    throw ex;
            }
        }).start();
    }
}
