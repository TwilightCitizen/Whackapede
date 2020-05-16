/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import edu.fullsail.whackapede.R;
import edu.fullsail.whackapede.activities.GameActivity;
import edu.fullsail.whackapede.models.Game;
import edu.fullsail.whackapede.views.GameSurfaceView;

@SuppressWarnings( "WeakerAccess" )
public class GameFragment extends Fragment {
    private FrameLayout frameGame;
    private GameActivity gameActivity;
    private Game game;
    private GameSurfaceView gameSurfaceView;
    private Menu menu;
    private TextView textScore;
    private TextView textClock;

    @Override public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setHasOptionsMenu( true );
    }

    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );

        if( !( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host GameFragment" );

        gameActivity = (GameActivity) context;
    }

    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_game, container, false );
    }

    @Override public void onCreateOptionsMenu(
        @NonNull Menu menu, @NonNull MenuInflater inflater
    ) {
        this.menu = menu;

        inflater.inflate( R.menu.menu_game, menu );
    }

    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        textScore = view.findViewById( R.id.text_score );
        textClock = view.findViewById( R.id.text_clock );
        frameGame = view.findViewById( R.id.frame_game );
        game = new Game();
        gameSurfaceView = new GameSurfaceView( gameActivity, this, null, game );

        frameGame.addView( gameSurfaceView );
    }

    @Override public void onResume() {
        super.onResume();

        if( game != null ) {
            frameGame.removeAllViews();
            gameSurfaceView = new GameSurfaceView( gameActivity, this, null, game );

            frameGame.addView( gameSurfaceView );
        }

        gameActivity.showActionBar();
    }

    @Override public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        if( item.getItemId() == R.id.action_play_pause ) {
            game.toggleState();

            MenuItem itemPlayPause = menu.getItem( 0 );

            itemPlayPause.setIcon( game.isGameIsPaused() ? R.drawable.icon_play : R.drawable.icon_pause );
            itemPlayPause.setTitle( game.isGameIsPaused() ? R.string.play : R.string.pause );

            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    public void onGameStatsChanged() {
        textScore.setText( String.format( Locale.getDefault(), "%d", game.getScore() ) );
        textClock.setText( String.format( Locale.getDefault(), "%d", (int) game.getRemainingTimeMillis() ) );
    }
}