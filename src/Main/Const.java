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
     * Font of displayed text
     */
    public static final Font FONT = new Font(null, Font.BOLD, 20);
    /**
     * barrier to synchronize game loop
     */
    public static final CyclicBarrier BARRIER = new CyclicBarrier(2);

}
