/**
 * coordinates the computer-controlled opponent
 */

package Opponent;

public class OpponentControler {

    /*
     * how many turns in advance to calculate
     */
    private final int rec_depth;
    /*
     * which color is controlled by the opponent; 'w'=white, 'b'=black
     */
    private final char color;

    public OpponentControler(int rec_depth, char color) {
        this.rec_depth = rec_depth;
        if (color == 'w' || color == 'b') {
            this.color = color;
        } else {
            this.color = 'w';
        }
    }
}
