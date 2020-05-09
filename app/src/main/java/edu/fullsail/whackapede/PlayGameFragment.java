/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

@SuppressWarnings( "WeakerAccess" )
public class PlayGameFragment extends Fragment {
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

        view.findViewById( R.id.button_back ).setOnClickListener( button -> navController.popBackStack() );
    }

    @Override public void onResume() {
        super.onResume();

        Objects.requireNonNull( ( ( AppCompatActivity ) requireActivity() ).getSupportActionBar() ).hide();
    }
}