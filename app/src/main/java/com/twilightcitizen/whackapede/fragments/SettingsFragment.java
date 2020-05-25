/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;

import com.twilightcitizen.whackapede.R;
import com.twilightcitizen.whackapede.activities.GameActivity;
import com.twilightcitizen.whackapede.utilities.SoundUtility;

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
        gameActivity.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED );
    }

    // Show the action bar on resume if hosted by a Game Activity.
    @Override public void onResume() {
        super.onResume();
        gameActivity.showActionBar();
    }

    // Apply any changes to the Sound Utility on exit.
    @Override public void onStop() {
        super.onStop();

        SoundUtility soundUtility = SoundUtility.getInstance();

        soundUtility.setupVolumes();
        soundUtility.setupBackgroundMusic();
    }
}