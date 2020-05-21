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

import com.twilightcitizen.whackapede.R;
import com.twilightcitizen.whackapede.activities.GameActivity;

/*
Instructions Fragment simply displays instructions on how to play Whack-A-Pede, using the action
bar for back/up navigation.
*/
@SuppressWarnings( "WeakerAccess" ) public class InstructionsFragment extends Fragment {
    // Game Activity must host Instructions Fragment.
    private GameActivity gameActivity;

    // Check the host context on attachment.
    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );
        checkGameActivityHost( context );
    }

    // Ensure that the host context is a Game Activity.
    private void checkGameActivityHost( Context context ) {
        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host InstructionsFragment" );

        gameActivity = (GameActivity) context;
    }

    // Just inflate the view on view creation.
    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_instructions, container, false );
    }

    // Show the action bar on resume.
    @Override public void onResume() {
        super.onResume();
        gameActivity.showActionBar();
    }
}