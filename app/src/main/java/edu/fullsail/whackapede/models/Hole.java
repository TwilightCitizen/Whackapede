/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.graphics.Canvas;

public class Hole {
    private double currentXPercent;
    private double currentYPercent;

    private double radiusPercent;

    public Hole( double currentXPercent, double currentYPercent, double radiusPercent ) {
        this.currentXPercent = currentXPercent;
        this.currentYPercent = currentYPercent;
        this.radiusPercent = radiusPercent;
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
}
