/**
 * Methods to get and evaluate moves
 */

package GameLogic;

import java.util.Arrays;

public abstract class MoveTools {

    /**
     * finds all moves that can be executed by a figure
     * @param board the board on which to determine the moves
     * @param x the x-coordinate of the figure
     * @param y the y-coordinate of the figure
     * @param color the color of the currently active player
     * @return array of possible moves
     */
    public static Move[] get_moves(BoardConfig board, int x, int y, Figure.Color color) {
        int index = y * 8 + x;
        Figure.Color fig_color = Figure.get_color(board.board[index]);
        Figure.Type fig_type = Figure.get_type(board.board[index]);
        // return if tile does not contain a valid figure or a figure of different color
        if (fig_type == null || fig_color == null || fig_color != color) {
            return null;
        }

        Move[] moves = get_all_moves(x, y, fig_type, fig_color);
        // filter illegal moves
        int count = 0;
        Move[] tmp = new Move[moves.length];
        for (Move m : moves) {
            if (m.y_dest < 0 || m.y_dest > 7 || m.x_dest < 0 || m.x_dest > 7) {
                continue;
            }
            tmp[count] = m;
            count++;
        }

        return Arrays.copyOf(tmp, count);
    }

    /**
     * helper method that finds all moves that could potentially be possible
     * @param x the x-coordinate of the figure
     * @param y the y-coordinate of the figure
     * @param fig_type the type of the figure
     * @param color the color of the figure
     * @return array of potentially possible moves
     */
    private static Move[] get_all_moves(int x, int y, Figure.Type fig_type, Figure.Color color) {
        Move[] moves = null;
        if (fig_type == Figure.Type.BISHOP) {
            moves = new Move[28];
            for (int i = 1; i < 8; i++) {
                moves[i] = new Move(x, y, x + i, y + i);
            }
            for (int i = 1; i < 8; i++) {
                moves[7 + i] = new Move(x, y, x + i, y - i);
            }
            for (int i = 1; i < 8; i++) {
                moves[14 + i] = new Move(x, y, x - i, y + i);
            }
            for (int i = 1; i < 8; i++) {
                moves[21 + i] = new Move(x, y, x - i, y - i);
            }
        } else if (fig_type == Figure.Type.KING) {
            moves = new Move[8];
            for (int i = 0; i < 3; i++) {
                moves[2 * i] = new Move(x, y, x - 1 + i, y - 1);
                moves[2 * i + 1] = new Move(x, y, x - 1 + i, y + 1);
            }
            moves[6] = new Move(x, y, x - 1, y);
            moves[7] = new Move(x, y, x + 1, y);
        } else if (fig_type == Figure.Type.KNIGHT) {
            moves = new Move[8];
            moves[0] = new Move(x, y, x - 1, y - 2);
            moves[1] = new Move(x, y, x + 1, y - 2);
            moves[2] = new Move(x, y, x - 1, y + 2);
            moves[3] = new Move(x, y, x + 1, y + 2);
            moves[4] = new Move(x, y, x - 2, y - 1);
            moves[5] = new Move(x, y, x - 2, y + 1);
            moves[6] = new Move(x, y, x + 2, y - 1);
            moves[7] = new Move(x, y, x + 2, y + 1);
        } else if (fig_type == Figure.Type.PAWN) {

        } else if (fig_type == Figure.Type.QUEEN) {

        } else if (fig_type == Figure.Type.ROOK) {

        }
        return moves;
    }
}
