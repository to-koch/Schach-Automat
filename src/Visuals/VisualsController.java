/**
 * enables communication between different gui components
 */

package Visuals;

public abstract class VisualsController {

    /*
     * the currently by the user selected tile
     */
    public static int selected_tile = -1;
    /*
     * reference to the board drawer
     */
    public static BoardDrawer drawer;

    /*
     * sends a signal to the board_drawer to update
     */
    public static void update_board() {
        drawer.repaint();
    }

    /*
     * initializes all required references
     */
    public static void init(BoardDrawer drawer) {
        VisualsController.drawer = drawer;
    }

}
