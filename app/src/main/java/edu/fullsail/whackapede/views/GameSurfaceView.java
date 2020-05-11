/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import edu.fullsail.whackapede.models.Game;
import edu.fullsail.whackapede.threads.GameThread;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;

    public GameSurfaceView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        SurfaceHolder surfaceHolder = getHolder();

        surfaceHolder.addCallback( this );

        gameThread = new GameThread( surfaceHolder, context );
    }

    @Override public void surfaceCreated( SurfaceHolder holder ) {
        gameThread.start();
    }

    @Override public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ) {

    }

    @Override public void surfaceDestroyed( SurfaceHolder holder ) {
        gameThread.end();
    }
}
