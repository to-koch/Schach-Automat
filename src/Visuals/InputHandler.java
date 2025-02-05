/**
 * Handles user input
 */

package Visuals;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements MouseListener {

    /**
     * the board drawers size
     */
    private final Dimension board_size;
    /**
     * the offset of the board relative to the board drawer label
     */
    private final int offset;
    /**
     * the size of a single tile
     */
    private final int square_size;

    public InputHandler(Dimension board_size, int offset, int square_size) {
        this.board_size = board_size;
        this.offset = offset;
        this.square_size = square_size;
    }

    /**
     * evaluate which tile has been clicked
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() - offset;
        int y = e.getY() - offset;
        int w = board_size.width - 2 * offset;
        int h = board_size.height - 2 * offset;
        if (x < 0 || x > w || y < 0 || y > h) {
            return;
        }
        int tile_x = x / square_size;
        int tile_y = y / square_size;
        VisualsController.selected_tile = tile_y * 8 + tile_x;
        VisualsController.update_board();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
