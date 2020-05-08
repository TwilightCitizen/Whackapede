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
    }
}
