/**
 * shows a final message
 */

package Visuals;

import GameLogic.Figure;
import Main.Settings;

import javax.swing.*;

public abstract class WinnerMsg {

    public static void show(Figure.Color winner) {
        boolean human_win = (winner == Settings.PLAYER_COLOR);
        if (human_win) {
            String msg = "Color " + winner.toString().toLowerCase() + " (you) has won!";
            JOptionPane.showMessageDialog(null, msg, "You have won!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String msg = "Color " + winner.toString().toLowerCase() + " (ai) has won!";
            JOptionPane.showMessageDialog(null, msg, "You have lost!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
