/**
 * ai opponent
 */

package Opponent;

import GameLogic.*;
import Main.Settings;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Opponent {

    private static int calls, hits;
    /**
     * memoization tables that stores already found board configuration ids with their corresponding evaluation
     */
    private static final HashMap<Integer, Integer> table_max = new HashMap<>();
    private static final HashMap<Integer, Integer> table_min = new HashMap<>();
    /**
     * counter to track how many turns have been played by the computer
     */
    private static int turn_count = 3;

    /**
     * evaluates the best next move from the computer's perspective
     * @param board the current board configuration
     * @return the best move
     */
    public static Move get_next(BoardConfig board) {
        if (turn_count < 3) {
            turn_count++;
            return Openings.get_next();
        }

        table_max.clear();
        table_min.clear();
        calls = 0; hits = 0;

        int max = Integer.MIN_VALUE;
        Move max_move = null;
        Figure.Color color = (Settings.PLAYER_COLOR == Figure.Color.WHITE) ? Figure.Color.BLACK : Figure.Color.WHITE;
        Figure.Color next_color = (color == Figure.Color.WHITE) ? Figure.Color.BLACK : Figure.Color.WHITE;
        int rec_depth = Settings.REC_DEPTH - 1;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                ArrayList<Move> moves = MoveTools.get_moves(board, x, y, color);
                if (moves == null) {
                    continue;
                }
                for (Move m : moves) {
                    BoardConfig board_new = MoveTools.exec_move(board, m);
                    if (EvalTools.is_checked(board_new, color)) {
                        continue;
                    }
                    if (EvalTools.checkmate(board_new, next_color) == EvalTools.Checkmate.TRUE) {
                        return m;
                    }
                    int val;
                    int id = board_new.id(); calls++;
                    if (table_max.containsKey(id)) {
                        val = table_max.get(id); hits++;
                    } else {
                        val = mini(board_new, rec_depth, next_color);
                        table_max.put(id, val);
                    }
                    if (val > max) {
                        max = val;
                        max_move = m;
                    }
                }
            }
        }
        table_max.clear();
        table_min.clear();
        return max_move;
    }

    /**
     * helper method for mini-max algorithm, evaluates the current position in the recursion tree
     * @param board the current board state
     * @param rec_depth the current recursion depth
     * @param color the current color
     * @return the biggest board value for the given position
     */
    private static int maxi(BoardConfig board, int rec_depth, Figure.Color color) {
        if (rec_depth < 1) {
            return EvalTools.eval_board(board);
        }
        int max = Integer.MIN_VALUE;
        Figure.Color next_color = (color == Figure.Color.WHITE) ? Figure.Color.BLACK : Figure.Color.WHITE;
        int r = rec_depth - 1;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                ArrayList<Move> moves = MoveTools.get_moves(board, x, y, color);
                if (moves == null) {
                    continue;
                }
                for (Move m : moves) {
                    BoardConfig board_new = MoveTools.exec_move(board, m);
                    if (EvalTools.checkmate(board_new, next_color) == EvalTools.Checkmate.TRUE) {
                        return Integer.MAX_VALUE;
                    }
                    int val;
                    int id = board_new.id(); calls++;
                    if (table_max.containsKey(id)) {
                        val = table_max.get(id); hits++;
                    } else {
                        val = mini(board_new, r, next_color);
                        table_max.put(id, val);
                    }
                    if (val > max) {
                        max = val;
                    }
                }
            }
        }
        return max;
    }

    /**
     * helper method for mini-max algorithm, evaluates the current position in the recursion tree
     * @param board the current board state
     * @param rec_depth the current recursion depth
     * @param color the current color
     * @return the smallest board value for the given position
     */
    private static int mini(BoardConfig board, int rec_depth, Figure.Color color) {
        if (rec_depth < 1) {
            return EvalTools.eval_board(board);
        }
        int min = Integer.MAX_VALUE;
        Figure.Color next_color = (color == Figure.Color.WHITE) ? Figure.Color.BLACK : Figure.Color.WHITE;
        int r = rec_depth - 1;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                ArrayList<Move> moves = MoveTools.get_moves(board, x, y, color);
                if (moves == null) {
                    continue;
                }
                for (Move m : moves) {
                    BoardConfig board_new = MoveTools.exec_move(board, m);
                    if (EvalTools.checkmate(board_new, next_color) == EvalTools.Checkmate.TRUE) {
                        return Integer.MIN_VALUE;
                    }
                    int val;
                    int id = board_new.id(); calls++;
                    if (table_min.containsKey(id)) {
                        val = table_min.get(id); hits++;
                    } else {
                        val = maxi(board_new, r, next_color);
                        table_min.put(id, val);
                    }
                    if (val < min) {
                        min = val;
                    }
                }
            }
        }
        return min;
    }
}
