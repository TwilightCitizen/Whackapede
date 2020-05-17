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

import edu.fullsail.whackapede.R;
import edu.fullsail.whackapede.activities.GameActivity;

/*
Leaderboard Fragment displays a scrollable list of players' published top scores, ordered from
highest to lowest, using the action bar for back/up navigation.
*/
@SuppressWarnings( "WeakerAccess" ) public class LeaderboardFragment extends Fragment {
    // Game Activity must host Leaderboard Fragment.
    private GameActivity gameActivity;

    // Check the host context on attachment.
    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );
        checkGameActivityHost( context );
    }

    // Ensure that the host context is a Game Activity.
    private void checkGameActivityHost( Context context ) {
        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host LeaderboardFragment" );

        gameActivity = (GameActivity) context;
    }

    // Just inflate the view on view creation.
    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_leaderboard, container, false );
    }

    // Show the action bar on resume.
    @Override public void onResume() {
        super.onResume();
        gameActivity.showActionBar();
    }
}