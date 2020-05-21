/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.twilightcitizen.whackapede.gameElements.Direction;

/*
Exit maintains directions of motion on the X and Y axes.  When applied to the position and speed of
other elements on these axes, those elements' velocity can be determined.  Nominally, the directions
can only be 1, -1, or 0, representing positive, negative, or stationary direction along the axes.

Exits cannot be instantiated externally, but must be obtained from any of the static instances already
provided.  These are intended to be used by Turns, to be applied to Segments traversing them, to change
their direction of motion.
*/
class Exit {
    // Directions along the X and Y axes.
    private final Direction direction;

    // Static Exit instances for exiting a turn from the Top, Bottom, Left, or Right.
    static final Exit exitTop    = new Exit( Direction.up );
    static final Exit exitBottom = new Exit( Direction.down );
    static final Exit exitLeft   = new Exit( Direction.left );
    static final Exit exitRight  = new Exit( Direction.right );

    // Four way exits for Turns situated not along the edges of the game arena.
    static final ArrayList< Exit > fourWayExit = new ArrayList< Exit >() { {
        add( exitTop ); add( exitBottom ); add( exitLeft); add( exitRight );
    } };

    /*
    Three way exits for Turns situated along the Top, Bottom, Left, and Right edges of
    the game arena, excluding the corners.
    */

    static final ArrayList< Exit > threeWayExitTop = new ArrayList< Exit >() { {
        add( exitBottom ); add( exitLeft); add( exitRight );
    } };

    static final ArrayList< Exit > threeWayExitBottom = new ArrayList< Exit >() { {
        add( exitTop ); add( exitLeft); add( exitRight );
    } };

    static final ArrayList< Exit > threeWayExitLeft = new ArrayList< Exit >() { {
        add( exitTop ); add( exitBottom ); add( exitRight );
    } };

    static final ArrayList< Exit > threeWayExitRight = new ArrayList< Exit >() { {
        add( exitTop ); add( exitBottom ); add( exitLeft);
    } };

    /*
    Two way exits for Turns situated at the Top Left, Top Right, Bottom Left, and
    Bottom Right corners of the game arena.
    */

    static final ArrayList< Exit > twoWayExitTopLeft = new ArrayList< Exit >() { {
        add( exitBottom ); add( exitRight );
    } };

    static final ArrayList< Exit > twoWayExitTopRight = new ArrayList< Exit >() { {
        add( exitBottom ); add( exitLeft );
    } };

    static final ArrayList< Exit > twoWayExitBottomLeft = new ArrayList< Exit >() { {
        add( exitTop ); add( exitRight );
    } };

    static final ArrayList< Exit > twoWayExitBottomRight = new ArrayList< Exit >() { {
        add( exitTop ); add( exitLeft );
    } };

    // Exits cannot be instantiated externally.
    private Exit( Direction direction ) { this.direction = direction; }

    // Determine if two Exits are equal, having equal X and Y directions.
    @Override public boolean equals( @Nullable Object obj ) {
        if( obj == this ) return true;

        if( !( obj instanceof Exit ) ) return false;

        Exit exit = (Exit)  obj;

        return exit.getDirection().getX() == direction.getX() &&
               exit.getDirection().getY() == direction.getY();
    }

    // Directions are read-only.  Top, Bottom, Left, and Right always point the same way from origin.
    Direction getDirection() { return direction; }
    // Obtain an Exit that is the exact opposite of the provided directions.
    static Exit getExitReverseOf( Direction direction ) {
        return new Exit( new Direction( 0 - direction.getX(), 0 - direction.getY() ) );
    }
}