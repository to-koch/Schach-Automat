/**
 * Methods to for finding and executing moves
 */

package GameLogic;

import java.util.ArrayList;

public abstract class MoveTools {

    /**
     * finds all moves that can be executed by a figure (ensures that path is clear as well)
     * @param board the board on which to determine the moves
     * @param x the x-coordinate of the figure
     * @param y the y-coordinate of the figure
     * @param color the color of the currently active player
     * @return list of possible moves
     */
    public static ArrayList<Move> get_moves(BoardConfig board, int x, int y, Figure.Color color) {
        if (x > 7 || x < 0 || y > 7 || y < 0) {
            return null;
        }
        int index = y * 8 + x;
        Figure.Color fig_color = Figure.get_color(board.board[index]);
        Figure.Type fig_type = Figure.get_type(board.board[index]);
        boolean already_moved = Figure.get_already_moved(board.board[index]);
        // aboard if tile does not contain a valid figure or a figure of different color
        if (fig_type == null || fig_color == null || fig_color != color) {
            return null;
        }

        ArrayList<Move> moves = new ArrayList<>();
        if (fig_type == Figure.Type.BISHOP) {
            for (int i = 1; i < 8; i++) {
                int x_new = x + i;
                int y_new = y + i;
                if (x_new > 7 || y_new > 7) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y_new));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int x_new = x + i;
                int y_new = y - i;
                if (x_new > 7 || y_new < 0) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y_new));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int x_new = x - i;
                int y_new = y + i;
                if (x_new < 0 || y_new > 7) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y_new));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int x_new = x - i;
                int y_new = y - i;
                if (x_new < 0 || y_new < 0) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y_new));
                    break;
                } else {
                    break;
                }
            }
        } else if (fig_type == Figure.Type.KING) {
            int[] x_new = {x-1, x, x+1, x-1, x+1, x-1, x, x+1};
            int[] y_new = {y-1, y-1, y-1, y, y, y+1, y+1, y+1};
            for (int i = 0; i < 8; i++) {
                if (x_new[i] > 7 || x_new[i] < 0 || y_new[i] > 7 || y_new[i] < 0) {
                    continue;
                }
                char dest_fig = board.board[y_new[i] * 8 + x_new[i]];
                if (dest_fig == 0 || Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new[i], y_new[i]));
                }
            }
        } else if (fig_type == Figure.Type.KNIGHT) {
            int[] x_new = {x-1, x+1, x-1, x+1, x-2, x-2, x+2, x+2};
            int[] y_new = {y-2, y-2, y+2, y+2, y-1, y+1, y-1, y+1};
            for (int i = 0; i < 8; i++) {
                if (x_new[i] > 7 || x_new[i] < 0 || y_new[i] > 7 || y_new[i] < 0) {
                    continue;
                }
                char dest_fig = board.board[y_new[i] * 8 + x_new[i]];
                if (dest_fig == 0 || Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new[i], y_new[i]));
                }
            }
        } else if (fig_type == Figure.Type.PAWN) {
            int delta_y = (fig_color == Figure.Color.WHITE) ? -1 : 1;
            int upper = already_moved ? 2 : 3;
            for (int i = 1; i < upper; i++) {
                int y_new = y + i * delta_y;
                if (y_new > 7 || y_new < 0 || board.board[y_new * 8 + x] != 0) {
                    break;
                }
                moves.add(new Move(x, y, x, y_new));
            }
            for (int i = -1; i < 2; i += 2) {
                int y_new = y + delta_y;
                int x_new = x + i;
                if (y_new > 7 || y_new < 0 || x_new > 7 || x_new < 0) {
                    continue;
                }
                char dest_fig = board.board[y_new * 8 + x_new];
                if (dest_fig != 0 && Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y_new));
                }
            }
        } else if (fig_type == Figure.Type.QUEEN) {
            for (int i = 1; i < 8; i++) {
                int x_new = x + i;
                int y_new = y + i;
                if (x_new > 7 || y_new > 7) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y_new));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int x_new = x + i;
                int y_new = y - i;
                if (x_new > 7 || y_new < 0) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y_new));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int x_new = x - i;
                int y_new = y + i;
                if (x_new < 0 || y_new > 7) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y_new));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int x_new = x - i;
                int y_new = y - i;
                if (x_new < 0 || y_new < 0) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y_new));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int x_new = x + i;
                if (x_new > 7) {
                    break;
                }
                char dest_fig = board.board[y * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int x_new = x - i;
                if (x_new < 0) {
                    break;
                }
                char dest_fig = board.board[y * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int y_new = y + i;
                if (y_new > 7) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x, y_new));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int y_new = y - i;
                if (y_new < 0) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x, y_new));
                    break;
                } else {
                    break;
                }
            }
        } else if (fig_type == Figure.Type.ROOK) {
            for (int i = 1; i < 8; i++) {
                int x_new = x + i;
                if (x_new > 7) {
                    break;
                }
                char dest_fig = board.board[y * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int x_new = x - i;
                if (x_new < 0) {
                    break;
                }
                char dest_fig = board.board[y * 8 + x_new];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x_new, y));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x_new, y));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int y_new = y + i;
                if (y_new > 7) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x, y_new));
                    break;
                } else {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                int y_new = y - i;
                if (y_new < 0) {
                    break;
                }
                char dest_fig = board.board[y_new * 8 + x];
                if (dest_fig == 0) {
                    moves.add(new Move(x, y, x, y_new));
                } else if (Figure.get_color(dest_fig) != color) {
                    moves.add(new Move(x, y, x, y_new));
                    break;
                } else {
                    break;
                }
            }
        }
        return moves;
    }

    /**
     * executes a given move, assuming that the move is legal
     * @param board the board configuration on which to execute the move
     * @param move the move to be executed
     * @return a new board configuration
     */
    public static BoardConfig exec_move(BoardConfig board, Move move) {
        BoardConfig b = board.copy();
        int src_index = move.y_src * 8 + move.x_src;
        int dest_index = move.y_dest * 8 + move.x_dest;
        char fig = board.board[src_index];
        if (!Figure.get_already_moved(fig)) {
            fig += 1;
        }
        // adjust new board and if needed king indices
        b.board[src_index] = 0;
        b.board[dest_index] = fig;
        if (Figure.get_type(fig) == Figure.Type.KING) {
            b.king_indices[Figure.get_color(fig).ordinal()] = dest_index;
        }
        b.last_moved[0] = src_index;
        b.last_moved[1] = dest_index;
        return b;
    }
}
