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

public class Turnstile {
    private double currentXPercent;
    private double currentYPercent;

    private double radiusPercent;

    private ArrayList< Exit > exits;

    public Turnstile(
        double currentXPercent, double currentYPercent, double radiusPercent,
        ArrayList< Exit > exits
    ) {
        this.currentXPercent = currentXPercent;
        this.currentYPercent = currentYPercent;
        this.radiusPercent = radiusPercent;
        this.exits = exits;
    }

    public double getCurrentXFor( Canvas canvas ) {
        return currentXPercent * canvas.getWidth();
    }

    public double getCurrentYFor( Canvas canvas ) {
        return currentYPercent * canvas.getWidth();
    }

    public double getRadiusFor( Canvas canvas ) {
        return radiusPercent * canvas.getWidth();
    }

    public Exit getRandomExit() {
        Random rand = new Random();

        return exits.get( rand.nextInt( exits.size() ) );
    }
}
