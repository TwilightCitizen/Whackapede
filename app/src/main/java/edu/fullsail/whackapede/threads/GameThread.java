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

import edu.fullsail.whackapede.models.Game;

public class GameThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private final Context context;
    private boolean isRunning;
    private final Game game;

    public GameThread( SurfaceHolder surfaceHolder, Context context, Game game ) {
        isRunning = true;
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        this.game = game;
    }

    public void run() {
        Canvas canvas = surfaceHolder.lockCanvas();
        long previousTimeMillis = System.currentTimeMillis();

        game.initializeBoard( canvas.getWidth() );
        surfaceHolder.unlockCanvasAndPost( canvas );

        while( isRunning ) {
            try {
                synchronized( surfaceHolder ) {
                    canvas = surfaceHolder.lockCanvas();

                    if( canvas == null ) break;

                    long currentTimeMillis = System.currentTimeMillis();
                    double elapsedTimeMillis = currentTimeMillis - previousTimeMillis;

                    game.animateOver( elapsedTimeMillis );
                    game.drawToCanvas( context, canvas );

                    previousTimeMillis = currentTimeMillis;
                }
            } finally {
                if( canvas != null ) surfaceHolder.unlockCanvasAndPost( canvas );
            }
        }
    }

    public void end() {
        isRunning = false;
    }
}
