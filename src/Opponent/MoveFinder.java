/**
 * evaluates the best move the opponent can perform
 */

package Opponent;

import GameLogic.BoardState;
import GameLogic.Turn;

public abstract class MoveFinder {

    /*
     * returns the best next move for the given board state
     */
    public static Turn get_best_move(BoardState state, char color, int depth) {
        if (color != 'w' && color != 'b') {
            return null;
        }
        return null;
    }

    /*
     * max and min methods for the minimax algorithm
     */
    private static int max() {
        return 0;
    }

    private static int min() {
        return 0;
    }
}
