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
        Canvas canvasBelow = new Canvas( bitmapBelow );

        Paint paintSegment = new Paint();

        paintSegment.setAntiAlias( true );
        paintSegment.setColor( colorBelow );

        for( Segment centipede : game.getCentipedes() ) {
            Segment segment = centipede;

            while( segment != null ) {
                if( segment.getIsBelow() ) canvasBelow.drawCircle(
                    segment.getCurrentX(), segment.getCurrentY(),
                    segment.getRadius(), paintSegment
                );

                segment = segment.getTail();
            }
        }
    }

    @Override public void drawToCanvas() {
        canvas.drawBitmap( bitmapBelow, 0, 0, paintBelow );
    }
}
