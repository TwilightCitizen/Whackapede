/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.gameElements;

public class Position {
    // Components along the X and Y axes.
    private final float x;
    private final float y;

    public Position( float x, float y ) { this.x = x; this.y = y; }

    // Components along the X and Y axes are read-only.
    public float getX() { return x; }
    public float getY() { return y; }
}
