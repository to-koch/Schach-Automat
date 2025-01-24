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
                    if (state.board[(y + 1) * 8 + x] == 0) {
                        result.add(new Move(x, y, x, y + 1));
                    }
                    if (x > 0 && state.board[(y + 1) * 8 + x - 1] != 0) {
                        result.add(new Move(x, y, x - 1, y + 1));
                    }
                    if (x < 7 && state.board[(y + 1) * 8 + x + 1] != 0) {
                        result.add(new Move(x, y, x + 1, y + 1));
                    }
                }
                if (!state.has_been_moved.get(y * 8 + x) && state.board[(y + 2) * 8 + x] == 0) {
                    result.add(new Move(x, y, x, y + 2));
                }
                return result;
            case 'p': // pawn white
                if (y > 0) {
                    if (state.board[(y - 1) * 8 + x] == 0) {
                        result.add(new Move(x, y, x, y - 1));
                    }
                    if (x > 0 && state.board[(y - 1) * 8 + x - 1] != 0) {
                        result.add(new Move(x, y, x - 1, y - 1));
                    }
                    if (x < 7 && state.board[(y - 1) * 8 + x + 1] != 0) {
                        result.add(new Move(x, y, x + 1, y - 1));
                    }
                }
                if (!state.has_been_moved.get(y * 8 + x) && state.board[(y - 2) * 8 + x] == 0) {
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
            if (src_x < dest_x) {
                int delta_y = (int) Math.signum(dest_y - src_y);
                int y = src_y + delta_y;
                for (int x = src_x + 1; x < dest_x; x++, y += delta_y) {
                    // Calculate index in board array
                    int i = y * 8 + x;
                    if ((int) state.board[i] != 0) {
                        return false;
                    }
                }
            } else {
                int delta_y = (int) Math.signum(dest_y - src_y);
                int y = src_y + delta_y;
                for (int x = src_x - 1; x > dest_x; x--, y += delta_y) {
                    // Calculate index in board array
                    int i = y * 8 + x;
                    if ((int) state.board[i] != 0) {
                        return false;
                    }
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
     * copies board and returns a new board with the given move executed
     */
    public static BoardState execute_move(BoardState state, Move move, char color) {
        if ((color != 'w' && color != 'b') || move == null) {
            return null;
        }

        int dest_idx = move.dest_y * 8 + move.dest_x;
        int src_idx = move.src_y * 8 + move.src_x;
        char dest_fig = state.board[dest_idx];
        char src_fig = state.board[src_idx];
        if ((color == 'b' && Character.isUpperCase(dest_fig)) || (color == 'w' && Character.isLowerCase(dest_fig))) { // tile is occupied by figure of same color
            return null;
        }

        BoardState state_c = state.copy();

        // change array entries
        state_c.has_been_moved.clear(src_idx);
        state_c.has_been_moved.set(dest_idx);
        state_c.board[src_idx] = 0;
        state_c.board[dest_idx] = src_fig;
        // adjust last moved entries
        state_c.last_moved[0] = src_idx;
        state_c.last_moved[1] = dest_idx;
        // adjust king index if moved
        if (src_fig == 'k') {
            state_c.king_indicies[0] = dest_idx;
        } else if (src_fig == 'K') {
            state_c.king_indicies[1] = dest_idx;
        }
        return state_c;
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
                result += (prefix * Piece.get_value(code)/* + heat_map[i]*/);
            } else if (code >= 65 && code <= 90) {
                result -= (prefix * Piece.get_value(code)/* + heat_map[i]*/);
            }
        }
        return result;
    }

    /*
     * checks if the king of the given color is in check
     */
    public static boolean check_chess(BoardState state, char color) {
        int king_idx = state.king_indicies[0];
        if (color == 'b') {
            king_idx = state.king_indicies[1];
        }
        int king_x = king_idx % 8;
        int king_y = king_idx / 8;

        // iterate over board and check if king is threatened
        for (int i = 0; i < 64; i++) {
            int x = i % 8;
            int y = i / 8;
            if (x == king_x && y == king_y) {
                continue;
            }
            int fig = state.board[i];
            if (fig == 0) { // tile is empty
                continue;
            }
            char dest_color = 'w';
            if (fig >= 65 && fig <= 90) {
                dest_color = 'b';
            }
            if (color == dest_color) { // tile is occupied by figure of same color
                continue;
            }
            if (check_path_clear(state, x, y, king_x, king_y)) {
                return true;
            }
        }
        return false;
    }

    /*
     * checks if the given color is in check mate
     */
    public static boolean check_chess_mate(BoardState state, char color) {
        if (!check_chess(state, color)) {
            return false;
        }

        int king_idx = state.king_indicies[0];
        if (color == 'b') {
            king_idx = state.king_indicies[1];
        }
        int king_x = king_idx % 8;
        int king_y = king_idx / 8;

        // iterate over board and kings moves and check if king still is threatend
        ArrayList<Move> king_moves = get_possible_moves(state, king_x, king_y);
        for (Move move :king_moves) {
            BoardState state_c = execute_move(state, move, color);
            if (state_c != null) {
                if (!check_chess(state_c, color)) {
                    return false;
                }
            }
        }
        return true;
    }
}
