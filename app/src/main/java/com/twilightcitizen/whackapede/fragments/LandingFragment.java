/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
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
Landing Fragment is the first fragment presented by Game Activity via the navigation host fragment
when the app first launches.  It acts as the root navigation entry, displaying a simple array of
buttons in a launchpad configuration for navigation to the app's other screens.  It also displays
the app logo and affords no back/up navigation.
*/
@SuppressWarnings( "WeakerAccess" ) public class LandingFragment extends Fragment {
    // Game Activity must host Landing Fragment.
    private GameActivity gameActivity;

    // Check the host context on attachment.
    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );
        checkGameActivityHost( context );
    }

    // Ensure that the host context is a Game Activity.
    private void checkGameActivityHost( Context context ) {
        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host LandingFragment" );

        gameActivity = (GameActivity) context;
    }

    // Just inflate the view on view creation.
    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_landing, container, false );
    }

    // Setup the launchpad buttons after view creation.
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        gameActivity.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED );
        setupLaunchpadButtons( view );
    }

    // Setup the launchpad buttons with navigation host fragment actions on click.
    private void setupLaunchpadButtons( View view ) {
        NavController navController = NavHostFragment.findNavController( LandingFragment.this );

        view.findViewById( R.id.button_play_game ).setOnClickListener(
            button -> navController.navigate( R.id.action_LandingFragment_to_PlayGameFragment ) );

        view.findViewById( R.id.button_leaderboard ).setOnClickListener(
            button -> navController.navigate( R.id.action_LandingFragment_to_LeaderboardFragment ) );

        view.findViewById( R.id.button_settings ).setOnClickListener(
            button -> navController.navigate( R.id.action_LandingFragment_to_SettingsFragment ) );

        view.findViewById( R.id.button_instructions ).setOnClickListener(
            button -> navController.navigate( R.id.action_LandingFragment_to_InstructionsFragment ) );

        view.findViewById( R.id.button_credits ).setOnClickListener(
            button -> navController.navigate( R.id.action_LandingFragment_to_CreditsFragment ) );
    }

    // Hide the action bar on resume.
    @Override public void onResume() {
        super.onResume();
        gameActivity.hideActionBar();
    }
}