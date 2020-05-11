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

        for( Segment centipede : game.getCentipedes() ) {
            Segment segment = centipede;

            while( segment != null ) {
                if( segment.getIsAbove() ) canvasAbove.drawCircle(
                    segment.getCurrentX(), segment.getCurrentY(),
                    segment.getRadius(), paintSegment
                );

                segment = segment.getTail();
            }
        }
    }

    @Override public void drawToCanvas() {
        canvas.drawBitmap( bitmapAbove, 0, 0, paintAbove );
    }
}
