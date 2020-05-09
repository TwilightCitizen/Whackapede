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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Objects;

@SuppressWarnings( "WeakerAccess" )
public class LeaderboardFragment extends Fragment {
    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_leaderboard, container, false );
    }

    @Override
    public void onResume() {
        super.onResume();

        Objects.requireNonNull( ( ( AppCompatActivity ) requireActivity() ).getSupportActionBar() ).show();
        requireActivity().setTitle( R.string.leaderboard );
    }
}