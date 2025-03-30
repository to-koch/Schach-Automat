/**
 * Loads and stores all spites.
 */

package Visuals;

import Main.Const;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Sprites {

    /**
     * 2d array to store the black and white sprite of every figure
     */
    public final static BufferedImage[][] sprites = new BufferedImage[2][6];

    /**
     * loads sprite files from resource folder
     */
    public static void load() {
        try {
            sprites[0][0] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\white\\bishop.png"));
            sprites[0][1] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\white\\king.png"));
            sprites[0][2] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\white\\knight.png"));
            sprites[0][3] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\white\\pawn.png"));
            sprites[0][4] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\white\\queen.png"));
            sprites[0][5] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\white\\rook.png"));

            sprites[1][0] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\black\\bishop.png"));
            sprites[1][1] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\black\\king.png"));
            sprites[1][2] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\black\\knight.png"));
            sprites[1][3] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\black\\pawn.png"));
            sprites[1][4] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\black\\queen.png"));
            sprites[1][5] = ImageIO.read(new File(Const.RSC_PATH + "sprites\\black\\rook.png"));
        } catch (IOException e) {
            System.err.println("Unable to load sprites");
            System.exit(1);
        }

    }
}
