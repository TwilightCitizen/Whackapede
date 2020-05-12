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

public class LayerBelow implements CanvasDrawable {
    private Canvas canvas;
    private Bitmap bitmapBelow;
    private Paint paintBelow = new Paint();

    public LayerBelow( Context context, Canvas canvas, Game game ) {
        this.canvas = canvas;

        int colorBelow = ContextCompat.getColor( context, R.color.nightBlue );
        bitmapBelow = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvasAbove = new Canvas( bitmapBelow );

        Paint paintSegment = new Paint();

        paintSegment.setAntiAlias( true );
        paintSegment.setColor( colorBelow );

        for( SegmentNew centipede : game.getCentipedes() ) {
            SegmentNew segmentNew = centipede;

            while( segmentNew != null ) {
                if( segmentNew.getIsBelow() ) canvasAbove.drawCircle(
                    segmentNew.getCurrentXFor( canvas ), segmentNew.getCurrentYFor( canvas ),
                    segmentNew.getRadiusFor(  canvas ), paintSegment
                );

                segmentNew = segmentNew.getTail();
            }
        }
    }

    @Override public void drawToCanvas() {
        canvas.drawBitmap( bitmapBelow, 0, 0, paintBelow );
    }
}
