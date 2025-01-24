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
            int max = -10000;

            // iterate over every move for every figure
            for (int i = 0; i < 64; i++) {
                char fig = state.board[i];
                if (state.board[i] == 0 || (color == 'w' && Character.isUpperCase(fig)) || (color == 'b' && Character.isLowerCase(fig))) {
                    continue;
                }
                int x = i % 8;
                int y = i / 8;
                ArrayList<Move> move_list = MoveTools.get_possible_moves(state, x, y);
                for (Move move : move_list) {
                    BoardState state_c = MoveTools.execute_move(state, move, color);
                    if (state_c != null) {
                        int val = min(state_c, next_color, depth-1, next_color);
                        if (val > max) {
                            max = val;
                            best_move = move;
                        }
                    }
                }
            }
            System.out.println(best_move.src_x + " " + best_move.src_y + " : " + best_move.dest_x + " " + best_move.dest_y);
            return best_move;
        }
    }

    /*
     * max and min methods for the minimax algorithm
     */
    private static int max(BoardState state, char color, int depth, char color_eval) {
        if (depth < 1) {
            return MoveTools.eval_board(state, color_eval);
        } else {
            char next_color = 'w';
            if (color == 'w') {
                next_color = 'b';
            }
            int max = -10000;
            // iterate over every move for every figure
            for (int i = 0; i < 64; i++) {
                char fig = state.board[i];
                if (state.board[i] == 0 || (color == 'w' && Character.isUpperCase(fig)) || (color == 'b' && Character.isLowerCase(fig))) {
                    continue;
                }
                int x = i % 8;
                int y = i / 8;
                ArrayList<Move> move_list = MoveTools.get_possible_moves(state, x, y);
                for (Move move : move_list) {
                    BoardState state_c = MoveTools.execute_move(state, move, color);
                    if (state_c != null) {
                        int val = min(state_c, next_color, depth-1, next_color);
                        if (val > max) {
                            max = val;
                        }
                    }
                }
            }
            return max;
        }
    }

    private static int min(BoardState state, char color, int depth, char color_eval) {
        if (depth < 1) {
            return MoveTools.eval_board(state, color_eval);
        } else {
            char next_color = 'w';
            if (color == 'w') {
                next_color = 'b';
            }
            int min = 10000;
            // iterate over every move for every figure
            for (int i = 0; i < 64; i++) {
                char fig = state.board[i];
                if (state.board[i] == 0 || (color == 'b' && Character.isUpperCase(fig)) || (color == 'w' && Character.isLowerCase(fig))) {
                    continue;
                }
                int x = i % 8;
                int y = i / 8;
                ArrayList<Move> move_list = MoveTools.get_possible_moves(state, x, y);
                for (Move move : move_list) {
                    BoardState state_c = MoveTools.execute_move(state, move, color);
                    if (state_c != null) {
                        int val = max(state_c, next_color, depth-1, next_color);
                        if (val < min) {
                            min = val;
                        }
                    }
                }
            }
            return min;
        }
    }
}
