package Tetris_v1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Responsible for taking all inputs and formatting and displaying outputs
 * Works with one game object
 */
public class UI{
    JTextArea play_area;
    JFrame frame;
    Game game;

    public UI(Game g){
        game = g;//links one ui object to one game object
        play_area = getPlayArea();
        frame = getFrame();

        frame.add(play_area);

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
        StringBuilder sb = new StringBuilder();

        for(int[] line: board){
            for (int c : line) {
                if (c==0) sb.append("  ");
                else sb.append("[]");
            }
            sb.append("\n");
        }
        sb.deleteCharAt(sb.length()-1);
        play_area.setText(sb.toString());
    }
    public JTextArea getPlayArea(){
        JTextArea play_area = new JTextArea(20, 10); // rows, columns
        play_area.setEditable(false);
        play_area.setFocusable(false);
        play_area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 30));
        play_area.setBounds(780, 140, 360, 800);
        play_area.setBackground(Color.BLACK);
        play_area.setForeground(Color.WHITE);
        return play_area;
    }
    public JFrame getFrame(){
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
    public void crash(){
        frame.dispose();
    }
}
