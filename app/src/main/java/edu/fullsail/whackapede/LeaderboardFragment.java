/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

@SuppressWarnings( "WeakerAccess" )
public class LeaderboardFragment extends Fragment {
    private GameActivity gameActivity;

    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );

        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host LeaderboardFragment" );

        gameActivity = (GameActivity) context;
    }

    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_leaderboard, container, false );
    }

    @Override public void onResume() {
        super.onResume();

        gameActivity.showActionBar();
    }
}