/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

import edu.fullsail.whackapede.interfaces.CanvasDrawable;

public class GameArena implements CanvasDrawable {
    private ArrayList< CanvasDrawable > canvasDrawables = new ArrayList<>();

    public GameArena( Context context, Canvas canvas, Game game ) {
        canvasDrawables.add( new LayerEarth( context, canvas ) );
        canvasDrawables.add( new LayerBelow( context, canvas, game ) );
        canvasDrawables.add( new LayerGrass( context, canvas ) );
        canvasDrawables.add( new LayerAbove( context, canvas, game ) );
    }

    @Override public void drawToCanvas() {
        for( CanvasDrawable canvasDrawable : canvasDrawables ) {
            canvasDrawable.drawToCanvas();
        }
    }
}
