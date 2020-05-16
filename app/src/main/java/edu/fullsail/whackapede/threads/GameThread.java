/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.threads;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import edu.fullsail.whackapede.activities.GameActivity;
import edu.fullsail.whackapede.fragments.GameFragment;
import edu.fullsail.whackapede.models.Game;

public class GameThread extends Thread  {
    private final SurfaceHolder surfaceHolder;
    private final GameActivity gameActivity;
    private boolean isRunning;
    private final Game game;
    private final GameFragment gameFragment;

    public GameThread(
        SurfaceHolder surfaceHolder, GameActivity gameActivity, GameFragment gameFragment, Game game
    ) {
        isRunning = true;
        this.surfaceHolder = surfaceHolder;
        this.gameActivity = gameActivity;
        this.game = game;
        this.gameFragment = gameFragment;
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

                    game.loop( gameActivity, canvas, elapsedTimeMillis );
                    gameActivity.runOnUiThread( gameFragment::onGameStatsChanged );


                    previousTimeMillis = currentTimeMillis;
                }
            } finally {
                if( canvas != null ) surfaceHolder.unlockCanvasAndPost( canvas );
            }
        }
    }

    public void end() { isRunning = false; }
}
