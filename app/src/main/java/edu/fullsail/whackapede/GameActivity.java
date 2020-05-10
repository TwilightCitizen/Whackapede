/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

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
        return Navigation.findNavController( this, R.id.nav_host_fragment ).navigateUp() ||
            super.onSupportNavigateUp();
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
