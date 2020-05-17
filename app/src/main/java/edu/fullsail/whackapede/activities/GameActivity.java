/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import edu.fullsail.whackapede.R;
import edu.fullsail.whackapede.fragments.GameFragment;

/*
Game Activity is the main activity presented after launch.  It hosts an action bar and a fragment
container view that serves as the applications navigation host fragment and manages all subordinate
fragments in the app.
*/
public class GameActivity extends AppCompatActivity {
    // Setup content and toolbar on creation.
    @Override protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game );
        setupToolbar();
    }

    // Setup the toolbar as an action bar.
    private void setupToolbar() {
        Toolbar toolbar = findViewById( R.id.toolbar );

        setSupportActionBar( toolbar );
    }

    // Setup the action bar navigation on start.
    @Override protected void onStart() {
        super.onStart();
        setupActionBarNavigation();
    }

    // Setup the action bar with the navigation controller for up navigation.
    private void setupActionBarNavigation() {
        NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment );

        NavigationUI.setupActionBarWithNavController( this, navController );
    }

    // Handle up navigation specially for the Game Fragment.
    @Override public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment );

        // Get the currently displayed fragment.
        Fragment fragment = getSupportFragmentManager().getFragments().get( 0 )
            .getChildFragmentManager().getFragments().get( 0 );

        GameFragment gameFragment;

        // Defer up navigation decision to the Game Fragment first.
        if( fragment instanceof GameFragment ) {
            gameFragment = (GameFragment) fragment;

            return gameFragment.onNavigateBackOrUp() && navController.navigateUp();
        }

        // Navigate normally for other fragments.
        return navController.navigateUp();
    }

    // Back navigation mirrors up navigation.
    @Override public void onBackPressed() { onSupportNavigateUp(); }

    // Show the action bar.  Called from subordinate fragments that need it.
    public void showActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if( actionBar == null ) return;

        actionBar.show();
    }

    // Hide the action bar.  Called from subordinate fragment that don't need it.
    public void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if( actionBar == null ) return;

        actionBar.hide();
    }
}
