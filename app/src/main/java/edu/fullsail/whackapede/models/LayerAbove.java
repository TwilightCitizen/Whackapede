/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;


import edu.fullsail.whackapede.R;
import edu.fullsail.whackapede.interfaces.CanvasDrawable;

public class LayerAbove implements CanvasDrawable {
    private Canvas canvas;
    private Bitmap bitmapAbove;
    private Paint paintAbove = new Paint();

    public LayerAbove( Context context, Canvas canvas, Game game ) {
        this.canvas = canvas;

        int colorAbove = ContextCompat.getColor( context, R.color.dayBlue );
        bitmapAbove = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvasAbove = new Canvas( bitmapAbove );

        Paint paintSegment = new Paint();

        paintSegment.setAntiAlias( true );
        paintSegment.setColor( colorAbove );

        for( SegmentNew centipede : game.getCentipedes() ) {
            SegmentNew segmentNew = centipede;

            while( segmentNew != null ) {
                if( segmentNew.getIsAbove() ) canvasAbove.drawCircle(
                    segmentNew.getCurrentXFor( canvas ), segmentNew.getCurrentYFor( canvas ),
                    segmentNew.getRadiusFor(  canvas ), paintSegment
                );

                segmentNew = segmentNew.getTail();
            }
        }
    }

    @Override public void drawToCanvas() {
        canvas.drawBitmap( bitmapAbove, 0, 0, paintAbove );
    }
}
