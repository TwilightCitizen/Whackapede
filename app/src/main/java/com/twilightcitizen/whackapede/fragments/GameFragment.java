/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Locale;

import com.twilightcitizen.whackapede.R;
import com.twilightcitizen.whackapede.activities.GameActivity;
import com.twilightcitizen.whackapede.models.Game;
import com.twilightcitizen.whackapede.utilities.SoundUtility;
import com.twilightcitizen.whackapede.utilities.TimeUtility;
import com.twilightcitizen.whackapede.viewModels.GameViewModel;
import com.twilightcitizen.whackapede.views.GameSurfaceView;

/*
Game Fragment displays the main game screen to the user.  It provides back/up navigation via the
action bar, but overrides its default behavior to help catch accidental taps that could cause the
player to unintentionally leave an ongoing game.  It provides a single menu action for pausing and
starting/resuming games, a scoreboard to show the guest or authenticated player's avatar, current
score, and time remaining, and the game arena itself via a SurfaceView loaded into a containing frame.
*/
@SuppressWarnings( "WeakerAccess" ) public class GameFragment extends Fragment
    implements GameActivity.OnNavigateBackOrUp {

    // View model used by the game.
    private GameViewModel gameViewModel;

    // Game Activity must host Game Fragment.
    private GameActivity gameActivity;

    // The Game itself.
    private Game game;

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
    @SuppressLint( "SourceLockedOrientationActivity" )
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        gameActivity.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        setupGameViewModel();
        setupScoreboard( view );
        setupGoogleAccount( view );
    }

    // Capture the view model for the game.
    private void setupGameViewModel() {
        gameViewModel = new ViewModelProvider( gameActivity ).get( GameViewModel.class );
    }

    /*
    Setup the scoreboard to display details for a guest player or player authenticated through
    Google Sign In accordingly.
    */
    private void setupGoogleAccount( View view ) {
        // Player's authenticated Google account, if any.
        GoogleSignInAccount googleSignInAccount = gameViewModel.getGoogleSignInAccount();

        if( googleSignInAccount == null ) return;

        String playerDisplayName = googleSignInAccount.getDisplayName();
        Uri uriPlayerAvatar = googleSignInAccount.getPhotoUrl();
        TextView textPlayer = view.findViewById( R.id.text_player );
        ImageView imageAvatar = view.findViewById( R.id.image_avatar );

        textPlayer.setText( playerDisplayName );

        Glide.with( gameActivity ).load( uriPlayerAvatar )
            .placeholder( R.drawable.icon_guest_account ).into( imageAvatar );
    }

    /*
    Create a new Game and SurfaceView where its arena will be drawn, adding the game arena
    to the frame where it should be drawn.
    */
    private void setupGameArena( View view ) {
        // Frame to host the SurfaceView where the game arena will be drawn to screen.
        FrameLayout frameGame = view.findViewById( R.id.frame_game );
        game = gameViewModel.getGame();
        // SurfaceView where the game arena will be drawn to screen.
        GameSurfaceView gameSurfaceView = new GameSurfaceView( this, null, game );

        frameGame.removeAllViews();
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
        setupGameArena( requireView() );
        gameActivity.showActionBar();
    }

    // Pause any ongoing game and toggle the play/pause button on the action bar on stop.
    @Override public void onStop() {
        super.onStop();
        game.pause();
        toggleItemPlayPause();
    }

    // Navigate back/up if the game is paused, but pause it and wait if it isn't.
    public boolean onNavigateBackOrUp() {
        if( game.getIsPaused() ) {
            NavController navController = NavHostFragment.findNavController( GameFragment.this );

            navController.navigate( R.id.action_GameFragment_to_LandingFragment );
            SoundUtility.getInstance().stopMusic();

            return false;
        }

        game.pause();
        toggleItemPlayPause();

        return false;
    }

    // Handle options selected in the menu.
    @Override public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        // Toggle the game's paused/running state and the play/pause button when it is tapped.
        if( item.getItemId() == R.id.action_play_pause ) {
            game.toggleState();
            toggleItemPlayPause();

            return true;
        }

        // Navigate to settings with the settings button is tapped.
        if( item.getItemId() == R.id.action_settings ) {
            NavController navController = NavHostFragment.findNavController( GameFragment.this );

            navController.navigate( R.id.action_GameFragment_to_SettingsFragment );

            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    // Toggle the play/pause action button's icon and title when tapped.
    private void toggleItemPlayPause() {
        MenuItem itemPlayPause = menu.getItem( 0 );

        itemPlayPause.setIcon( game.getIsPaused() ? R.drawable.icon_play : R.drawable.icon_pause );
        itemPlayPause.setTitle( game.getIsPaused() ? R.string.play : R.string.pause );
    }

    // Publish new score and time remaining to the scoreboard.  Called from the Game Thread.
    public void onGameStatsChanged() {
        textScore.setText( String.format( Locale.getDefault(), "%,d", game.getScore() ) );

        textClock.setText( TimeUtility.getInstance().millisToMinutesAndSeconds(
            game.getRemainingTimeMillis()
        ) );
    }

    // Handle Game Over, passing Google Account and Final Score to Game Over Fragment.
    public void onGameOver() {
        NavController navController = NavHostFragment.findNavController( GameFragment.this );

        navController.navigate( R.id.action_GameFragment_to_GameOverFragment );
    }
}