/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.utilities;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.SparseIntArray;

import com.twilightcitizen.whackapede.R;
import com.twilightcitizen.whackapede.application.Whackapede;

public class SoundUtility {
    // The singleton instance.
    private static SoundUtility instance = null;

    // Sound pool for sound effects.
    private SoundPool effectsPool;
    // Sound effect IDs mapped to the effects sound Pool.
    private SparseIntArray effectsMap;

    // Media player for background music.
    private MediaPlayer musicPlayer;

    // IDs for sound effects.
    private static final int SOUND_ID_HIT = 0;
    private static final int SOUND_ID_MISS = 1;
    private static final int SOUND_ID_NEW_ROUND = 2;
    private static final int SOUND_ID_GAME_OVER = 3;

    // Private constructor prevents instantiation.
    private SoundUtility() {
        setupSoundEffects();
        setupBackgroundMusic();
    }

    // Return the singleton instance, instantiating as needed.
    public static SoundUtility getInstance() {
        if( instance == null ) instance = new SoundUtility();

        return instance;
    }

    // Setup all the sound effects in a sound pool.
    private void setupSoundEffects() {
        // Configure audio attributes for game audio.
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();

        attrBuilder.setUsage( AudioAttributes.USAGE_GAME );

        // Initialize Sound Pool to play sound effects.
        SoundPool.Builder builder = new SoundPool.Builder();

        builder.setMaxStreams( 1 );
        builder.setAudioAttributes( attrBuilder.build() );

        effectsPool = builder.build();

        // Get the application context.
        Context context = Whackapede.getInstance().getContext();

        // Create sparse map of sounds and load them.
        effectsMap = new SparseIntArray( 5 );

        effectsMap.put( SOUND_ID_HIT, effectsPool.load( context, R.raw.hit, 1 ) );
        effectsMap.put( SOUND_ID_MISS, effectsPool.load( context, R.raw.miss, 1 ) );
        effectsMap.put( SOUND_ID_NEW_ROUND, effectsPool.load( context, R.raw.new_round, 1 ) );
        effectsMap.put( SOUND_ID_GAME_OVER, effectsPool.load( context, R.raw.game_over, 1 ) );
    }

    // Setup all the sound effects in a sound pool.
    private void setupBackgroundMusic() {
        musicPlayer = MediaPlayer.create( Whackapede.getInstance().getContext(), R.raw.mind_bender );

        musicPlayer.setLooping( true );
    }

    // Play various sound effects.
    public void playHit() { effectsPool.play( effectsMap.get( SOUND_ID_HIT ), 1, 1, 1, 0, 1f ); }
    public void playMiss() { effectsPool.play( effectsMap.get( SOUND_ID_MISS ), 1, 1, 1, 0, 1f ); }
    public void playNewRound() { effectsPool.play( effectsMap.get( SOUND_ID_NEW_ROUND ), 1, 1, 1, 0, 1f ); }
    public void playGameOver() { effectsPool.play( effectsMap.get( SOUND_ID_GAME_OVER ), 1, 1, 1, 0, 1f ); }

    // Control background music playback.
    public void playResumeMusic() { musicPlayer.start(); }
    public void pauseMusic() { musicPlayer.pause(); }
    public void stopMusic() { musicPlayer.pause(); musicPlayer.seekTo( 0 ); }
}
