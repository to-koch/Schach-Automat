/**
 * Manages the frame and its content
 */

package Visuals;

import GameLogic.BoardConfig;
import GameLogic.Figure;
import Main.Const;
import Main.Settings;

import javax.print.attribute.standard.PrinterLocation;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    /**
     * Label to draw current board configuration
     */
    private BoardDrawer drawer;

    public Frame(BoardConfig initial_board) {
        super();

        // calculate frame size to be 90% of the screen height
        Dimension full_size = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (0.9 * full_size.height);
        Dimension s = new Dimension(height, height);

        // initialize frame and get actual canvas size
        this.setTitle("Schach Automat");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(s);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        Dimension content_size = this.getContentPane().getSize();

        // calculate the layout: square board
        Dimension board_size = new Dimension(content_size.width, content_size.width);

        // Create gui components
        JLayeredPane layout = new JLayeredPane();
        layout.setSize(content_size);
        layout.setLocation(0, 0);
        this.add(layout);
        drawer = new BoardDrawer(initial_board, board_size);
        drawer.setLocation(0, 0);
        layout.add(drawer, JLayeredPane.PALETTE_LAYER);

        layout.setVisible(true);

        // initialize visuals controller
        VisualsController.init(drawer);
    }
}
