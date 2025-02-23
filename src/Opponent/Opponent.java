/**
 * ai opponent
 */

package Opponent;

import GameLogic.*;
import Main.Settings;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Opponent {

    /**
     * memoization table that stores already found board configuration ids with their corresponding evaluation
     */
    private static HashMap<Integer, Integer> table;

    /**
     * evaluates the best next move from the computer's perspective
     * @param board the current board configuration
     * @return the best move
     */
    public static Move get_next(BoardConfig board) {
        table = new HashMap<>();

        int max = Integer.MIN_VALUE;
        Move max_move = null;
        Figure.Color color = (Settings.PLAYER_COLOR == Figure.Color.WHITE) ? Figure.Color.BLACK : Figure.Color.WHITE;
        Figure.Color next_color = (color == Figure.Color.WHITE) ? Figure.Color.BLACK : Figure.Color.WHITE;
        int rec_depth = Settings.REC_DEPTH - 1;

        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                ArrayList<Move> moves = MoveTools.get_moves(board, x, y, color);
                if (moves == null) {
                    continue;
                }
                for (Move m : moves) {
                    BoardConfig board_new = MoveTools.exec_move(board, m);
                    int val;
                    int id = board_new.id();
                    if (table.containsKey(id)) {
                        val = table.get(id);
                    } else {
                        val = mini(board_new, rec_depth, next_color);
                    }
                    if (val > max) {
                        max = val;
                        max_move = m;
                    }
                    table.put(board_new.id(), val);
                }
            }
        }
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

        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                ArrayList<Move> moves = MoveTools.get_moves(board, x, y, color);
                if (moves == null) {
                    continue;
                }
                for (Move m : moves) {
                    BoardConfig board_new = MoveTools.exec_move(board, m);
                    int val;
                    int id = board_new.id();
                    if (table.containsKey(id)) {
                        val = table.get(id);
                    } else {
                        val = mini(board_new, r, next_color);
                    }
                    if (val > max) {
                        max = val;
                    }
                    table.put(board_new.id(), val);
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

        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                ArrayList<Move> moves = MoveTools.get_moves(board, x, y, color);
                if (moves == null) {
                    continue;
                }
                for (Move m : moves) {
                    BoardConfig board_new = MoveTools.exec_move(board, m);
                    int val;
                    int id = board_new.id();
                    if (table.containsKey(id)) {
                        val = table.get(id);
                    } else {
                        val = maxi(board_new, r, next_color);
                    }
                    if (val < min) {
                        min = val;
                    }
                    table.put(board_new.id(), val);
                }
            }
        }
        return min;
    }
}
