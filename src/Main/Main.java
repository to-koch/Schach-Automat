/**
 * entry point into program, initializes all required components and starts game loop
 */

package Main;

import GameLogic.*;
import Opponent.Opponent;
import Visuals.*;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

public class Main {

    public static void main(String[] args) {
        // initialize visuals
        Sprites.load();
        BoardConfig board = new BoardConfig();
        board.init();
        new Frame(board);
        game_loop(board);
    }

    /**
     * handles game loop
     * @param board the initial board configuration
     */
    private static void game_loop(BoardConfig board) {
        while (true) {
            if (Figure.Color.WHITE == Settings.PLAYER_COLOR) {
                // white player move
                Move player_move = await_player_move(board);
                while (player_move == null) {
                    VisualsController.selected_tile = -1;
                    VisualsController.update_board(board);
                    player_move = await_player_move(board);
                }
                VisualsController.selected_tile = -1;
                board = MoveTools.exec_move(board, player_move);
                VisualsController.update_board(board);
                if (EvalTools.checkmate(board, Figure.Color.BLACK)) {
                    WinnerMsg.show(Figure.Color.WHITE);
                    System.exit(0);
                }
                // black computer move
                Move cmp_move = Opponent.get_next(board);
                if (cmp_move == null) {
                    WinnerMsg.show(Figure.Color.WHITE);
                    System.exit(0);
                }
                board = MoveTools.exec_move(board, cmp_move);
                VisualsController.update_board(board);
                if (EvalTools.checkmate(board, Figure.Color.WHITE)) {
                    WinnerMsg.show(Figure.Color.BLACK);
                    System.exit(0);
                }
            } else {
                // white computer move
                Move cmp_move = Opponent.get_next(board);
                if (cmp_move == null) {
                    WinnerMsg.show(Figure.Color.BLACK);
                    System.exit(0);
                }
                board = MoveTools.exec_move(board, cmp_move);
                VisualsController.update_board(board);
                if (EvalTools.checkmate(board, Figure.Color.BLACK)) {
                    WinnerMsg.show(Figure.Color.WHITE);
                    System.exit(0);
                }
                // black player move
                Move player_move = await_player_move(board);
                while (player_move == null) {
                    VisualsController.selected_tile = -1;
                    VisualsController.update_board(board);
                    player_move = await_player_move(board);
                }
                VisualsController.selected_tile = -1;
                board = MoveTools.exec_move(board, player_move);
                VisualsController.update_board(board);
                if (EvalTools.checkmate(board, Figure.Color.WHITE)) {
                    WinnerMsg.show(Figure.Color.BLACK);
                    System.exit(0);
                }
            }
        }
    }

    /**
     * reads the next player's move, synchronized with input handel via barrier, and shows an error message if an illegal move was entered
     * @param board the current board configuration
     * @return the player's move or @null in case of illegal input
     */
    private static Move await_player_move(BoardConfig board) {
        try {
            Const.BARRIER.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int i = VisualsController.selected_tile;
        int x_src = i % 8;
        int y_src = i / 8;
        ArrayList<Move> moves = MoveTools.get_moves(board, x_src, y_src, Settings.PLAYER_COLOR);
        if (moves == null) {
            ErrorMsg.show("Please select a " + Settings.PLAYER_COLOR.toString().toLowerCase() + " figure");
            return null;
        }
        for (Move m : moves) {
            VisualsController.drawer.highlighted[m.y_dest * 8 + m.x_dest] = true;
        }
        VisualsController.update_board();

        try {
            Const.BARRIER.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        i = VisualsController.selected_tile;
        int x_dest = i % 8;
        int y_dest = i / 8;
        for (Move m : moves) {
            if (m.x_dest == x_dest && m.y_dest == y_dest) {
                Move move = new Move(x_src, y_src, x_dest, y_dest);
                if (EvalTools.is_checked(MoveTools.exec_move(board, move), Settings.PLAYER_COLOR)) {
                    ErrorMsg.show("Your king must not be in check");
                    return null;
                }
                return move;
            }
        }
        ErrorMsg.show("Please select a valid move");
        return null;
    }
}
