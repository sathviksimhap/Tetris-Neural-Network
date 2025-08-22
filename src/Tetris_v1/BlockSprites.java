package Tetris_v1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlockSprites {
    public static BufferedImage[] blocks = new BufferedImage[9];

    static {
        try {
            blocks[1] = ImageIO.read(new File("C:\\Users\\sride\\IdeaProjects\\Tetris\\src\\resources\\I_piece.png"));
            blocks[2] = ImageIO.read(new File("C:\\Users\\sride\\IdeaProjects\\Tetris\\src\\resources\\J_piece.png"));
            blocks[3] = ImageIO.read(new File("C:\\Users\\sride\\IdeaProjects\\Tetris\\src\\resources\\L_piece.png"));
            blocks[4] = ImageIO.read(new File("C:\\Users\\sride\\IdeaProjects\\Tetris\\src\\resources\\B_piece.png"));
            blocks[5] = ImageIO.read(new File("C:\\Users\\sride\\IdeaProjects\\Tetris\\src\\resources\\S_piece.png"));
            blocks[6] = ImageIO.read(new File("C:\\Users\\sride\\IdeaProjects\\Tetris\\src\\resources\\T_piece.png"));
            blocks[7] = ImageIO.read(new File("C:\\Users\\sride\\IdeaProjects\\Tetris\\src\\resources\\Z_piece.png"));
            blocks[8] = ImageIO.read(new File("C:\\Users\\sride\\IdeaProjects\\Tetris\\src\\resources\\I_piece.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
