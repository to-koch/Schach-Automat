/**
 * entry point into program, initializes all required components and starts game loop
 */

package Main;

import GameLogic.BoardState;
import Opponent.OpponentControler;
import Visuals.Frame;
import Visuals.Sprites;
import Visuals.VisualsController;

public class Main {

    public static void main(String[] args) {
        // initialize visuals
        Sprites.load();
        BoardState state = new BoardState();
        state.init();
        new Frame(state);

        // initialize game loop
        OpponentControler opponent1 = new OpponentControler(4, 'w');
        OpponentControler opponent2 = new OpponentControler(4, 'b');
        while (true) {
            state = opponent1.next_move(state);
            VisualsController.drawer.update_board(state);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            state = opponent2.next_move(state);
            VisualsController.drawer.update_board(state);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
