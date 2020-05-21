/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.twilightcitizen.whackapede.R;
import com.twilightcitizen.whackapede.activities.GameActivity;

/*
Game Over Fragment simply displays the app logo end game details to the user, affording back/up
navigation with a custom button in lieu of the action bar up button for aesthetic reasons.
Publication of scores to the leaderboard happens here, too.
*/
@SuppressWarnings( "WeakerAccess" ) public class GameOverFragment extends Fragment
    implements GameActivity.OnNavigateBackOrUp  {

    // Game Activity must host Game Over Fragment.
    private GameActivity gameActivity;

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