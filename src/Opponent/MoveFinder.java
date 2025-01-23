/**
 * evaluates the best move the opponent can perform
 */

package Opponent;

import GameLogic.BoardState;
import GameLogic.Move;
import GameLogic.MoveTools;

import java.util.ArrayList;

public abstract class MoveFinder {

    /*
     * returns the best next move for the given board state
     */
    public static Move get_best_move(BoardState state, char color, int depth) {
        if ((color != 'w' && color != 'b') || depth < 1) {
            return null;
        } else {
            char next_color = 'w';
            if (color == 'w') {
                next_color = 'b';
            }

            Move best_move = null;
            int max = -1000;

            // iterate over every move for every figure
            for (int i = 0; i < 64; i++) {
                if (state.board[i] == 0) {
                    continue;
                }
                int x = i % 8;
                int y = i / 8;
                ArrayList<Move> move_list = MoveTools.get_possible_moves(state, x, y);
                for (Move move : move_list) {
                    BoardState state_copy = state.copy();
                    if (MoveTools.execute_move(state_copy, move, color)) {
                        int val = min();
                        if (val > max) {
                            max = val;
                            best_move = move;
                        }
                    }
                }
            }
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
