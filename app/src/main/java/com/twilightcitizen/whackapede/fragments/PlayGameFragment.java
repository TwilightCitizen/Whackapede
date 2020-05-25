/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.twilightcitizen.whackapede.R;
import com.twilightcitizen.whackapede.activities.GameActivity;
import com.twilightcitizen.whackapede.models.Game;
import com.twilightcitizen.whackapede.viewModels.GameViewModel;

/*
Play Game Fragment precedes Game Fragment, providing an intermediate launch pad with buttons
to proceed to the game as a guest or with a signed in Google account.  It also displays the app
logo and uses a custom back button in lieu of the action bar for aesthetic purposes.
*/
@SuppressWarnings( "WeakerAccess" ) public class PlayGameFragment extends Fragment {
    // View model used by the game.
    private GameViewModel gameViewModel;

    // Game Activity must host Play Game Fragment.
    private GameActivity gameActivity;

    // Google Sign In request code.
    private static final int REQUEST_GOOGLE_SIGN_IN = 100;

    // Check the host context on attachment.
    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );
        checkGameActivityHost( context );
    }

    // Ensure that the host context is a Game Activity.
    private void checkGameActivityHost( Context context ) {
        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host PlayGameFragment" );

        gameActivity = (GameActivity) context;
    }

    // Just inflate the view on view creation.
    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_play_game, container, false );
    }

    // Setup the launchpad buttons after view creation.
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        setupGameViewModel();
        setupLaunchpadButtons( view );
    }

    // Capture the view model for the game.  Always start a new game from this fragment.
    private void setupGameViewModel() {
        gameViewModel = new ViewModelProvider( gameActivity ).get( GameViewModel.class );

        gameViewModel.setGame( new Game() );
    }

    // Setup the launchpad buttons with navigation host fragment actions on click.
    private void setupLaunchpadButtons( View view ) {
        NavController navController = NavHostFragment.findNavController( PlayGameFragment.this );

        view.findViewById( R.id.button_guest ).setOnClickListener( button -> {
            gameViewModel.setGoogleSignInAccount( null );
            navController.navigate( R.id.action_PlayGameFragment_to_GameFragment );
        } );

        view.findViewById( R.id.button_sign_in ).setOnClickListener( this::onSignInClick );

        view.findViewById( R.id.button_back ).setOnClickListener( button -> navController.navigateUp() );
    }

    // Setup Google Sign In button to attempt Google account authentication.
    private void onSignInClick( View v ) {
        GoogleSignInOptions googleSignInOptions =
            new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN )
                .requestIdToken( getString( R.string.default_web_client_id ) )
                .requestEmail().build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient( gameActivity, googleSignInOptions );
        Intent googleSignInIntent = googleSignInClient.getSignInIntent();

        startActivityForResult( googleSignInIntent, REQUEST_GOOGLE_SIGN_IN );
        googleSignInClient.signOut();
    }

    // Obtain Google Sign In task for handling Google Sign In result.
    @Override public void onActivityResult(
        int requestCode, int resultCode, @Nullable Intent data
    ) {
        super.onActivityResult( requestCode, resultCode, data );

        // Guard against non-Google Sign In request.
        if( requestCode != REQUEST_GOOGLE_SIGN_IN ) return;

        // Obtain asynchronous task in which Google Sign In activity authenticates user.
        Task< GoogleSignInAccount > googleSignInAccountTask = GoogleSignIn.getSignedInAccountFromIntent( data );

        // Process results of Google Sign In task.
        handleGoogleSignInResult( googleSignInAccountTask );
    }

    // Handle the result of the Google Sign In task result.
    private void handleGoogleSignInResult( Task< GoogleSignInAccount > googleSignInAccountTask ) {
        NavController navController = NavHostFragment.findNavController( PlayGameFragment.this );

        try {
            // Attempt to obtain the authenticated account from the completed Google Sign In task.
            GoogleSignInAccount googleSignInAccount = googleSignInAccountTask.getResult( ApiException.class );

            gameViewModel.setGoogleSignInAccount( googleSignInAccount );
            navController.navigate( R.id.action_PlayGameFragment_to_GameFragment );

        } catch( ApiException e ) {
            navController.navigate( R.id.action_PlayGameFragment_to_GameFragment );
        }
    }

    // Hide the action bar on resume.
    @Override public void onResume() {
        super.onResume();
        gameActivity.hideActionBar();
    }
}