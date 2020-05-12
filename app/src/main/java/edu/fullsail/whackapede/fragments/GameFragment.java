/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.fullsail.whackapede.R;
import edu.fullsail.whackapede.activities.GameActivity;
import edu.fullsail.whackapede.models.Game;
import edu.fullsail.whackapede.views.GameSurfaceView;

@SuppressWarnings( "WeakerAccess" )
public class GameFragment extends Fragment {
    private GameActivity gameActivity;

    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );

        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host GameFragment" );

        gameActivity = (GameActivity) context;
    }

    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_game, container, false );
    }

    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        FrameLayout frameGame = view.findViewById( R.id.frame_game );
        Game game = new Game();
        GameSurfaceView gameSurfaceView = new GameSurfaceView( gameActivity, null, game );

        frameGame.addView( gameSurfaceView );
    }

    @Override public void onResume() {
        super.onResume();

        gameActivity.showActionBar();
    }
}