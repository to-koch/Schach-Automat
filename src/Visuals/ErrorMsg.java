/**
 * handles communication with player in case of error
 */

package Visuals;

import javax.swing.*;

public abstract class ErrorMsg {

    /**
     * creates an error message via a dialog field
     * @param msg the error message to print
     */
    public static void show(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error!", JOptionPane.ERROR_MESSAGE);
    }
}
