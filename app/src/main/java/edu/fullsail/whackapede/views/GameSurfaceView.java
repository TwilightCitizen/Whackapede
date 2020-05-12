/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import edu.fullsail.whackapede.models.Game;
import edu.fullsail.whackapede.threads.GameThread;

@SuppressLint( "ViewConstructor" )
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Game game;

    public GameSurfaceView( Context context, AttributeSet attrs, Game game ) {
        super( context, attrs );

        SurfaceHolder surfaceHolder = getHolder();

        surfaceHolder.addCallback( this );

        this.game = game;
        gameThread = new GameThread( surfaceHolder, context, game );
    }

    @Override public void surfaceCreated( SurfaceHolder holder ) {
        gameThread.start();
    }

    @Override public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ) {

    }

    @Override public void surfaceDestroyed( SurfaceHolder holder ) {
        gameThread.end();
        gameThread.interrupt();
    }

    public Game getGame() {
        return game;
    }
}
