/**
 * Data structure for a certain board configuration
 */

package GameLogic;

public class BoardConfig {

    /**
     * Encoding of a figure:
     *  - COLOR: 0-99 white, 100-199 black
     *  - TYPE: ?10-?19 bishop, ?20-?29 king, ?30-?39 knight, ?40-?49 pawn, ?50-?59 queen, ?60-?69 rook
     *  - HAS MOVED: ??0 has not been moved, ??1 has already been moved
     */
    public char[] board = new char[64];
    /**
     * indices of the tiles involved in the last move (for highlighting)
     */
    public int[] last_moved = new int[2];
    /**
     * indices of the white (king_indices[0]) and black (king_indices[1]) king (for finding chess)
     */
    public int[] king_indices = new int[2];

    /**
     * initializes a board configuration with the initial chess board
     */
    public void init() {
        board[0] = 160;
        board[1] = 130;
        board[2] = 110;
        board[3] = 150;
        board[4] = 120;
        board[5] = 110;
        board[6] = 130;
        board[7] = 160;
        for (int i = 8; i < 16; i++) {
            board[i] = 140;
        }
        for (int i = 48; i < 56; i++) {
            board[i] = 40;
        }
        board[56] = 60;
        board[57] = 30;
        board[58] = 10;
        board[59] = 50;
        board[60] = 20;
        board[61] = 10;
        board[62] = 30;
        board[63] = 60;

        last_moved[0] = -1;
        last_moved[1] = -1;

        king_indices[0] = 60;
        king_indices[1] = 4;
    }

    /**
     * creates a copy of the board configuration
     * @return deep copy of the board configuration
     */
    public BoardConfig copy() {
        BoardConfig b = new BoardConfig();
        System.arraycopy(this.board, 0, b.board, 0, 64);
        System.arraycopy(this.last_moved, 0, b.last_moved, 0, 2);
        System.arraycopy(this.king_indices, 0, b.king_indices, 0, 2);
        return b;
    }

    /**
     * calculates an individual id for each board configuration, for lookup table
     * @return the id
     */
    public int id() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            sb.append(this.board[i]);
            sb.append(this.board[i]);
            sb.append(this.board[i]);
        }
        String s = sb.toString();
        return s.hashCode();
    }
}
