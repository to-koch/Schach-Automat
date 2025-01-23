/**
 * Data structure for a move
 */

package GameLogic;

public class Move {

    /*
     * x,y coordinates of source and destination tile involved in a move
     */
    public int src_x, src_y, dest_x, dets_y;

    public Move(int src_x, int src_y, int dest_x, int dets_y) {
        this.src_x = src_x;
        this.src_y = src_y;
        this.dest_x = dest_x;
        this.dets_y = dets_y;
    }

}
