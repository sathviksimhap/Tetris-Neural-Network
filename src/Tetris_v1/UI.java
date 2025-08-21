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
    TetrisPanel play_area, next_box;
    JFrame frame;
    Game game;

    public UI(Game g){
        game = g;//links one ui object to one game object
        play_area = getPlayArea();
//        next_box = getNextBox();
        frame = getFrame();

        System.out.println("play_area = " + play_area);

        frame.add(play_area);
//        frame.add(next_box);

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
        play_area.setGameArea(board);
        play_area.repaint();
    }
    public void setNextBox(int[][] next_array){
//        StringBuilder sb = new StringBuilder("  NEXT  \n");
//
//        for(int[] line: next_array){
//            for (int c : line) {
//                if (c==0) sb.append("  ");
//                else sb.append("[]");
//            }
//            sb.append("\n");
//        }
//        sb.deleteCharAt(sb.length()-1);
//        next_box.setText(sb.toString());
    }
    public TetrisPanel getPlayArea(){
        TetrisPanel play_area = new TetrisPanel();

        play_area.setFocusable(false);
        play_area.setBounds(780, 140, 320, 640);
        play_area.setBackground(Color.BLACK);
        return play_area;
    }
    public JTextArea getNextBox(){
        JTextArea next_box = new JTextArea(4, 4); // rows, columns
        next_box.setEditable(false);
        next_box.setFocusable(false);
        next_box.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 30));
        next_box.setBounds(1180, 440, 144, 130);
        next_box.setBackground(Color.BLACK);
        next_box.setForeground(Color.WHITE);
        return next_box;
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
