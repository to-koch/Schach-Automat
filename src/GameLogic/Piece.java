/**
 * contains helper methods connected to individual pieces
 */

package GameLogic;

public abstract class Piece {

    /*
     * returns the value of the given piece
     */
    public static int get_value(char code) {
        switch(code) {
            case 'B': // bishop
            case 'b':
                return 3;
            case 'K': // king
            case 'k':
                return 1000;
            case 'N': // knight
            case 'n':
                return 3;
            case 'P': // pawn
            case 'p':
                return 1;
            case 'Q': // queen
            case 'q':
                return 9;
            case 'R': // rook
            case 'r':
                return 5;
            default:
                return 0;
        }
    }
}
