/**
 * Draws board and figures
 */

package Visuals;

import GameLogic.BoardState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardDrawer extends JLabel {

    /*
     * the current board state to be drawn
     */
    private BoardState state;
    /*
     * size of the label
     */
    private final Dimension size;
    /*
     * the offset of the board relative to the board drawer label
     */
    private final int offset;
    /*
     * the size of a single tile
     */
    private final int square_size;

    public BoardDrawer(BoardState state, Dimension size) {
        super();
        this.state = state;
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
                if (state.last_moved[0] == index_in_array || state.last_moved[1] == index_in_array) {
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
                BufferedImage img;
                int color_idx;
                char code = state.board[index_in_array];
                if (code >= 97 && code <= 122) {
                    // draw white figure
                    color_idx = 0;
                    } else if (code >= 65 && code <= 90) {
                    // draw black figure
                    color_idx = 1;
                } else {
                    // illegal figure code
                    continue;
                }
                code = Character.toUpperCase(code);
                switch(code) {
                    case 'B': // bishop
                        img = Sprites.sprites[color_idx][0];
                        break;
                    case 'K': // king
                        img = Sprites.sprites[color_idx][1];
                        break;
                    case 'N': // knight
                        img = Sprites.sprites[color_idx][2];
                        break;
                    case 'P': // pawn
                        img = Sprites.sprites[color_idx][3];
                        break;
                    case 'Q': // queen
                        img = Sprites.sprites[color_idx][4];
                        break;
                    case 'R': // rook
                        img = Sprites.sprites[color_idx][5];
                        break;
                    default:
                        continue;
                }
                g.drawImage(img, offset + x * square_size, offset + y * square_size, square_size, square_size, null);
            }
            toggle *= -1;
        }
    }
}
