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
    private final float positionXPercent;
    private final float positionYPercent;

    private final float radiusPercent;

    private final ArrayList< Exit > exits;

    Turn(
        float positionXPercent, float currentYPercent, float radiusPercent,
        ArrayList< Exit > exits
    ) {
        this.positionXPercent = positionXPercent;
        this.positionYPercent = currentYPercent;
        this.radiusPercent = radiusPercent;
        this.exits = exits;
    }

    float getCurrentXFor( Canvas canvas ) {
        return positionXPercent * canvas.getWidth();
    }

    float getCurrentYFor( Canvas canvas ) {
        return positionYPercent * canvas.getWidth();
    }

    float getRadiusFor( Canvas canvas ) {
        return radiusPercent * canvas.getWidth();
    }

    float getPositionXPercent() {
        return positionXPercent;
    }

    float getPositionYPercent() {
        return positionYPercent;
    }

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
