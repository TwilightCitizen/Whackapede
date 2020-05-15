/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

class Turn {
    private final int positionX;
    private final int positionY;

    private final ArrayList< Exit > exits;

    Turn( int positionXPercent, int currentYPercent, ArrayList< Exit > exits ) {
        this.positionX = positionXPercent;
        this.positionY = currentYPercent;
        this.exits = exits;
    }

    int getPositionX() { return positionX; }

    int getPositionY() { return positionY; }

    Exit getRandomExit( Segment segment ) {
        Random rand = new Random();
        Exit excludedExit = Exit.getExitReverseOf( segment.getDirectionX(), segment.getDirectionY() );
        ArrayList< Exit > includedExits = new ArrayList<>( exits );

        includedExits.remove( excludedExit );

        return includedExits.get( rand.nextInt( includedExits.size() ) );
    }

    ArrayList< Exit > getExits() {
        return exits;
    }
}
