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

class Turnstile {
    private final double currentXPercent;
    private final double currentYPercent;

    private final double radiusPercent;

    private final ArrayList< Exit > exits;

    Turnstile(
        double currentXPercent, double currentYPercent, double radiusPercent,
        ArrayList< Exit > exits
    ) {
        this.currentXPercent = currentXPercent;
        this.currentYPercent = currentYPercent;
        this.radiusPercent = radiusPercent;
        this.exits = exits;
    }

    double getCurrentXFor( Canvas canvas ) {
        return currentXPercent * canvas.getWidth();
    }

    double getCurrentYFor( Canvas canvas ) {
        return currentYPercent * canvas.getWidth();
    }

    double getRadiusFor( Canvas canvas ) {
        return radiusPercent * canvas.getWidth();
    }

    double getCurrentXPercent() {
        return currentXPercent;
    }

    double getCurrentYPercent() {
        return currentYPercent;
    }

    Exit getRandomExit() {
        Random rand = new Random();

        return exits.get( rand.nextInt( exits.size() ) );
    }

    ArrayList< Exit > getExits() {
        return exits;
    }
}
