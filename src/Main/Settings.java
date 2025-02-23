/**
 * settings that can be modified by the player via settings.txt
 */

package Main;

import GameLogic.Figure;

public abstract class Settings {

    /**
     * the color controlled by the player
     */
    public static Figure.Color PLAYER_COLOR = Figure.Color.WHITE;
    /**
     * the number of threads used for finding a move
     */
    public static int NUM_THREADS = 2;
    /**
     * the depth of the recursion tree to be traversed
     */
    public static int REC_DEPTH = 6;
}
