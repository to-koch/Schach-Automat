/**
 * hardcoded openings
 */

package Opponent;

import GameLogic.Figure;
import GameLogic.Move;
import Main.Settings;

import java.util.Random;

public class Openings {

    /**
     * array of arrays of moves, each sub-array represents an opening
     */
    private static Move[][] openings_white = {
            {new Move(4,6,4,4),new Move(1,7,2,5),new Move(6,7,5,5)},
            {new Move(4,6,4,4),new Move(1,7,2,5),new Move(3,6,3,4)},
            {new Move(3,6,3,4),new Move(2,6,2,5),new Move(1,6,1,4)}
    };
    private static Move[][] openings_black = {
            {new Move(4,1,4,3),new Move(1,0,2,2),new Move(6,0,5,2)},
            {new Move(4,1,4,3),new Move(1,0,2,2),new Move(3,1,3,3)},
            {new Move(3,1,3,3),new Move(2,1,2,2),new Move(1,1,1,3)}
    };
    /**
     * during initialization of the program, one opening gets randomly selected
     */
    private static int selected_opening = new Random().nextInt(openings_white.length);
    /**
     * count to track which move of the selected opening is the next to return
     */
    private static int position_count = 0;


    /**
     * get next move of the randomly selected opening
     * @return a move
     */
    public static Move get_next() {
        Move m;
        if (Settings.PLAYER_COLOR == Figure.Color.WHITE) {
            m = openings_black[selected_opening][position_count];
        } else {
            m = openings_white[selected_opening][position_count];
        }
        position_count++;
        return m;
    }
}
