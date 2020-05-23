/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.utilities;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.SparseIntArray;

import com.twilightcitizen.whackapede.R;
import com.twilightcitizen.whackapede.application.Whackapede;

public class SoundUtility {
    // The singleton instance.
    private static SoundUtility instance = null;

    // Sound Pool used by the utility.
    private SoundPool soundPool;
    // Sound effect IDs mapped to Sound Pool.
    private SparseIntArray soundMap;

    // IDs for sound effects.
    private static final int SOUND_ID_HIT = 0;
    private static final int SOUND_ID_MISS = 1;
    private static final int SOUND_ID_NEW_ROUND = 2;
    private static final int SOUND_ID_GAME_OVER = 3;

    // Private constructor prevents instantiation.
    private SoundUtility() {
        // Configure audio attributes for game audio.
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();

        attrBuilder.setUsage( AudioAttributes.USAGE_GAME );

        // Initialize Sound Pool to play sound effects.
        SoundPool.Builder builder = new SoundPool.Builder();

        builder.setMaxStreams( 1 );
        builder.setAudioAttributes( attrBuilder.build() );

        soundPool = builder.build();

        // Get the application context.
        Context context = Whackapede.getInstance().getContext();

        // Create sparse map of sounds and load them.
        soundMap = new SparseIntArray( 5 );

        soundMap.put( SOUND_ID_HIT, soundPool.load( context, R.raw.hit, 1 ) );
        soundMap.put( SOUND_ID_MISS, soundPool.load( context, R.raw.miss, 1 ) );
        soundMap.put( SOUND_ID_NEW_ROUND, soundPool.load( context, R.raw.new_round, 1 ) );
        soundMap.put( SOUND_ID_GAME_OVER, soundPool.load( context, R.raw.game_over, 1 ) );
    }

    // Return the singleton instance, instantiating as needed.
    public static SoundUtility getInstance() {
        if( instance == null ) instance = new SoundUtility();

        return instance;
    }

    public void playHit() { soundPool.play( soundMap.get( SOUND_ID_HIT ), 1, 1, 1, 0, 1f ); }
    public void playMiss() { soundPool.play( soundMap.get( SOUND_ID_MISS ), 1, 1, 1, 0, 1f ); }
    public void playNewRound() { soundPool.play( soundMap.get( SOUND_ID_NEW_ROUND ), 1, 1, 1, 0, 1f ); }
    public void playGameOver() { soundPool.play( soundMap.get( SOUND_ID_GAME_OVER ), 1, 1, 1, 0, 1f ); }
}
