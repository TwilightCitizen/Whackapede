/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.views;

import android.annotation.SuppressLint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;


import edu.fullsail.whackapede.activities.GameActivity;
import edu.fullsail.whackapede.fragments.GameFragment;
import edu.fullsail.whackapede.models.Game;
import edu.fullsail.whackapede.threads.GameThread;

@SuppressLint( "ViewConstructor" )
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final GameThread gameThread;
    private final Game game;
    private GameFragment gameFragment;

    public GameSurfaceView( GameActivity gameActivity, GameFragment gameFragment, AttributeSet attrs, Game game ) {
        super( gameActivity, attrs );

        SurfaceHolder surfaceHolder = getHolder();

        surfaceHolder.addCallback( this );

        this.game = game;
        this.gameFragment = gameFragment;
        gameThread = new GameThread( surfaceHolder, gameActivity, gameFragment, game );
    }

    @Override public void surfaceCreated( SurfaceHolder holder ) {
        gameThread.start();
    }

    @Override public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ) {}

    @Override public void surfaceDestroyed( SurfaceHolder holder ) {
        gameThread.end();
        gameThread.interrupt();
    }

    @SuppressLint( "ClickableViewAccessibility" )
    @Override public boolean onTouchEvent( MotionEvent event ) {
        if( event.getAction() != MotionEvent.ACTION_DOWN ) return false;

        MotionEvent translatedEvent = MotionEvent.obtain(
            event.getDownTime(), event.getEventTime(), event.getAction(),
            event.getX(), event.getY(), event.getMetaState()
        );

        game.addTouchEvent( translatedEvent );

        return  true;
    }
}
