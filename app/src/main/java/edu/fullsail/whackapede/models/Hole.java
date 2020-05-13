/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.graphics.Canvas;

class Hole {
    private final double currentXPercent;
    private final double currentYPercent;

    private final double radiusPercent;

    Hole( double currentXPercent, double currentYPercent, double radiusPercent ) {
        this.currentXPercent = currentXPercent;
        this.currentYPercent = currentYPercent;
        this.radiusPercent = radiusPercent;
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
}
