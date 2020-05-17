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

/*
Game Thread runs in the background from the UI thread, synchronizing the repetition of manipulating
the Game's state and drawing its visual elements to the Game SurfaceView over time.
*/
public class GameThread extends Thread  {
    // Game SurfaceView holder provides the canvas on which the game arena is drawn.
    private final SurfaceHolder surfaceHolder;
    // Game Activity provides context for obtaining resources.
    private final GameActivity gameActivity;
    // Flag to indicate the Game Thread's running status.
    private boolean isRunning;
    // Game instance being controlled and drawn to screen.
    private final Game game;
    // Game Fragment to notify when game stats have changed.
    private final GameFragment gameFragment;

    // Maintain all the above instances and flag the Game Thread as running.
    public GameThread(
        SurfaceHolder surfaceHolder, GameActivity gameActivity, GameFragment gameFragment, Game game
    ) {
        isRunning = true;
        this.surfaceHolder = surfaceHolder;
        this.gameActivity = gameActivity;
        this.game = game;
        this.gameFragment = gameFragment;
    }

    /*
    Initialize the Game's board, then repeatedly loop it over time, updating the Game Fragment
    of game stat changes each time.
     */
    public void run() {
        // Get the current time from which all time intervals derive going forward.
        long previousTimeMillis = System.currentTimeMillis();
        // Get the canvas from the surface initially to get its width.
        Canvas canvas = surfaceHolder.lockCanvas();

        // Initialize the Game's visual elements for drawing to this specific canvas size.
        game.initializeBoard( canvas.getWidth() );
        surfaceHolder.unlockCanvasAndPost( canvas );

        // Go until the thread is stopped.
        while( isRunning ) {
            try {
                // Prevent concurrent reentry into the game loop.
                synchronized( surfaceHolder ) {
                    // Get the canvas from the surface for drawing the game to it.
                    canvas = surfaceHolder.lockCanvas();

                    // Make sure it's still valid in case the thread was stopped from the
                    // user navigating away or some other interruption.
                    if( canvas == null ) break;

                    // Get the time elapsed this loop.
                    long currentTimeMillis = System.currentTimeMillis();
                    double elapsedTimeMillis = currentTimeMillis - previousTimeMillis;

                    // Pass the canvas and elapsed time to the game loop.
                    game.loop( gameActivity, canvas, elapsedTimeMillis );
                    // Notify the Game Fragment to update the scoreboard.
                    gameActivity.runOnUiThread( gameFragment::onGameStatsChanged );

                    // Prepare to calculate the next interval of elapsed time.
                    previousTimeMillis = currentTimeMillis;
                }
            } finally {
                // Ensure canvas is unlocked and posted.
                if( canvas != null ) surfaceHolder.unlockCanvasAndPost( canvas );
            }
        }
    }

    // Stop the Game Thread from looping after being run.
    public void end() { isRunning = false; }
}
