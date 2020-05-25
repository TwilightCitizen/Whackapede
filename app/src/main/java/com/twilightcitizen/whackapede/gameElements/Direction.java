/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.gameElements;

public class Direction {
    // Components along the X and Y axes.
    private final float x;
    private final float y;

    // Static Exit instances for exiting a turn from the Top, Bottom, Left, or Right.
    public static final Direction up    = new Direction(  0, -1 );
    public static final Direction down  = new Direction(  0,  1 );
    public static final Direction left  = new Direction( -1,  0 );
    public static final Direction right = new Direction(  1,  0 );

    public Direction( float x, float y ) { this.x = x; this.y = y; }

    // Components along the X and Y axes are read-only.
    public float getX() { return x; }
    public float getY() { return y; }
}
