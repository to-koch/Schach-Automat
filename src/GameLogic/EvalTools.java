/**
 * Methods for evaluating a board configuration
 */

package GameLogic;

import Main.Settings;
import java.util.ArrayList;

public abstract class EvalTools {

    /**
     * all possible checkmate states
     */
    public enum Checkmate {TRUE, FALSE, DRAW}
    /**
     * basic value of each figure {bishop, king, knight, pawn, queen, rook}
     */
    private static final int[] fig_values = {3, 1000, 3, 1, 9, 5};

    /**
     * evaluates the board
     * @param board the board configuration to be evaluated
     * @return <0 if the human player is leading, >0 if the computer is leading
     */
    public static int eval_board(BoardConfig board) {
        int count = 0;
        for (int i = 0; i < 64; i++) {
            char code = board.board[i];
            if (code != 0) {
                int prefix = (Settings.PLAYER_COLOR == Figure.get_color(code)) ? -1 : 1;
                count += (prefix * fig_values[Figure.get_type(code).ordinal()]);
            }
        }
        return count;
    }

    /**
     * checks if the given color still owns its king
     * @param board the current boatd state
     * @param color the color to check
     * @return true if the given color still owns its king else false
     */
    public static boolean has_king(BoardConfig board, Figure.Color color) {
        for (int i = 0; i < 64; i++) {
            char code = board.board[i];
            if (code == 0 || Figure.get_color(code) != color || Figure.get_type(code) != Figure.Type.KING) {
                continue;
            }
            return true;
        }
        return false;
    }

    /**
     * checks if the king of the specified color is in check
     * @param board the current board
     * @param color the color of the king to be checked
     * @return true if the king is in check else false
     */
    public static boolean is_checked(BoardConfig board, Figure.Color color) {
        int king_index = board.king_indices[color.ordinal()];
        Figure.Color opp_color = (color == Figure.Color.WHITE) ? Figure.Color.BLACK : Figure.Color.WHITE;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                ArrayList<Move> moves = MoveTools.get_moves(board, x, y, opp_color);
                if (moves == null) {
                    continue;
                }
                for (Move m : moves) {
                    if (m.y_dest * 8 + m.x_dest == king_index) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * checks if a color is in checkmate
     * @param board the current board
     * @param color the color to be checked
     * @return true if the color is in checkmate else false
     */
    public static Checkmate checkmate(BoardConfig board, Figure.Color color) {
        boolean has_moves_remaining = false;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                ArrayList<Move> moves = MoveTools.get_moves(board, x, y, color);
                if (moves == null) {
                    continue;
                }
                has_moves_remaining = true;
                for (Move m : moves) {
                    BoardConfig board_new = MoveTools.exec_move(board, m);
                    if (!is_checked(board_new, color)) {
                        return Checkmate.FALSE;
                    }
                }
            }
        }
        if (!has_moves_remaining) {
            return Checkmate.DRAW;
        }
        return Checkmate.TRUE;
    }
}