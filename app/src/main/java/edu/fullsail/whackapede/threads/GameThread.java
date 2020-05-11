/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.threads;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import edu.fullsail.whackapede.models.GameArena;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private Context context;
    private boolean isRunning;

    public GameThread( SurfaceHolder surfaceHolder, Context context ) {
        isRunning = true;
        this.surfaceHolder = surfaceHolder;
        this.context = context;
    }

    public void run() {
        while( isRunning ) {
            Canvas canvas = surfaceHolder.lockCanvas();
            GameArena gameArena = new GameArena( context, canvas );
            /*
            // TODO: Update game state and draw to canvas.
            Paint paintAbove = new Paint();
            Paint paintBelow = new Paint();

            float radiusSegment = radiusHole * 0.75f;

            int colorAbove = ContextCompat.getColor( context, R.color.dayBlue );
            int colorBelow = ContextCompat.getColor( context, R.color.nightBlue );

            Bitmap bitmapAbove = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
            Canvas canvasAbove = new Canvas( bitmapAbove );

            Bitmap bitmapBelow = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
            Canvas canvasBelow = new Canvas( bitmapBelow );

            paintAbove.setAntiAlias( true );
            paintAbove.setColor( colorAbove );

            canvasAbove.drawCircle( (float) canvas.getWidth() / 7 * 2 + radiusHole, (float) canvas.getHeight() / 11 * 2 + radiusHole, radiusSegment, paintAbove );

            paintBelow.setAntiAlias( true );
            paintBelow.setColor( colorBelow );

            canvasBelow.drawCircle( (float) canvas.getWidth() / 7 * 1.25F + radiusHole, (float) canvas.getHeight() / 11 * 1.25F + radiusHole, radiusSegment, paintBelow );

            canvas.drawBitmap( bitmapBelow, 0, 0, paintBitmap );
            canvas.drawBitmap( bitmapAbove, 0, 0, paintBitmap );
            */

            gameArena.drawToCanvas();
            surfaceHolder.unlockCanvasAndPost( canvas );
        }
    }

    public void end() {
        isRunning = false;
    }
}
