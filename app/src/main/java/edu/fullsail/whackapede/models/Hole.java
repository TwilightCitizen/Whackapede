/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.graphics.Canvas;

public class Hole {
    private float currentXPercent;
    private float currentYPercent;

    private float radiusPercent;

    public Hole( float currentXPercent, float currentYPercent, float radiusPercent ) {
        this.currentXPercent = currentXPercent;
        this.currentYPercent = currentYPercent;
        this.radiusPercent = radiusPercent;
    }

    public float getCurrentXFor( Canvas canvas ) {
        return currentXPercent * canvas.getWidth();
    }

    public float getCurrentYFor( Canvas canvas ) {
        return currentYPercent * canvas.getWidth();
    }

    public float getRadiusFor( Canvas canvas ) {
        return radiusPercent * canvas.getWidth();
    }
}
