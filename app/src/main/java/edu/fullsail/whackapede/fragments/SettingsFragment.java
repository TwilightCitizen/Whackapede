/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;

import edu.fullsail.whackapede.R;
import edu.fullsail.whackapede.activities.GameActivity;

@SuppressWarnings( "WeakerAccess" )
public class SettingsFragment extends PreferenceFragmentCompat {
    private GameActivity gameActivity;

    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );

        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host SettingsFragment" );

        gameActivity = (GameActivity) context;
    }

    @Override public void onCreatePreferences( Bundle savedInstanceState, String rootKey ) {
        addPreferencesFromResource( R.xml.game_settings );
    }

    @Override public void onResume() {
        super.onResume();

        gameActivity.showActionBar();
    }
}