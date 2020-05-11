/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import edu.fullsail.whackapede.R;
import edu.fullsail.whackapede.interfaces.CanvasDrawable;

public class LayerEarth implements CanvasDrawable {
    private Canvas canvas;

    private int colorEarth;

    public LayerEarth( Context context, Canvas canvas ) {
        this.canvas = canvas;

        colorEarth = ContextCompat.getColor( context, R.color.earthBrown );
    }

    @Override public void drawToCanvas() {
        canvas.drawColor( colorEarth );
    }
}
