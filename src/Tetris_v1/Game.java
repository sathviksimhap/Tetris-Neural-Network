package Tetris_v1;

import java.util.Arrays;

/**
 * Responsible for all logic
 * Uses a 2D int array to represent the tetris board
 * 0 -> clear cell
 * 1 -> set cell
 * >1 -> hard coded pieces
 *     10s place tracks piece
 *     1s place tracks rotation
 *     eg: 61 would be a T-piece rotated by 90 degrees *
 */
public class Game {
    int LOCKED = 1, FREE = 0;
    boolean left, right, x_key, z_key;
    int next_piece = -1, arr = 2, x_held = 0, z_held = 0;
    int left_counter = 10, right_counter = 10, piece_moved_right = 0, piece_moved_left = 0;
    double gravity = 0.1, drop = 0;
    int[][] board = new int[20][10];

    int[][] getBoard(){
        processFrame();
        return board;
    }
    //Input state tracking functions
    public void setLeft(boolean b){
        left = b;
    }
    public void setX(boolean b){
        x_key = b;
    }
    public void setZ(boolean b){
        z_key = b;
    }
    public void setRight(boolean b){
        right = b;
    }
    public void clearBoard(){
        board = new int[20][10];
    }
    public boolean noPieceOnBoard(){
        for(int i=0; i<20; i++)
            for(int j=0; j<10; j++)
                if(board[i][j]>1)
                    return false;
        return true;
    }
    private void processFrame(){
        if(noPieceOnBoard())
            spawnNextPiece();

        //Movement
        if(right)
            tryMoveRight();
        else
            stopRight();

        if(left)
            tryMoveLeft();
        else
            stopLeft();

        //Rotation
        if(x_key) {
            if (++x_held == 1)
                tryRotateX();
        }
        else
            x_held=0;

        //Gravity
        drop += gravity;
        if(drop > 1){
            tryDropPiece();
            drop--;
        }
    }
    private void spawnNextPiece(){
        int piece = getNextPiece();
        boolean piece_placed = switch (piece) {
            case 1 -> placeIPiece();
            case 2 -> placeJPiece();
            case 3 -> placeLPiece();
            case 4 -> placeBPiece();
            case 5 -> placeSPiece();
            case 6 -> placeTPiece();
            case 7 -> placeZPiece();
            default -> false;
        };
    }
    private int getNextPiece(){
        if(true) return 1;

        if(next_piece==-1){
            next_piece = (int) (Math.random()*7) + 1;
            return (int) (Math.random()*7) + 1;
        }
        int temp = next_piece;
        next_piece = (int) (Math.random()*7) + 1;
        return temp;
    }
    private boolean placeIPiece(){
        //Check
        for(int i=3; i<7; i++)
            if(board[0][i]!=0)
                return false;

        //Set
        for(int i=3; i<7; i++)
            board[0][i] = Vals.I_PIECE[0];
        return true;
    }
    private boolean placeJPiece(){
        //Check
        for(int i=4; i<7; i++)
            if (board[0][i] != 0)
                return false;
        if(board[1][6] != 0)
            return false;

        //Set
        for(int i=4; i<7; i++)
            board[0][i] = Vals.J_PIECE[0];
        board[1][6] = Vals.J_PIECE[0];
        return true;
    }
    private boolean placeLPiece(){
        //Check
        for(int i=4; i<7; i++)
            if (board[0][i] != 0)
                return false;
        if(board[1][4] != 0)
            return false;

        //Set
        for(int i=4; i<7; i++)
            board[0][i] = Vals.L_PIECE[0];
        board[1][4] = Vals.L_PIECE[0];
        return true;
    }
    private boolean placeBPiece(){
        //Check
        for(int i=0; i<2; i++)
            for(int j=4; j<6; j++)
                if(board[i][j] != 0)
                    return false;
        //set
        for(int i=0; i<2; i++)
            for(int j=4; j<6; j++)
                board[i][j] = Vals.B_PIECE[0];
        return true;
    }
    private boolean placeSPiece(){
        //Check
        for(int i=0; i<2; i++)
            for(int j=4+i; j<6+i; j++)
                if(board[i][j] != 0)
                    return false;

        //Set
        for(int i=0; i<2; i++)
            for(int j=4+i; j<6+i; j++)
                board[i][j] = Vals.S_PIECE[0];
        return true;
    }
    private boolean placeTPiece(){
        //Check
        if(board[0][5] != 0)
            return false;
        for(int i=4; i<7; i++)
            if(board[1][i] != 0)
                return false;

        //Set
        board[0][5] = Vals.T_PIECE[0];
        for(int i=4; i<7; i++)
            board[1][i] = Vals.T_PIECE[0];
        return true;
    }
    private boolean placeZPiece(){
        //Check
        for(int i=0; i<2; i++)
            for(int j=5-i; j<7-i; j++)
                if(board[i][j] != 0)
                    return false;

        //Set
        for(int i=0; i<2; i++)
            for(int j=5-i; j<7-i; j++)
                board[i][j] = Vals.Z_PIECE[0];
        return true;
    }
    private void tryDropPiece(){
        int[][] loc = new int[4][2];
        int blocks = 0;

        //Find and check if all blocks can move down
        for(int i=0; i<19 && blocks<4; i++)
            for(int j=0; j<10 && blocks<4; j++)
                if(board[i][j]>1 && board[i+1][j]!=1)
                    loc[blocks++] = new int[]{i, j};

        if(blocks<4) setPiece();
        else doDropPiece(loc);
    }
    private void setPiece(){
        for(int i=0; i<20; i++)
            for(int j=0; j<10; j++)
                if(board[i][j]>1)
                    board[i][j] = 1;
    }
    private void doDropPiece(int[][] loc){
        int piece = board[loc[0][0]][loc[0][1]];
        for(int i=0; i<4; i++)
            board[loc[i][0]][loc[i][1]] = 0;

        for(int i=0; i<4; i++)
            board[loc[i][0]+1][loc[i][1]] = piece;
    }
    private void tryMoveRight(){
        //Delayed Auto Shift
        if(piece_moved_right==1 && right_counter>0){
            right_counter--;
            return;
        }
        //Auto Repeat Rate
        arr--;
        if(arr>0)
            return;
        arr=2;

        int[][] loc = new int[4][2];
        int blocks = 0;

        //Find location of all blocks
        for(int i=0; i<20 && blocks<4; i++)
            for(int j=0; j<9 && blocks<4; j++)
                if(board[i][j]>1 && board[i][j+1]!=1)
                    loc[blocks++] = new int[]{i, j};

        if(blocks==4)doMoveRight(loc);
    }
    private void doMoveRight(int[][] loc){
        piece_moved_right++;
        int piece = board[loc[0][0]][loc[0][1]];
        for(int i=0; i<4; i++)
            board[loc[i][0]][loc[i][1]] = 0;

        for(int i=0; i<4; i++)
            board[loc[i][0]][loc[i][1]+1] = piece;
    }
    private void tryMoveLeft(){
        //Delayed Auto Shift
        if(piece_moved_left==1 && left_counter>0){
            left_counter--;
            return;
        }
        //Auto Repeat Rate
        arr--;
        if(arr>0)
            return;
        arr=2;

        int[][] loc = new int[4][2];
        int blocks = 0;

        //Find location of all blocks
        for(int i=0; i<20 && blocks<4; i++)
            for(int j=1; j<10 && blocks<4; j++)
                if(board[i][j]>1 && board[i][j-1]!=1)
                    loc[blocks++] = new int[]{i, j};

        if(blocks==4)doMoveLeft(loc);
    }
    private void doMoveLeft(int[][] loc){
        piece_moved_left++;
        int piece = board[loc[0][0]][loc[0][1]];
        for(int i=0; i<4; i++)
            board[loc[i][0]][loc[i][1]] = 0;

        for(int i=0; i<4; i++)
            board[loc[i][0]][loc[i][1]-1] = piece;
    }
    private void stopRight(){
        //Reset DAS
        right_counter = 10;
        piece_moved_right = 0;
    }
    private void stopLeft(){
        //Reset DAS
        left_counter = 10;
        piece_moved_left = 0;
    }
    private void tryRotateX(){
        //Locate first block
        int new_i, new_j;
        int[] base_loc = getFirstBlock();
        int base_i = base_loc[0], base_j = base_loc[1];

        //Find which piece it is and load appropriate rotation tables
        int piece = board[base_i][base_j];
        int rotation = ((piece % 10) + 1) % 4;
        piece = (piece / 10) - 1;
        int[][] loc = Vals.x_rotation_table[piece][rotation];

        //Check if it can be rotated
        boolean can_rotate = true;
        for(int i=0; i<4; i++){
            new_i = base_i + loc[i][0];
            new_j = base_j + loc[i][1];
            //   Boundary Checks.                                 Checking for collision with set pieces
            if(!(new_i>=0 && new_i<20 && new_j>=0 && new_j<10) || board[new_i][new_j] == 1)
                can_rotate = false;
        }
        if(can_rotate)doRotateX(loc);
    }
    private void doRotateX(int[][] loc){
        //Locate first block
        int[] base_loc = getFirstBlock();
        int base_i = base_loc[0], base_j = base_loc[1];

        //Set current blocks to clear
        int blocks=0, piece = board[base_i][base_j];
        for(int i=base_i; i<20 && blocks<4; i++)
            for(int j=base_j; j<10 && blocks<4; j++)
                if(board[i][j]!=0){
                    blocks++;
                    board[i][j] = 0;
                }

        int rem = (piece / 10) * 10;
        piece %= 10;
        piece = ((piece + 1) % 4) + rem;

        //Setting new blocks
        for(int i=0; i<4; i++)
            board[base_i + loc[i][0]][base_j + loc[i][1]] = piece;
    }
    private int[] getFirstBlock(){
        for(int i=0; i<20; i++)
            for(int j=0; j<10; j++)
                if(board[i][j]>1)
                    return new int[]{i, j};
        return null;
    }
}
