/**
 * entry point into program, initializes all required components and starts game loop
 */

package Main;

import GameLogic.BoardConfig;
import Visuals.Frame;
import Visuals.Sprites;

public class Main {

    public static void main(String[] args) {
        // initialize visuals
        Sprites.load();
        BoardConfig board = new BoardConfig();
        board.init();
        new Frame(board);


    }

}
