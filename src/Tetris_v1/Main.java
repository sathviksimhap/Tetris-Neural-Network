package Tetris_v1;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(Main::myRun);
    }
    public static void myRun(){
        Game game = new Game();
        UI display = new UI(game);

        //Main Game Loop
        new Timer(Vals.FRAME_TIME, e->{
            try {
                long start = System.nanoTime();

                display.setGameArea(game.getBoard());
                display.setNextBox(game.getNextBox());

                long time = (System.nanoTime() - start) / 1_000_000;

                if (time > Vals.FRAME_TIME) {
                    System.err.println("Error: Frame processing time exceeded limit (" + time + "ms)");
                    display.crash();
                    ((Timer) e.getSource()).stop();
                }
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
