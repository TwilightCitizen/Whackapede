/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.viewModels;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twilightcitizen.whackapede.models.Game;

/*
Game ViewModel maintains model information for the game across lifecycle changes.
*/
public class GameViewModel extends ViewModel {
    // Game used.
    private Game game;
    // Google Sign In account used.
    private GoogleSignInAccount googleSignInAccount;

    public Game getGame() {
        if( game == null ) game = new Game();

        return game;
    }

    public void setGame( Game game ) {
        this.game = game;
    }

    public GoogleSignInAccount getGoogleSignInAccount() {
        return googleSignInAccount;
    }

    public void setGoogleSignInAccount( GoogleSignInAccount googleSignInAccount ) {
        this.googleSignInAccount = googleSignInAccount;
    }
}
