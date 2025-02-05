/**
 * Extracts figure information from code (for more information on the encoding -> BoardConfig class)
 */

package GameLogic;

public abstract class Figure {

    public enum Type {BISHOP, KING, KNIGHT, PAWN, QUEEN, ROOK}

    public enum Color {WHITE, BLACK}

    /**
     * Extracts the color property of a figure
     * @param code encoded char value of a figure
     * @return the color of the figure or @null if code is not valid
     */
    public static Color get_color(char code) {
        int r = code / 100;
        if (r == 0) {
            return Color.WHITE;
        }
        if (r == 1) {
            return Color.BLACK;
        }
        return null;
    }

    /**
     * Extracts the type property of a figure
     * @param code encoded char value of a figure
     * @return the type of the figure or @null if code is invalid
     */
    public static Type get_type(char code) {
        int r = (code % 100) / 10;
        return switch (r) {
            case 1 -> Type.BISHOP;
            case 2 -> Type.KING;
            case 3 -> Type.KNIGHT;
            case 4 -> Type.PAWN;
            case 5 -> Type.QUEEN;
            case 6 -> Type.ROOK;
            default -> null;
        };
    }

    /**
     * Extracts if the figure has already been moved
     * @param code encoded char value of a figure
     * @return whether a figure has already been moved
     */
    public static boolean get_already_moved(char code) {
        int r = code % 2;
        if (r == 0) {
            return false;
        }
        return true;
    }
}
