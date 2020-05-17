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

/*
Settings Fragment provides a standardized Android settings screen for user configuration of app
settings.
*/
@SuppressWarnings( "WeakerAccess" ) public class SettingsFragment extends PreferenceFragmentCompat {
    // Game Activity must host Settings Fragment.
    private GameActivity gameActivity;

    // Check the host context on attachment.
    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );
        checkGameActivityHost( context );
    }

    // Ensure that the host context is a Game Activity.
    private void checkGameActivityHost( Context context ) {
        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host SettingsFragment" );

        gameActivity = (GameActivity) context;
    }

    // Load preferences from XML resources.
    @Override public void onCreatePreferences( Bundle savedInstanceState, String rootKey ) {
        addPreferencesFromResource( R.xml.game_settings );
    }

    // Show the action bar on resume.
    @Override public void onResume() {
        super.onResume();
        gameActivity.showActionBar();
    }
}