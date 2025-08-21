package Tetris_v1;

import javax.swing.*;
import java.awt.*;

public class TetrisPanel extends JPanel {
    int[][] board;
    public void setGameArea(int[][] board) {
        this.board = board;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int size = 32;
        for (int r = 0; r < 20; r++) {
            for (int c = 0; c < 10; c++) {
                int block = board[r][c];
                if (block != 0) {
                    block %= 100;
                    block /= 10;
                    g.drawImage(BlockSprites.blocks[block],
                            c * size,
                            r * size,
                            size, size,
                            null);
                }
            }
        }
    }
}
