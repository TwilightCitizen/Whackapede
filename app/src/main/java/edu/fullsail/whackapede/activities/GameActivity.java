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

public class GameActivity extends AppCompatActivity {
    @Override protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game );

        Toolbar toolbar = findViewById( R.id.toolbar );

        setSupportActionBar( toolbar );
    }

    @Override protected void onStart() {
        super.onStart();

        NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment );

        NavigationUI.setupActionBarWithNavController( this, navController );
    }

    @Override public boolean onSupportNavigateUp() {
        Fragment fragment = getSupportFragmentManager().getFragments().get( 0 )
            .getChildFragmentManager().getFragments().get( 0 );

        GameFragment gameFragment;

        if( fragment instanceof GameFragment ) {
            gameFragment = (GameFragment) fragment;

            return gameFragment.onNavigateBackOrUp() &&
                Navigation.findNavController( this, R.id.nav_host_fragment ).navigateUp() ||
                super.onSupportNavigateUp();
        }

        return Navigation.findNavController( this, R.id.nav_host_fragment ).navigateUp() ||
            super.onSupportNavigateUp();
    }

    @Override public void onBackPressed() {
        onSupportNavigateUp();
    }

    public void showActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if( actionBar == null ) return;

        actionBar.show();
    }

    public void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if( actionBar == null ) return;

        actionBar.hide();
    }
}
