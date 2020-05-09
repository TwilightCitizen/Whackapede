/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package edu.fullsail.whackapede;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class GameActivity extends AppCompatActivity {
    @Override protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game );

        Toolbar toolbar = findViewById( R.id.toolbar );

        setSupportActionBar( toolbar );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
