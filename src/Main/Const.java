/**
 * Abstract class that contains all constants.
 */

package Main;

import java.awt.*;
import java.util.concurrent.CyclicBarrier;

public class Const {

    /**
     * Path to resource folder
     */
    public static final String RSC_PATH = ".\\rsc\\";
    /**
     * name of the settings file
     */
    public static final String SETTINGS_FILE = "settings.txt";
    /**
     * Font of displayed text
     */
    public static final Font FONT = new Font(null, Font.BOLD, 20);
    /**
     * barrier to synchronize game loop
     */
    public static final CyclicBarrier BARRIER = new CyclicBarrier(2);

}
