/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.threads;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;

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
            Drawable drawableEarth = context.getResources().getDrawable( R.drawable.game_layer_earth, null );

            drawableEarth.setBounds( 0, 0, canvas.getWidth(), canvas.getHeight() );
            drawableEarth.draw( canvas );
            surfaceHolder.unlockCanvasAndPost( canvas );
        }
    }

    public void end() {
        isRunning = false;
    }
}
