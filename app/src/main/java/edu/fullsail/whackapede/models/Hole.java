/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.graphics.Canvas;

class Hole {
    private final float positionXPercent;
    private final float positionYPercent;

    private final float radiusPercent;

    Hole( float positionXPercent, float positionYPercent, float radiusPercent ) {
        this.positionXPercent = positionXPercent;
        this.positionYPercent = positionYPercent;
        this.radiusPercent = radiusPercent;
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
}
