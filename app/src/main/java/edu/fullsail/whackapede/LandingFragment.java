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
public class LandingFragment extends Fragment {
    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_landing, container, false );
    }

    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

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

    @Override public void onResume() {
        super.onResume();

        Objects.requireNonNull( ( ( AppCompatActivity ) requireActivity() ).getSupportActionBar() ).hide();
    }
}