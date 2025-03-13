/**
 * enables communication between different gui components
 */

package Visuals;

import GameLogic.BoardConfig;

public abstract class VisualsController {

    /**
     * the currently by the user selected tile
     */
    public static int selected_tile = -1;
    /**
     * reference to the board drawer
     */
    public static BoardDrawer drawer;

    /**
     * sends a signal to the board drawer to update
     */
    public static void update_board() {
        drawer.repaint();
    }

    /**
     * sends a signal to the board drawer to update using the new board configuration
     * @param config the new board configuration to use
     */
    public static void update_board(BoardConfig config) {
        drawer.update_board(config);
    }

    /**
     * initializes all required references
     */
    public static void init(BoardDrawer drawer) {
        VisualsController.drawer = drawer;
    }
}
