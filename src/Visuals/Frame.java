/**
 * Manages the frame and its content
 */

package Visuals;

import GameLogic.BoardConfig;
import Main.Const;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    /**
     * Label to indicate which player (black or white) is to execute a turn
     */
    private final JLabel turn_label;
    /**
     * Label to draw current board configuration
     */
    private BoardDrawer drawer;

    public Frame(BoardConfig initial_board) {
        super();

        // calculate frame size to be 90% of the screen height
        Dimension full_size = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (0.9 * full_size.height);
        int width = (int) (0.9 * height);
        Dimension s = new Dimension(width, height);

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
        int board_offset = content_size.height - content_size.width;

        // Create gui components
        JLayeredPane layout = new JLayeredPane();
        layout.setSize(content_size);
        layout.setLocation(0, 0);
        this.add(layout);
        turn_label = new JLabel("", SwingConstants.CENTER);
        turn_label.setFont(Const.FONT);
        turn_label.setOpaque(true);
        turn_label.setSize(content_size.width, board_offset);
        turn_label.setLocation(0, 0);
        turn_label.setVisible(true);
        layout.add(turn_label, JLayeredPane.PALETTE_LAYER);
        drawer = new BoardDrawer(initial_board, board_size);
        drawer.setLocation(0, board_offset);
        layout.add(drawer);

        layout.setVisible(true);

        // initialize visuals controller
        VisualsController.init(drawer);
    }

}
