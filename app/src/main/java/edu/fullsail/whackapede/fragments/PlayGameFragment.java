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
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import edu.fullsail.whackapede.R;
import edu.fullsail.whackapede.activities.GameActivity;

/*
Play Game Fragment precedes Game Fragment, providing an intermediate launch pad with buttons
to proceed to the game as a guest or with a signed in Google account.  It also displays the app
logo and uses a custom back button in lieu of the action bar for aesthetic purposes.
*/
@SuppressWarnings( "WeakerAccess" ) public class PlayGameFragment extends Fragment {
    // Game Activity must host Play Game Fragment.
    private GameActivity gameActivity;

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
        setupLaunchpadButtons( view );
    }

    // Setup the launchpad buttons with navigation host fragment actions on click.
    private void setupLaunchpadButtons( View view ) {
        NavController navController = NavHostFragment.findNavController( PlayGameFragment.this );

        view.findViewById( R.id.button_guest ).setOnClickListener(
                button -> navController.navigate( R.id.action_PlayGameFragment_to_GameFragment ) );

        /* view.findViewById( R.id.sign_in ).setOnClickListener(
                button -> navController.navigate( R.id.todo ) ); */

        view.findViewById( R.id.button_back ).setOnClickListener( button -> navController.navigateUp() );
    }

    // Hide the action bar on resume.
    @Override public void onResume() {
        super.onResume();
        gameActivity.hideActionBar();
    }
}