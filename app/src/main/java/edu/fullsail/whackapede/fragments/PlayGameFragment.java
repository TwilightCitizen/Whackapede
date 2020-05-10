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

@SuppressWarnings( "WeakerAccess" )
public class PlayGameFragment extends Fragment {
    private GameActivity gameActivity;

    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );

        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host PlayGameFragment" );

        gameActivity = (GameActivity) context;
    }

    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_play_game, container, false );
    }

    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        NavController navController = NavHostFragment.findNavController( PlayGameFragment.this );

        view.findViewById( R.id.button_guest ).setOnClickListener(
            button -> navController.navigate( R.id.action_PlayGameFragment_to_GameFragment ) );

        /* view.findViewById( R.id.sign_in ).setOnClickListener(
                button -> navController.navigate( R.id.todo ) ); */

        view.findViewById( R.id.button_back ).setOnClickListener( button -> navController.navigateUp() );
    }

    @Override public void onResume() {
        super.onResume();

        gameActivity.hideActionBar();
    }
}