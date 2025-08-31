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
    boolean left, right, x_key, z_key, clearing, tetris;
    int next_piece = (int) (Math.random()*7) + 1, arr = 0, x_held = 0, z_held = 0, clear_delay = 0, required_delay, current_clearing_block;
    int left_counter = 10, right_counter = 10, piece_moved_right = 0, piece_moved_left = 0, piece_place_delay = 10, score = 0, lines_cleared = 0;
    double gravity = Vals.GRAVITY, drop = 0;
    int[][] board = new int[20][10];

    public int[][] getBoard(){
        processFrame();
        return board;
    }
    public int[][] getNextBox(){
        return Vals.NEXT_PIECE[next_piece-1];//converting to 0-indexed
    }
    public int getScore(){
        return score;
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
    private boolean noPieceOnBoard(){
        for(int i=0; i<20; i++)
            for(int j=0; j<10; j++)
                if(board[i][j]>0 && board[i][j]<100)
                    return false;
        return true;
    }
    private void processFrame(){
        if(tryClearLine()) return;

        if(noPieceOnBoard()) {
            if(piece_place_delay > 0) {
                piece_place_delay--;
                return;
            }
            else {
                piece_place_delay = 10;
                if (!spawnNextPiece())//either spawns next piece or throws exception
                    throw new RuntimeException("Game Over");
            }
        }

        processDas();
        processInput();

        //Gravity
        drop += gravity;
        if(drop > 1){
            tryDropPiece();
            drop = 0;
        }
    }
    private boolean tryClearLine(){
        setClearLine();//sets clear delay if set

        if(!clearing)
            return false;

        if(tetris)
            doClearTetris();
        else
            doClearLine();

        return true;
    }
    private void setClearLine(){
        if(clearing) return;

        int flag = 0;
        for(int i=19; i>=0; i--){
            int sum = 0;

            for(int j=0; j<10; j++)
                if(board[i][j]>100 && board[i][j]<175) sum++;

            if(sum==10){
                flag++;
                for(int j=0; j<10; j++)
                    board[i][j] += 100;
            }
        }
        if(flag==0) return;

        clear_delay = Vals.LINE_CLEAR_DELAY;
        required_delay = 14;
        clearing = true;
        current_clearing_block = 5;

        if(flag == 4){
            clear_delay = Vals.LINE_CLEAR_DELAY*2;
            required_delay = 28;
            tetris = true;
        }
    }
    private void doClearLine(){
        if(clear_delay == 0){
            settleBoard();
            score += Vals.LINE_CLEAR_POINTS[lines_cleared/5];
            lines_cleared = 0;
            clearing = false;
            return;
        }
        if(clear_delay == required_delay) {
            required_delay-=3;
            current_clearing_block--;
            for (int i = 0; i < 20; i++)
                if (board[i][current_clearing_block] > 200) {
                    board[i][current_clearing_block] = 0;
                    board[i][9-current_clearing_block] = 0;
                    lines_cleared++;
                }
        }
        clear_delay--;
    }
    private void doClearTetris(){
        if(clear_delay == 0){
            settleBoard();
            score += 25000;
            clearing = false;
            tetris = false;
            return;
        }
        if(clear_delay == required_delay) {
            required_delay-=6;
            current_clearing_block--;
            for (int i = 0; i < 20; i++)
                if (board[i][current_clearing_block] > 200) {
                    board[i][current_clearing_block] = 0;
                    board[i][9-current_clearing_block] = 0;
                }
        }
        clear_delay--;
    }
    private void settleBoard(){
        for(int i=19; i>= 0; i--){
            int j = i;
            while (j >= 0 && Arrays.stream(board[j]).allMatch(x -> x == 0)) j--;
            if(j<0) break;

            if (i != j) { // <-- the only fix
                System.arraycopy(board[j], 0, board[i], 0, board[0].length);
                Arrays.fill(board[j], 0);
            }
        }
    }
    private void processDas(){
        //Delayed Auto Shift
        if(right && piece_moved_right==1 && right_counter>0){
            right_counter--;
            return;
        }
        if(left && piece_moved_left==1 && left_counter>0)
            right_counter--;
    }
    private void processInput(){
        //Rotation
        if(x_key) {
            if (++x_held == 1)
                tryRotate('x');
        }
        else
            x_held=0;

        if(z_key) {
            if (++z_held == 1)
                tryRotate('z');
        }
        else
            z_held=0;

        //Movement
        if(right)
            tryMoveRight();
        else
            stopRight();

        if(left)
            tryMoveLeft();
        else
            stopLeft();
    }
    private boolean spawnNextPiece(){
        int piece = getNextPiece();
        return switch (piece) {
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
            for(int j=5-i; j<7-i; j++)
                if(board[i][j] != 0)
                    return false;

        //Set
        for(int i=0; i<2; i++)
            for(int j=5-i; j<7-i; j++)
                board[i][j] = Vals.S_PIECE[0];
        return true;
    }
    private boolean placeTPiece(){
        //Check
        if(board[1][5] != 0)
            return false;
        for(int i=4; i<7; i++)
            if(board[0][i] != 0)
                return false;

        //Set
        board[1][5] = Vals.T_PIECE[0];
        for(int i=4; i<7; i++)
            board[0][i] = Vals.T_PIECE[0];
        return true;
    }
    private boolean placeZPiece(){
        //Check
        for(int i=0; i<2; i++)
            for(int j=4+i; j<6+i; j++)
                if(board[i][j] != 0)
                    return false;

        //Set
        for(int i=0; i<2; i++)
            for(int j=4+i; j<6+i; j++)
                board[i][j] = Vals.Z_PIECE[0];
        return true;
    }
    private void tryDropPiece(){
        int[][] loc = new int[4][2];
        int blocks = 0;

        //Find and check if all blocks can move down
        for(int i=0; i<19 && blocks<4; i++)
            for(int j=0; j<10 && blocks<4; j++)
                if((board[i][j]>1 && board[i][j]<100) && (board[i+1][j]==0 || board[i+1][j]<100))
                    loc[blocks++] = new int[]{i, j};

        if(blocks<4) setPiece();
        else doDropPiece(loc);
    }
    private void setPiece(){
        for(int i=0; i<20; i++)
            for(int j=0; j<10; j++)
                if(board[i][j]!=0 && board[i][j]<100)
                    board[i][j] += 100;
    }
    private void doDropPiece(int[][] loc){
        int piece = board[loc[0][0]][loc[0][1]];
        for(int i=0; i<4; i++)
            board[loc[i][0]][loc[i][1]] = 0;

        for(int i=0; i<4; i++)
            board[loc[i][0]+1][loc[i][1]] = piece;
    }
    private void tryMoveRight(){
        //Auto Repeat Rate
        arr--;
        if(arr>0 && piece_moved_right > 0)
            return;
        arr=Vals.ARR;

        int[][] loc = new int[4][2];
        int blocks = 0;

        //Find location of all blocks
        for(int i=0; i<20 && blocks<4; i++)
            for(int j=0; j<9 && blocks<4; j++)
                if((board[i][j]>1 && board[i][j]<100) && (board[i][j+1]==0 || board[i][j+1]<100))
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
        //Auto Repeat Rate
        arr--;
        if(arr>0 && piece_moved_left > 0)
            return;
        arr=Vals.ARR;

        int[][] loc = new int[4][2];
        int blocks = 0;

        //Find location of all blocks
        for(int i=0; i<20 && blocks<4; i++)
            for(int j=1; j<10 && blocks<4; j++)
                if((board[i][j]>1 && board[i][j]<100) && (board[i][j-1]==0 || board[i][j-1]<100))
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
        right_counter = Vals.DAS;
        piece_moved_right = 0;
    }
    private void stopLeft(){
        //Reset DAS
        left_counter = Vals.DAS;
        piece_moved_left = 0;
    }
    private void tryRotate(char polarity){
        //Locate first block
        int new_i, new_j;
        int[] base_loc = getFirstBlock();
        int base_i = base_loc[0], base_j = base_loc[1];

        //Find which piece it is and load appropriate rotation tables
        int piece = board[base_i][base_j];
        int rotation = (piece % 10)% 4;
        piece = (piece / 10) - 1;
        int[][] loc;

        if(polarity=='x') loc = Vals.x_rotation_table[piece][(rotation+1)%4];
        else loc = Vals.z_rotation_table[piece][(rotation+3)%4];

        //Check if it can be rotated
        boolean can_rotate = true;
        for(int i=0; i<4; i++){
            new_i = base_i + loc[i][0];
            new_j = base_j + loc[i][1];
            //   Boundary Checks.                                 Checking for collision with set pieces
            if(!(new_i>=0 && new_i<20 && new_j>=0 && new_j<10) || board[new_i][new_j] > 100)
                can_rotate = false;
        }
        if(can_rotate) doRotate(loc, polarity);
    }
    private void doRotate(int[][] loc, char polarity){
        //Locate first block
        int[] base_loc = getFirstBlock();
        int base_i = base_loc[0], base_j = base_loc[1];

        //Set current blocks to clear
        int blocks=0, piece = board[base_i][base_j];
        for(int i=base_i; i<20 && blocks<4; i++)
            for(int j=0; j<10 && blocks<4; j++)
                if(board[i][j]>0 && board[i][j]<100){
                    blocks++;
                    board[i][j] = 0;
                }

        int rem = (piece / 10) * 10;
        piece %= 10;
        if(polarity=='x') piece = ((piece + 1) % 4) + rem;
        else piece = ((piece + 3) % 4) + rem;

        //Setting new blocks
        for(int i=0; i<4; i++)
            board[base_i + loc[i][0]][base_j + loc[i][1]] = piece;
    }
    private int[] getFirstBlock(){
        for(int i=0; i<20; i++)
            for(int j=0; j<10; j++)
                if(board[i][j]>0 && board[i][j]<100)
                    return new int[]{i, j};
        return null;
    }
}
