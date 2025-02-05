/**
 * Draws board and figures
 */

package Visuals;

import GameLogic.BoardConfig;
import GameLogic.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardDrawer extends JLabel {

    /**
     * the current board configuration to be drawn
     */
    private BoardConfig board_config;
    /**
     * size of the label
     */
    private final Dimension size;
    /**
     * the offset of the board relative to the board drawer label
     */
    private final int offset;
    /**
     * the size of a single tile
     */
    private final int square_size;

    public BoardDrawer(BoardConfig board_config, Dimension size) {
        super();
        this.board_config = board_config;
        this.size = size;
        this.setSize(size);
        this.setVisible(true);

        // calculate tile size and offsets
        offset = (int) (0.05 * size.width);
        square_size = (size.width - 2 * offset) / 8;

        this.addMouseListener(new InputHandler(size, offset, square_size));
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        // draw background
        g.setColor(new Color(207, 185, 151));
        g.fillRect(0, 0, size.width, size.height);

        // iterate over board and draw tiles, figures and highlighted tile
        int toggle = 1;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                // Calculate tile position in board array
                int index_in_array = y * 8 + x;
                if (board_config.last_moved[0] == index_in_array || board_config.last_moved[1] == index_in_array) {
                    // highlight tiles involved in last move
                    g.setColor(new Color(255, 127, 127));
                }
                else if (toggle == 1) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.GRAY);
                }
                toggle *= -1;
                // draw tile
                g.fillRect(offset + square_size * x, offset + square_size * y, square_size, square_size);
                g.setColor(Color.BLACK);
                g.drawRect(offset + square_size * x, offset + square_size * y, square_size, square_size);

                // draw rectangle if tile is highlighted
                if (index_in_array == VisualsController.selected_tile) {
                    g.setColor(Color.RED);
                    g.setStroke(new BasicStroke(3));
                    g.drawRect(offset +  x * square_size + (int) (square_size * 0.05), offset + y * square_size + (int) (square_size * 0.05), (int ) (square_size * 0.9), (int) (square_size * 0.9));
                    g.setStroke(new BasicStroke(1));
                }

                // draw figure
                char code = board_config.board[index_in_array];
                Figure.Type type = Figure.get_type(code);
                Figure.Color color = Figure.get_color(code);
                if (type == null || color == null) {
                    continue;
                }
                BufferedImage img = Sprites.sprites[color.ordinal()][type.ordinal()];
                g.drawImage(img, offset + x * square_size, offset + y * square_size, square_size, square_size, null);
            }
            toggle *= -1;
        }
    }

    public void update_board(BoardConfig board_config) {
        this.board_config = board_config;
        repaint();
    }

}
