/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.threads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.SurfaceHolder;

import androidx.core.content.ContextCompat;

import edu.fullsail.whackapede.R;

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

            // TODO: Update game state and draw to canvas.
            Paint paintBitmap = new Paint();
            Paint paintHole = new Paint();
            Paint paintAbove = new Paint();
            Paint paintBelow = new Paint();

            float radiusHole = (float) canvas.getWidth() / 7 / 2;
            float radiusSegment = radiusHole * 0.75f;

            int colorGrass = ContextCompat.getColor( context, R.color.grassGreen );
            int colorEarth = ContextCompat.getColor( context, R.color.earthBrown );
            int colorAbove = ContextCompat.getColor( context, R.color.dayBlue );
            int colorBelow = ContextCompat.getColor( context, R.color.nightBlue );

            Bitmap bitmapGrass = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
            Canvas canvasGrass = new Canvas( bitmapGrass );

            Bitmap bitmapAbove = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
            Canvas canvasAbove = new Canvas( bitmapAbove );

            Bitmap bitmapBelow = Bitmap.createBitmap( canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888 );
            Canvas canvasBelow = new Canvas( bitmapBelow );

            canvasGrass.drawColor( colorGrass );

            paintHole.setAntiAlias( true );
            paintHole.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.CLEAR ) );

            canvasGrass.drawCircle( (float) canvas.getWidth() / 7 + radiusHole, (float) canvas.getHeight() / 11 + radiusHole, radiusHole, paintHole );

            paintAbove.setAntiAlias( true );
            paintAbove.setColor( colorAbove );

            canvasAbove.drawCircle( (float) canvas.getWidth() / 7 * 2 + radiusHole, (float) canvas.getHeight() / 11 * 2 + radiusHole, radiusSegment, paintAbove );

            paintBelow.setAntiAlias( true );
            paintBelow.setColor( colorBelow );

            canvasBelow.drawCircle( (float) canvas.getWidth() / 7 * 1.25F + radiusHole, (float) canvas.getHeight() / 11 * 1.25F + radiusHole, radiusSegment, paintBelow );

            canvas.drawColor( colorEarth );
            canvas.drawBitmap( bitmapBelow, 0, 0, paintBitmap );
            canvas.drawBitmap( bitmapGrass, 0, 0, paintBitmap );
            canvas.drawBitmap( bitmapAbove, 0, 0, paintBitmap );

            surfaceHolder.unlockCanvasAndPost( canvas );
        }
    }

    public void end() {
        isRunning = false;
    }
}
