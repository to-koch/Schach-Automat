/**
 * entry point into program, initializes all required components and starts game loop
 */

package Main;

import GameLogic.*;
import Visuals.Frame;
import Visuals.Sprites;
import Visuals.VisualsController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // initialize visuals
        Sprites.load();
        BoardConfig board = new BoardConfig();
        board.init();
        new Frame(board);
        test(board);
    }

    public static void test(BoardConfig board) {
        Random r = new Random();
        while (true) {
            int index = r.nextInt(64);
            char code = board.board[index];
            while (code == 0) {
                index = r.nextInt(64);
                code = board.board[index];
            }
            ArrayList<Move> moves = MoveTools.get_moves(board,index%8,index/8, Figure.get_color(code));
            for (Move m : moves) {
                VisualsController.drawer.highlighted[m.y_dest * 8 + m.x_dest] = true;
            }
            if (moves.isEmpty()) {
                continue;
            }
            Move move = moves.get(r.nextInt(moves.size()));


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            board = MoveTools.exec_move(board, move);
            VisualsController.update_board(board);
            if (EvalTools.is_checked(board, Figure.Color.WHITE)) {
                System.out.println("Weiß im Schach");
            }
            if (EvalTools.is_checked(board, Figure.Color.BLACK)) {
                System.out.println("Schwarz im Schach");
            }
            System.out.println("Punkte: " + EvalTools.eval_board(board));
            System.out.println("==================================");
        }
    }

}
