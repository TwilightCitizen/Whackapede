/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.gameElements;

public class Position {
    // Components along the X and Y axes.
    private final int x;
    private final int y;

    public Position( int x, int y ) { this.x = x; this.y = y; }

    // Components along the X and Y axes are read-only.
    public int getX() { return x; }
    public int getY() { return y; }
}
