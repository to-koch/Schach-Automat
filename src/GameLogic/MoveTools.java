/**
 * Methods to get and evaluate moves
 */

package GameLogic;

import java.util.ArrayList;

public abstract class MoveTools {

    /**
     * finds all moves that can be executed by a figure
     * @param board the board on which to determine the moves
     * @param x the x-coordinate of the figure
     * @param y the y-coordinate of the figure
     * @param color the color of the currently active player
     * @return list of possible moves
     */
    public static ArrayList<Move> get_moves(BoardConfig board, int x, int y, Figure.Color color) {
        int index = y * 8 + x;
        Figure.Color fig_color = Figure.get_color(board.board[index]);
        Figure.Type fig_type = Figure.get_type(board.board[index]);
        // return if tile does not contain a valid figure or a figure of different color
        if (fig_type == null || fig_color == null || fig_color != color) {
            return null;
        }

        ArrayList<Move> moves =  new ArrayList<>();
        if (fig_type == Figure.Type.BISHOP) {

        } else if (fig_type == Figure.Type.KING) {

        } else if (fig_type == Figure.Type.KNIGHT) {

        } else if (fig_type == Figure.Type.PAWN) {

        } else if (fig_type == Figure.Type.QUEEN) {

        } else if (fig_type == Figure.Type.ROOK) {

        }

        return moves;
    }


}
