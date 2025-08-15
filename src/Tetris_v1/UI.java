package Tetris_v1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UI{
    JTextArea play_area;
    JFrame frame;
    Game game;

    public UI(Game g){
        game = g;
        play_area = getPlayArea();
        frame = getFrame();

        frame.add(play_area);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        game.setLeft(true);
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.setRight(true);
                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        game.setLeft(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.setRight(false);
                        break;
                }
            }
        });

        frame.setVisible(true);
    }
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        return frame;
    }
    public void crash(){
        frame.dispose();
    }
}
