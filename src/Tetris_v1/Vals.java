package Tetris_v1;

public final class  Vals {
    private Vals(){}

    public static final int FRAME_TIME = 200;

    public static final int[] I_PIECE = {10, 11, 12, 13};
    public static final int[] J_PIECE = {20, 21, 22 ,23};
    public static final int[] L_PIECE = {30, 31, 32, 33};
    public static final int[] B_PIECE = {40, 41, 42, 43};
    public static final int[] S_PIECE = {50, 51, 52, 53};
    public static final int[] T_PIECE = {60, 61, 62, 63};
    public static final int[] Z_PIECE = {70, 71, 72, 73};

    //int[piece][rotation][blocks][cords]
    public static final int[][][][] x_rotation_table = {
            //I-Piece
            {
                    {{2, -2}, {2, -1}, {2, 0}, {2, 1}},
                    {{-2, 2}, {-1, 2}, {0, 2}, {1, 2}},
                    {{2, -2}, {2, -1}, {2, 0}, {2, 1}},
                    {{-2, 2}, {-1, 2}, {0, 2}, {1, 2}}
            },
            //T-Piece
            {
                    {{0, 0}, {1, 0}, {1, 1}, {1, -1}},
                    {{0, 0}, {1, 0}, {2, 0}, {1, 1}},
                    {{1, -1}, {1, 0}, {1, 1}, {2, 0}},
                    {{0, 0}, {-1, 1}, {0, 1}, {1, 1}}
            }
    };
    //int[piece][rotation][blocks][cords]
    public static final int[][][][] z_rotation_table = {
            //I-Piece
            {
                    {{2, -2}, {2, -1}, {2, 0}, {2, 1}},
                    {{-2, 2}, {-1, 2}, {0, 2}, {1, 2}},
                    {{2, -2}, {2, -1}, {2, 0}, {2, 1}},
                    {{-2, 2}, {-1, 2}, {0, 2}, {1, 2}}
            },
            //T-Piece

    };
}
