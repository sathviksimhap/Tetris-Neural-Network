package Tetris_v1;

import java.util.Arrays;

public class Game {
    boolean left, right, x_key, z_key;
    int next_piece = -1, das = 10, arr = 2;
    double gravity = 0.03125, drop = 0;
    int[][] board = new int[20][10];

    int[][] getBoard(){
        processFrame();
        return board;
    }
    public void setLeft(boolean b){
        left = b;
    }
    public void setRight(boolean b){
        right = b;
    }
    public void clearBoard(){
        board = new int[20][10];
    }
    public boolean noPieceOnBoard(){
        boolean flag = true;
        for(int i=0; i<20; i++)
            for(int j=0; j<10; j++)
                if(board[i][j]>1)
                    flag = false;
        return flag;
    }
    private void processFrame(){
        if(noPieceOnBoard())
            spawnNextPiece();

        if(right)
            tryMoveRight();

        if(left)
            tryMoveLeft();

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
        if(next_piece==-1){
            next_piece = (int) (Math.random()*7) + 1;
            return (int) (Math.random()*7) + 1;
        }
        int temp = next_piece;
        next_piece = (int) (Math.random()*7) + 1;
        return temp;
    }
    private boolean placeIPiece(){
        int[][] copy = new int[board.length][];
        for (int i = 0; i < 2; i++)
            copy[i] = Arrays.copyOf(board[i], 10);

        for(int i=3; i<7; i++){
            if(copy[0][i]!=0) return false;
            copy[0][i] = Vals.I_PIECE[0];
        }
        for (int i = 0; i < 2; i++)
            System.arraycopy(copy[i], 0, board[i], 0, 10);
        return true;
    }
    private boolean placeJPiece(){
        int[][] copy = new int[board.length][];
        for (int i = 0; i < 2; i++)
            copy[i] = Arrays.copyOf(board[i], 10);

        for(int i=4; i<7; i++) {
            if (copy[0][i] != 0) return false;
            copy[0][i] = Vals.J_PIECE[0];
        }
        if(copy[1][6] != 0) return false;
        copy[1][6] = Vals.J_PIECE[0];

        for (int i = 0; i < 2; i++)
            System.arraycopy(copy[i], 0, board[i], 0, 10);
        return true;
    }
    private boolean placeLPiece(){
        int[][] copy = new int[board.length][];
        for (int i = 0; i < 2; i++)
            copy[i] = Arrays.copyOf(board[i], 10);

        for(int i=4; i<7; i++) {
            if (copy[0][i] != 0) return false;
            copy[0][i] = Vals.L_PIECE[0];
        }
        if(copy[1][4] != 0) return false;
        copy[1][4] = Vals.L_PIECE[0];

        for (int i = 0; i < 2; i++)
            System.arraycopy(copy[i], 0, board[i], 0, 10);
        return true;
    }
    private boolean placeBPiece(){
        int[][] copy = new int[board.length][];
        for (int i = 0; i < 2; i++)
            copy[i] = Arrays.copyOf(board[i], 10);

        if(copy[0][4] != 0) return false;
        copy[0][4] = Vals.B_PIECE[0];
        if(copy[0][5] != 0) return false;
        copy[0][5] = Vals.B_PIECE[0];
        if(copy[1][4] != 0) return false;
        copy[1][4] = Vals.B_PIECE[0];
        if(copy[1][5] != 0) return false;
        copy[1][5] = Vals.B_PIECE[0];

        for (int i = 0; i < 2; i++)
            System.arraycopy(copy[i], 0, board[i], 0, 10);
        return true;
    }
    private boolean placeSPiece(){
        int[][] copy = new int[board.length][];
        for (int i = 0; i < 2; i++)
            copy[i] = Arrays.copyOf(board[i], 10);

        if(copy[0][4] != 0) return false;
        copy[0][4] = Vals.S_PIECE[0];
        if(copy[0][5] != 0) return false;
        copy[0][5] = Vals.S_PIECE[0];
        if(copy[1][5] != 0) return false;
        copy[1][5] = Vals.S_PIECE[0];
        if(copy[1][6] != 0) return false;
        copy[1][6] = Vals.S_PIECE[0];

        for (int i = 0; i < 2; i++)
            System.arraycopy(copy[i], 0, board[i], 0, 10);
        return true;
    }
    private boolean placeTPiece(){
        int[][] copy = new int[board.length][];
        for (int i = 0; i < 2; i++)
            copy[i] = Arrays.copyOf(board[i], 10);

        if(copy[0][4] != 0) return false;
        copy[0][4] = Vals.T_PIECE[0];
        if(copy[0][5] != 0) return false;
        copy[0][5] = Vals.T_PIECE[0];
        if(copy[0][6] != 0) return false;
        copy[0][6] = Vals.T_PIECE[0];
        if(copy[1][5] != 0) return false;
        copy[1][5] = Vals.T_PIECE[0];

        for (int i = 0; i < 2; i++)
            System.arraycopy(copy[i], 0, board[i], 0, 10);
        return true;
    }
    private boolean placeZPiece(){
        int[][] copy = new int[board.length][];
        for (int i = 0; i < 2; i++)
            copy[i] = Arrays.copyOf(board[i], 10);

        if(copy[0][5] != 0) return false;
        copy[0][5] = Vals.Z_PIECE[0];
        if(copy[0][6] != 0) return false;
        copy[0][6] = Vals.Z_PIECE[0];
        if(copy[1][4] != 0) return false;
        copy[1][4] = Vals.Z_PIECE[0];
        if(copy[1][5] != 0) return false;
        copy[1][5] = Vals.Z_PIECE[0];

        for (int i = 0; i < 2; i++)
            System.arraycopy(copy[i], 0, board[i], 0, 10);
        return true;
    }
    private void tryDropPiece(){
        int[][] loc = new int[4][2];
        int blocks = 0;

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
        arr--;
        if(arr>0)
            return;
        arr=2;

        int[][] loc = new int[4][2];
        int blocks = 0;

        for(int i=0; i<20 && blocks<4; i++)
            for(int j=0; j<9 && blocks<4; j++)
                if(board[i][j]>1 && board[i][j+1]!=1)
                    loc[blocks++] = new int[]{i, j};

        if(blocks==4)doMoveRight(loc);
    }
    private void doMoveRight(int[][] loc){
        int piece = board[loc[0][0]][loc[0][1]];
        for(int i=0; i<4; i++)
            board[loc[i][0]][loc[i][1]] = 0;

        for(int i=0; i<4; i++)
            board[loc[i][0]][loc[i][1]+1] = piece;
    }
    private void tryMoveLeft(){
        arr--;
        if(arr>0)
            return;
        arr=2;

        int[][] loc = new int[4][2];
        int blocks = 0;

        for(int i=0; i<20 && blocks<4; i++)
            for(int j=1; j<10 && blocks<4; j++)
                if(board[i][j]>1 && board[i][j-1]!=1)
                    loc[blocks++] = new int[]{i, j};

        if(blocks==4)doMoveLeft(loc);
    }
    private void doMoveLeft(int[][] loc){
        int piece = board[loc[0][0]][loc[0][1]];
        for(int i=0; i<4; i++)
            board[loc[i][0]][loc[i][1]] = 0;

        for(int i=0; i<4; i++)
            board[loc[i][0]][loc[i][1]-1] = piece;
    }
}
