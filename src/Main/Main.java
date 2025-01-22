/**
 * entry point into program, initializes all required components and starts game loop
 */

package Main;

import GameLogic.BoardState;
import Visuals.Frame;
import Visuals.Sprites;

public class Main {

    public static void main(String[] args) {
        Sprites.load();
        BoardState state = new BoardState();
        state.init();
        new Frame(state);
    }

}
