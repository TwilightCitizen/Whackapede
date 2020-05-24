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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twilightcitizen.whackapede.R;
import com.twilightcitizen.whackapede.activities.GameActivity;
import com.twilightcitizen.whackapede.utilities.LeaderboardUtility;

import java.util.Locale;

/*
Game Over Fragment simply displays the app logo end game details to the user, affording back/up
navigation with a custom button in lieu of the action bar up button for aesthetic reasons.
Publication of scores to the leaderboard happens here, too.
*/
@SuppressWarnings( "WeakerAccess" ) public class GameOverFragment extends Fragment
    implements GameActivity.OnNavigateBackOrUp, LeaderboardUtility.OnPutScoreListener {

    // Game Activity must host Game Over Fragment.
    private GameActivity gameActivity;
    // Player's authenticated Google account, if any.
    private GoogleSignInAccount googleSignInAccount;
    // Player's final score, total rounds, and total time.
    private int finalScore;
    private int totalRounds;
    private long totalTime;
    // Back button and text view for status of publishing score to leaderboard.
    private TextView textPublishing;
    private Button buttonBack;

    // Check the host context on attachment.
    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );
        checkGameActivityHost( context );
    }

    // Ensure that the host context is a Game Activity.
    private void checkGameActivityHost( Context context ) {
        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host GameOverFragment" );

        gameActivity = (GameActivity) context;
    }

    // Just inflate the view on view creation.
    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_game_over, container, false );
    }

    // Setup the back button after view creation.
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        setupBackButton( view );
        setupGuestOrGoogleAccount( view );
        publishScoreToLeaderboard( view );
    }

    // Publish the player's score to the leaderboard.
    private void publishScoreToLeaderboard( View view ) {
        textPublishing = view.findViewById( R.id.text_game_over_publish );
        buttonBack = view.findViewById( R.id.button_back );

        // Guard against publishing guest account score or zero score to leaderboard.
        if( googleSignInAccount == null || finalScore == 0 ) {
            textPublishing.setVisibility( View.GONE );
            buttonBack.setEnabled( true );

            return;
        }

        // Otherwise, attempt to publish final score to leaderboard.
        LeaderboardUtility.getInstance().publishScoreToLeaderboard(
            gameActivity, googleSignInAccount, finalScore, totalRounds, totalTime, this
        );
    }

    // Promote back/up navigation on successful score publication.
    @Override public void onPutScoreSucceeded() {
        buttonBack.setEnabled( true );

        textPublishing.setText( getString( R.string.game_over_published ) );
    }

    // Promote back/up navigation on unsuccessful score publication.
    @Override public void onPutScoreFailed( Exception e ) {
        buttonBack.setEnabled( true );

        textPublishing.setText( getString( R.string.game_over_unpublished ) );
    }

    /*
    Setup the scoreboard to display details for a guest player or player authenticated through
    Google Sign In accordingly.
    */
    private void setupGuestOrGoogleAccount( View view ) {
        if( getArguments() == null ) return;

        googleSignInAccount = getArguments().getParcelable( GameFragment.GOOGLE_SIGN_IN_ACCOUNT );
        finalScore = getArguments().getInt( GameFragment.FINAL_SCORE );
        totalRounds = getArguments().getInt( GameFragment.TOTAL_ROUNDS );
        totalTime = getArguments().getLong( GameFragment.TOTAL_TIME );

        TextView textGameOverPlayer = view.findViewById( R.id.text_game_over_player );
        TextView textGameOverMessage = view.findViewById( R.id.text_game_over_message );
        ImageView imageAvatar = view.findViewById( R.id.image_avatar );

        textGameOverMessage.setText(
            getResources().getQuantityString( R.plurals.game_over_message, finalScore, finalScore )
        );

        if( googleSignInAccount == null ) {
            textGameOverPlayer.setText( String.format(
                Locale.getDefault(), getString( R.string.game_over_player ), ""
            ) );
        } else {
            textGameOverPlayer.setText( String.format(
                Locale.getDefault(), getString( R.string.game_over_player ),
                ", " + googleSignInAccount.getDisplayName()
            ) );

            Uri uriPlayerAvatar = googleSignInAccount.getPhotoUrl();

            Glide.with( gameActivity ).load( uriPlayerAvatar )
                .placeholder( R.drawable.icon_guest_account ).into( imageAvatar );
        }
    }

    // Setup the back button for back/up navigation with the navigation host fragment.
    private void setupBackButton( View view ) {
        view.findViewById( R.id.button_back ).setOnClickListener( button -> onNavigateBackOrUp() );
    }

    // Navigate all the back to the landing.
    public boolean onNavigateBackOrUp() {
        NavController navController = NavHostFragment.findNavController( GameOverFragment.this );

        navController.navigate( R.id.action_GameOverFragment_to_LandingFragment );

        return false;
    }

    // Hide the action bar on resume.
    @Override public void onResume() {
        super.onResume();
        gameActivity.hideActionBar();
    }
}