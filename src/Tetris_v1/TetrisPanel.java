package Tetris_v1;

import javax.swing.*;
import java.awt.*;

public class TetrisPanel extends JPanel {
    int[][] board;
    int rows, cols;
    public void setGameArea(int[][] board, int rows, int cols) {
        this.board = board;
        this.rows = rows;
        this.cols = cols;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (board == null) return;
        super.paintComponent(g);

        int size = 32;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
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
