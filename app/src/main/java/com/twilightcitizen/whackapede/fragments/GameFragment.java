/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Locale;

import com.twilightcitizen.whackapede.R;
import com.twilightcitizen.whackapede.activities.GameActivity;
import com.twilightcitizen.whackapede.models.Game;
import com.twilightcitizen.whackapede.utilities.TimeUtility;
import com.twilightcitizen.whackapede.views.GameSurfaceView;

/*
Game Fragment displays the main game screen to the user.  It provides back/up navigation via the
action bar, but overrides its default behavior to help catch accidental taps that could cause the
player to unintentionally leave an ongoing game.  It provides a single menu action for pausing and
starting/resuming games, a scoreboard to show the guest or authenticated player's avatar, current
score, and time remaining, and the game arena itself via a SurfaceView loaded into a containing frame.
*/
@SuppressWarnings( "WeakerAccess" ) public class GameFragment extends Fragment {
    // Frame to host the SurfaceView where the game arena will be drawn to screen.
    private FrameLayout frameGame;
    // Game Activity must host Game Fragment.
    private GameActivity gameActivity;
    // The Game itself.
    private Game game;
    // SurfaceView where the game arena will be drawn to screen.
    private GameSurfaceView gameSurfaceView;
    // Menu hosting the play/pause action button.
    private Menu menu;
    // Player's current score and time remaining on the scoreboard.
    private TextView textScore;
    private TextView textClock;

    // Setup with menu options on creation.
    @Override public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setHasOptionsMenu( true );
    }

    // Check the host context on attachment.
    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );
        checkGameActivityHost( context );
    }

    // Ensure that the host context is a Game Activity.
    private void checkGameActivityHost( Context context ) {
        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host GameFragment" );

        gameActivity = (GameActivity) context;
    }

    // Just inflate the view on view creation.
    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_game, container, false );
    }

    // Inflate the menu when creating option for the action bar.
    @Override public void onCreateOptionsMenu(
        @NonNull Menu menu, @NonNull MenuInflater inflater
    ) {
        this.menu = menu;

        inflater.inflate( R.menu.menu_game, menu );
    }

    // Setup the scoreboard and game arena after view creation.
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        setupScoreboard( view );
        setupGameArena( view );
        setupGuestOrGoogleAccount( view );
    }

    private void setupGuestOrGoogleAccount( View view ) {
        if( getArguments() == null ) return;

        GoogleSignInAccount googleSignInAccount = getArguments().getParcelable( PlayGameFragment.GOOGLE_SIGN_IN_ACCOUNT );

        if( googleSignInAccount == null ) return;

        String playerDisplayName = googleSignInAccount.getDisplayName();
        Uri uriPlayerAvatar = googleSignInAccount.getPhotoUrl();
        TextView textPlayer = view.findViewById( R.id.text_player );
        ImageView imageAvatar = view.findViewById( R.id.image_avatar );

        textPlayer.setText( playerDisplayName );
        Glide.with( gameActivity ).load( uriPlayerAvatar ).placeholder( R.drawable.icon_guest_account ).into( imageAvatar );
    }

    // Create a new Game and SurfaceView where its arena will be drawn, adding the game arena
    // to the frame where it should be drawn.
    private void setupGameArena( View view ) {
        frameGame = view.findViewById( R.id.frame_game );
        game = new Game();
        gameSurfaceView = new GameSurfaceView( gameActivity, this, null, game );

        frameGame.addView( gameSurfaceView );
    }

    // Setup the fields on the scoreboard for player score and time remaining.
    private void setupScoreboard( View view ) {
        textScore = view.findViewById( R.id.text_score );
        textClock = view.findViewById( R.id.text_clock );
    }

    // Show the action bar on resume, restoring any ongoing game for display in the arena.
    @Override public void onResume() {
        super.onResume();

        if( game != null ) restoreGameToArena();

        gameActivity.showActionBar();
    }

    /*
    Game Fragment retains its Game instance when the user navigates away from the app and restores
    it if it wasn't completely destroyed, but the SurfaceView where its arena is drawn doesn't.  So,
    we remove any dead SurfaceViews from their containing frame, create a new one with the retained
    Game instance, and add it to the containing frame to maintain ongoing games between interruptions.
    */
    private void restoreGameToArena() {
        frameGame.removeAllViews();
        gameSurfaceView = new GameSurfaceView( gameActivity, this, null, game );

        frameGame.addView( gameSurfaceView );
    }

    // Pause any ongoing game and toggle the play/pause button on the action bar on stop.
    @Override public void onStop() {
        super.onStop();
        game.pause();
        toggleItemPlayPause();
    }

    // Navigate back/up if the game is paused, but pause it and wait if it isn't.
    public boolean onNavigateBackOrUp() {
        if( game.isGameIsPaused() ) return true;

        game.pause();
        toggleItemPlayPause();

        return false;
    }

    // Toggle the game's paused/running state and the play/pause button when it is tapped.
    @Override public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        if( item.getItemId() == R.id.action_play_pause ) {
            game.toggleState();
            toggleItemPlayPause();

            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    // Toggle the play/pause action button's icon and title when tapped.
    private void toggleItemPlayPause() {
        MenuItem itemPlayPause = menu.getItem( 0 );

        itemPlayPause.setIcon( game.isGameIsPaused() ? R.drawable.icon_play : R.drawable.icon_pause );
        itemPlayPause.setTitle( game.isGameIsPaused() ? R.string.play : R.string.pause );
    }

    // Publish new score and time remaining to the scoreboard.  Called from the Game Thread.
    public void onGameStatsChanged() {
        textScore.setText( String.format( Locale.getDefault(), "%d", game.getScore() ) );
        textClock.setText( TimeUtility.getInstance().millisToMinutesAndSeconds( game.getRemainingTimeMillis() ) );
    }
}