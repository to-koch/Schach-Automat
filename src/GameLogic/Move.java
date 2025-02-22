/**
 * Container class for a move
 */

package GameLogic;

public class Move {

    /**
     * coordinates on the board of source and destination tile
     */
    public int x_dest, y_dest, x_src, y_src;

    public Move(int x_src, int y_src, int x_dest, int y_dest) {
        this.x_dest = x_dest;
        this.y_dest = y_dest;
        this.x_src = x_src;
        this.y_src = y_src;
    }
}
