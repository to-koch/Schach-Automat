/**
 * settings that can be modified by the player via settings.txt
 */

package Main;

import GameLogic.Figure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public abstract class Settings {

    /**
     * the color controlled by the player
     */
    public static Figure.Color PLAYER_COLOR = Figure.Color.BLACK;
    /**
     * the number of threads used for finding a move
     */
    public static int NUM_THREADS = 2;
    /**
     * the depth of the recursion tree to be traversed
     */
    public static int REC_DEPTH = 4;

    /**
     * read settings from settings.txt
     */
    public static void load() {
        try {
            File settings_file = new File(Const.RSC_PATH + Const.SETTINGS_FILE);
            Scanner scanner = new Scanner(settings_file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line == null) {
                    System.err.println("An error occurred while reading settings.txt");
                    System.exit(1);
                }
                line = line.toLowerCase();
                StringTokenizer tok = new StringTokenizer(line, "=");
                String key = tok.nextToken();
                String value = tok.nextToken();
                if (key.compareTo("player_color") == 0) {
                    if (value.compareTo("white") == 0) {
                        PLAYER_COLOR = Figure.Color.WHITE;
                    } else if (value.compareTo("black") == 0) {
                        PLAYER_COLOR = Figure.Color.BLACK;
                    } else {
                        System.err.println("Illegal value for option <" + key + ">: " + value);
                        System.exit(1);
                    }
                } else if (key.compareTo("rec_depth") == 0) {
                    try {
                        REC_DEPTH = Integer.parseInt(value);
                    } catch (Exception e) {
                        System.err.println("Illegal value for option <" + key + "> (Not a number) : " + value);
                        System.exit(1);
                    }
                } else {
                    System.err.println("Unknown option in settings.txt: <" + key + ">");
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.err.println("Unable to read settings.txt");
            System.exit(1);
        }
    }
}
