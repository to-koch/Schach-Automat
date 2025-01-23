/**
 * Contains various methods required for finding and evaluating chess moves
 */

package GameLogic;

import java.util.ArrayList;

public abstract class MoveTools {

    /*
     * the "heat" value for every tile (how valuable a tile is)
     */
    private static int[] heat_map = {
            1, 1, 1, 1, 1, 1, 1, 1,
            1, 2, 2, 2, 2, 2, 2, 1,
            1, 2, 3, 3, 3, 3, 2, 1,
            1, 2, 3, 4, 4, 3, 2, 1,
            1 ,2 ,3 ,4 ,4, 3, 2, 1,
            1, 2, 3, 3, 3, 3, 2, 1,
            1, 2, 2, 2, 2, 2, 2, 1,
            1, 1, 1, 1, 1, 1, 1, 1
    };

    /*
     * Returns all possible moves that can be executed by the figure specified by the coordinates
     */
    public static ArrayList<Move> get_possible_moves(BoardState state, int x, int y) {
        int index = y * 8 + x;
        char code = state.board[index];
        ArrayList<Move> result = new ArrayList<>();

        switch(code) {
            case 'B': // bishop
            case 'b':
                for (int dest_x = 0; dest_x < 7; dest_x++) {
                    if (x == dest_x) {
                        continue;
                    }
                    int delta = dest_x - x;
                    int dest_y = y + delta;
                    if (!(dest_y > 7 || dest_y < 0) && check_path_clear(state, x, y, dest_x, dest_y)) {
                        result.add(new Move(x, y, dest_x, dest_y));
                    }
                    dest_y = y - delta;
                    if (!(dest_y > 7 || dest_y < 0) && check_path_clear(state, x, y, dest_x, dest_y)) {
                        result.add(new Move(x, y, dest_x, dest_y));
                    }
                }
                return result;
            case 'K': // king
            case 'k':
                for (int dest_x = x - 1; dest_x < x + 2; dest_x++) {
                    for (int dest_y = y - 1; dest_y < y + 2; dest_y++) {
                        if (dest_x == x && dest_y == y) {
                            continue;
                        }
                        if (dest_x > 7 || dest_x < 0 || dest_y > 7 || dest_y < 0) {
                            continue;
                        }
                        if (check_path_clear(state, x, y, dest_x, dest_y)) {
                            result.add(new Move(x, y,  dest_x, dest_y));
                        }
                    }
                }
                return result;
            case 'N': // knight
            case 'n':
                int[] dest_xs = {x-1, x-2, x-2, x-1, x+1, x+2, x+2, x+1};
                int[] dest_ys = {y-2, y-1, y+1, y+2, y+2, y+1, y-1, y-2};
                for (int i = 0; i < dest_xs.length; i++) {
                    int dest_x = dest_xs[i];
                    int dest_y = dest_ys[i];
                    if (dest_x > 7 || dest_x < 0 || dest_y > 7 || dest_y < 0) {
                        continue;
                    }
                    result.add(new Move(x, y, dest_x, dest_y));
                }
                return result;
            case 'P': // pawn black
                if (y < 7) {
                    result.add(new Move(x, y, x, y + 1));
                }
                if (!state.has_been_moved.get(y * 8 + x)) {
                    result.add(new Move(x, y, x, y + 2));
                }
                return result;
            case 'p': // pawn white
                if (y > 0) {
                    result.add(new Move(x, y, x, y - 1));
                }
                if (!state.has_been_moved.get(y * 8 + x)) {
                    result.add(new Move(x, y, x, y - 2));
                }
                return result;
            case 'Q': // queen
            case 'q':
                for (int dest_x = 0; dest_x < 7; dest_x++) {
                    if (x == dest_x) {
                        continue;
                    }
                    int delta = dest_x - x;
                    int dest_y = y + delta;
                    if (!(dest_y > 7 || dest_y < 0) && check_path_clear(state, x, y, dest_x, dest_y)) {
                        result.add(new Move(x, y, dest_x, dest_y));
                    }
                    dest_y = y - delta;
                    if (!(dest_y > 7 || dest_y < 0) && check_path_clear(state, x, y, dest_x, dest_y)) {
                        result.add(new Move(x, y, dest_x, dest_y));
                    }
                }
                for (int dest_x = 0; dest_x < 7; dest_x++) {
                    if (dest_x == x) {
                        continue;
                    }
                    if (check_path_clear(state, x, y, dest_x, y)) {
                        result.add(new Move(x, y, dest_x, y));
                    }
                }
                for (int dest_y = 0; dest_y < 7; dest_y++) {
                    if (dest_y == y) {
                        continue;
                    }
                    if (check_path_clear(state, x, y, x, dest_y)) {
                        result.add(new Move(x, y, x, dest_y));
                    }
                }
                return result;
            case 'R': // rook
            case 'r':
                for (int dest_x = 0; dest_x < 7; dest_x++) {
                    if (dest_x == x) {
                        continue;
                    }
                    if (check_path_clear(state, x, y, dest_x, y)) {
                        result.add(new Move(x, y, dest_x, y));
                    }
                }
                for (int dest_y = 0; dest_y < 7; dest_y++) {
                    if (dest_y == y) {
                        continue;
                    }
                    if (check_path_clear(state, x, y, x, dest_y)) {
                        result.add(new Move(x, y, x, dest_y));
                    }
                }
                return result;
        }
        return null;
    }

    /*
     * checks if the path in between 2 tiles is clear
     */
    public static boolean check_path_clear(BoardState state, int src_x, int src_y, int dest_x, int dest_y) {
        boolean diagonal_move = Math.abs(dest_x - src_x) == Math.abs(dest_y - src_y);
        boolean horizontal_move = dest_y == src_y;
        boolean vertical_move = dest_x == src_x;

        if (diagonal_move) {
            int delta_x = 1;
            int delta_y = 1;
            int start_x = src_x;
            int end_x = dest_x;
            int y = src_y + 1;
            if (start_x < end_x) {
                delta_x = -1;
                start_x = dest_x;
                end_x = src_x;
            }
            if (src_y < dest_y) {
                delta_y = -1;
                y = dest_y + 1;
            }
            for (int x = start_x + 1; x < end_x; x += delta_x, y += delta_y) {
                int i = y * 8 + x;
                if ((int) state.board[i] != 0) {
                    return false;
                }
            }
        } else if (horizontal_move) {
            int x_max = Math.max(src_x, dest_x);
            int x_min = Math.min(src_x, dest_x);
            for (int x = x_min + 1; x < x_max; x++) {
                int i = src_y * 8 + x;
                if ((int) state.board[i] != 0) {
                    return false;
                }
            }
        } else if (vertical_move) {
            int y_max = Math.max(src_y, dest_y);
            int y_min = Math.min(src_y, dest_y);
            for (int y = y_min + 1; y < y_max; y++) {
                int i = y * 8 + src_x;
                if ((int) state.board[i] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * executes a given move
     */
    public static boolean execute_move(BoardState state, Move move, char color) {
        if (color != 'w' && color != 'b') {
            return false;
        }

        int dest_idx = move.dets_y * 8 + move.dest_x;
        int src_idx = move.src_y * 8 + move.src_x;
        char dest_fig = state.board[dest_idx];
        char src_fig = state.board[src_idx];
        char dest_color = 'w';
        if (dest_fig >= 65 && dest_fig <= 90) {
            dest_color = 'b';
        }
        if (color == dest_color) { // tile is occupied by figure of same color
            return false;
        }

        // change array entries
        state.has_been_moved.clear(src_idx);
        state.has_been_moved.set(dest_idx);
        state.board[src_fig] = 0;
        state.board[dest_fig] = src_fig;
        // adjust last moved entries
        state.last_moved[0] = src_idx;
        state.last_moved[1] = dest_idx;
        // adjust king index if moved
        if (src_fig == 'k') {
            state.king_indicies[0] = dest_idx;
        } else if (src_fig == 'K') {
            state.king_indicies[1] = dest_idx;
        }
        return true;
    }

    /*
     * evaluate the given board from colors perspective
     */
    public static int eval_board(BoardState state, char color) {
        int prefix;
        if (color == 'w') {
            prefix = 1;
        } else if (color == 'b') {
            prefix = -1;
        } else {
            return 0;
        }

        int result = 0;
        for (int i = 0; i < state.board.length; i++) {
            char code = state.board[i];
            if (code >= 97 && code <= 122) {
                result += (prefix * Piece.get_value(code) * heat_map[i]);
            } else if (code >= 65 && code <= 90) {
                result -= (prefix * Piece.get_value(code) * heat_map[i]);
            }
        }
        return result;
    }

}
