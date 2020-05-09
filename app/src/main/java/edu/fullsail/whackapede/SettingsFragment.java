/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Objects;

@SuppressWarnings( "WeakerAccess" )
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override public void onCreatePreferences( Bundle savedInstanceState, String rootKey ) {
        addPreferencesFromResource( R.xml.game_settings );
    }

    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );

        Objects.requireNonNull( ( ( AppCompatActivity ) requireActivity() ).getSupportActionBar() ).show();
        requireActivity().setTitle( R.string.settings );
    }
}