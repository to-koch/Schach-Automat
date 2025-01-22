/**
 * Data structure for a chess board configuration.
 * Store the figure any and related board information.
 */

package GameLogic;

import java.util.Arrays;
import java.util.BitSet;

public class BoardState {

    /*
     * Array that stores board
     * white figure values: b(=bishop), k(=king), n(=knight), p(=pawn), q(=queen), r(=rook)
     * black figure values: B(=bishop), K(=king), N(=knight), P(=pawn), Q(=queen), R(=rook)
     */
    public char[] board = new char[64];
    /*
     * BitSet of length 64 to store if figure in tile i has already been moved
     */
    public BitSet has_been_moved = new BitSet();
    /*
     * Array of length 2 to store the indices of the 2 tiles involved in the last move
     */
    public int[] last_moved = new int[2];

    /*
     * Initializes the board with the start values
     */
    public void init() {
        has_been_moved.clear();
        last_moved[0] = -1; last_moved[1] = -1;

        board[0] = 'R';
        board[1] = 'N';
        board[2] = 'B';
        board[3] = 'Q';
        board[4] = 'K';
        board[5] = 'B';
        board[6] = 'N';
        board[7] = 'R';
        for (int i = 8; i < 16; i++) {
            board[i] = 'P';
        }
        for (int i = 48; i < 56; i++) {
            board[i] = 'p';
        }
        board[56] = 'r';
        board[57] = 'n';
        board[58] = 'b';
        board[59] = 'q';
        board[60] = 'k';
        board[61] = 'b';
        board[62] = 'n';
        board[63] = 'r';
    }

    /*
     * Returns a deep copy of the current board
     */
    public BoardState copy() {
        BoardState b = new BoardState();
        b.board = Arrays.copyOf(this.board, 64);
        b.has_been_moved = (BitSet) this.has_been_moved.clone();
        b.last_moved[0] = this.last_moved[0];
        b.last_moved[1] = this.last_moved[1];
        return b;
    }
}
