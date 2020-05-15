/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;

class Exit {
    private final float directionX;
    private final float directionY;

    static final Exit exitTop    = new Exit(  0, -1 );
    static final Exit exitBottom = new Exit(  0,  1 );
    static final Exit exitLeft   = new Exit( -1,  0 );
    static final Exit exitRight  = new Exit(  1,  0 );

    static final ArrayList< Exit > fourWayExit = new ArrayList< Exit >() { {
        add( exitTop ); add( exitBottom ); add( exitLeft); add( exitRight );
    } };

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

    private Exit( float directionX, float directionY ) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    @Override public boolean equals( @Nullable Object obj ) {
        if( obj == this ) return true;

        if( !( obj instanceof Exit ) ) return false;

        Exit exit = (Exit)  obj;

        return Float.compare( exit.getDirectionX(), directionX ) == 0 &&
               Float.compare( exit.getDirectionY(), directionY ) == 0;
    }

    float getDirectionX() {
        return directionX;
    }

    float getDirectionY() {
        return directionY;
    }

    static Exit getExitReverseOf( float directionX, float directionY ) {
        return new Exit( 0 - directionX, 0 - directionY);
    }
}