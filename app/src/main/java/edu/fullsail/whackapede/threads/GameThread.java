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
import edu.fullsail.whackapede.models.GameArena;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private Context context;
    private boolean isRunning;
    private Game game;

    public GameThread( SurfaceHolder surfaceHolder, Context context, Game game ) {
        isRunning = true;
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        this.game = game;
    }

    public void run() {
        while( isRunning ) {
            Canvas canvas = surfaceHolder.lockCanvas();
            GameArena gameArena = new GameArena( context, canvas, game );

            gameArena.drawToCanvas();
            surfaceHolder.unlockCanvasAndPost( canvas );
        }
    }

    public void end() {
        isRunning = false;
    }
}
