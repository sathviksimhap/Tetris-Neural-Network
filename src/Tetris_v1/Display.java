package Tetris_v1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Responsible for taking all inputs and formatting and displaying outputs
 * Works with one game object
 */
public class Display {
    TetrisPanel play_area, next_box;
    JLabel score_box;
    JFrame frame;
    Game game;

    public Display(Game g){
        game = g;//links one ui object to one game object
        play_area = getPlayArea();
        next_box = getNextBox();
        score_box = getScoreBox();
        frame = getFrame();

        frame.add(play_area);
        frame.add(next_box);
        frame.add(score_box);
        frame.add(getScoreTextBox());
        frame.add(getNextTextBox());

        frame.addKeyListener(new KeyAdapter() {
            //relays if keys are held
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        game.setLeft(true);
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.setRight(true);
                        break;
                    case KeyEvent.VK_X:
                        game.setX(true);
                        break;
                    case KeyEvent.VK_Z:
                        game.setZ(true);
                        break;
                }
            }
            //relays if keys are not held
            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        game.setLeft(false);
                    case KeyEvent.VK_RIGHT:
                        game.setRight(false);
                        break;
                    case KeyEvent.VK_X:
                        game.setX(false);
                        break;
                    case KeyEvent.VK_Z:
                        game.setZ(false);
                        break;
                }
            }
        });
        frame.setVisible(true);
    }
    //Converts 2d int array of the board to formatted string
    public void setGameArea(int[][] board){
        play_area.setGameArea(board, 20, 10);
        play_area.repaint();
    }
    public void setNextBox(int[][] next_array){
        next_box.setGameArea(next_array, 3, 5);
        next_box.repaint();
    }
    public void setScoreBox(int score){
        score_box.setText(String.format("%06d", score));
    }
    private TetrisPanel getPlayArea(){
        TetrisPanel play_area = new TetrisPanel();

        play_area.setFocusable(false);
        play_area.setBounds(780, 140, 320, 640);
        play_area.setBackground(Color.BLACK);
        return play_area;
    }
    private TetrisPanel getNextBox(){
        TetrisPanel next_box = new TetrisPanel();

        next_box.setFocusable(false);
        next_box.setBounds(1180, 390, 160, 96);
        next_box.setBackground(Color.BLACK);
        return next_box;
    }
    private JLabel getScoreBox(){
        JLabel score_box = new JLabel("000000");

        score_box.setFocusable(false);
        score_box.setBounds(940, 80, 160, 60);
        score_box.setFont(new Font("Monospaced", Font.BOLD, 32));
        score_box.setOpaque(true);
        score_box.setBackground(Color.BLACK);
        score_box.setForeground(Color.WHITE);
        return score_box;
    }
    private JFrame getFrame(){
        JFrame frame = new JFrame("Tetris");
        frame.setSize(1920, 1080);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setLayout(null);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        return frame;
    }
    private JLabel getNextTextBox(){
        JLabel next_text_box = new JLabel("NEXT", SwingConstants.CENTER);
        next_text_box.setFocusable(false);
        next_text_box.setBounds(1180, 320, 160, 80);
        next_text_box.setFont(new Font("Monospaced", Font.BOLD, 64));
        next_text_box.setOpaque(true);
        next_text_box.setBackground(Color.BLACK);
        next_text_box.setForeground(Color.WHITE);
        return next_text_box;
    }
    private JLabel getScoreTextBox(){
        JLabel score_text_box = new JLabel("SCORE: ", SwingConstants.CENTER);
        score_text_box.setFocusable(false);
        score_text_box.setBounds(780, 80, 160, 60);
        score_text_box.setFont(new Font("Monospaced", Font.BOLD, 32));
        score_text_box.setOpaque(true);
        score_text_box.setBackground(Color.BLACK);
        score_text_box.setForeground(Color.WHITE);
        return score_text_box;
    }
    public void crash(){
        frame.dispose();
    }
}
