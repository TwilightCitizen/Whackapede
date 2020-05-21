/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.views;

import android.annotation.SuppressLint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;


import com.twilightcitizen.whackapede.activities.GameActivity;
import com.twilightcitizen.whackapede.fragments.GameFragment;
import com.twilightcitizen.whackapede.models.Game;
import com.twilightcitizen.whackapede.threads.GameThread;

/*
Game Surface View is the custom SurfaceView to which the Game's arena is drawn for on-screen display.
It maintains an instance of the Game it displays to publish touch events to it, and a Game Thread to
control the Game and and its drawing in the background so the UI thread remains unencumbered.
*/
@SuppressLint( "ViewConstructor" ) public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    // Game Thread to control and draw game.
    private final GameThread gameThread;
    // Game being controlled and drawn.
    private final Game game;

    // Get a holder to the SurfaceView for drawing, passing it and the Game to be drawn to a new Game Thread.
    public GameSurfaceView( GameActivity gameActivity, GameFragment gameFragment, AttributeSet attrs, Game game ) {
        super( gameActivity, attrs );

        SurfaceHolder surfaceHolder = getHolder();

        surfaceHolder.addCallback( this );

        this.game = game;
        gameThread = new GameThread( surfaceHolder, gameActivity, gameFragment, game );
    }

    // Start the Game Thread when the drawing surface is created and ready for drawing.
    @Override public void surfaceCreated( SurfaceHolder holder ) {
        gameThread.start();
    }

    @Override public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ) {}

    // Stop the Game Thread when the drawing surface is no longer available.
    @Override public void surfaceDestroyed( SurfaceHolder holder ) {
        gameThread.end();
        gameThread.interrupt();
    }

    /*
    Obtain touch events, translating their location from window/screen-based to surface-based coordinates,
    passing them to the Game for action.
     */
    @SuppressLint( "ClickableViewAccessibility" ) @Override public boolean onTouchEvent( MotionEvent event ) {
        if( event.getAction() != MotionEvent.ACTION_DOWN ) return false;

        /*
        The mere act of obtaining motion events is enough to translate the tap location from window/screen-
        based coordinates to surface-based ones.  There is no need to subtract the SurfaceView's left or
        top placement within the screen.  Frankly, I'm a little mystified why this works as is and need
        to do a little more research.  In the meantime, it seems to work, so *shrugs*.
        */
        MotionEvent translatedEvent = MotionEvent.obtain(
            event.getDownTime(), event.getEventTime(), event.getAction(),
            event.getX(), event.getY(), event.getMetaState()
        );

        game.addTouchEvent( translatedEvent );

        return  true;
    }
}
