/**
 * hardcoded openings
 */

package Opponent;

import GameLogic.Move;

import java.util.Random;

public class Openings {

    /**
     * array of arrays of moves, each sub-array represents an opening
     */
    private static Move[][] openings = {
            {},
            {}
    };
    /**
     * during initialization of the program, one opening gets randomly selected
     */
    private static int selected_opening = new Random().nextInt(openings.length);
    /**
     * count to track which move of the selected opening is the next to return
     */
    private static int postion_count = -1;


    /**
     * get next move of the randomly selected opening
     * @return a move
     */
    public static Move get_next() {
        postion_count++;
        return openings[selected_opening][postion_count];
    }
}
