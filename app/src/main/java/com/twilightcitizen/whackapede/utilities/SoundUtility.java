/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import androidx.preference.PreferenceManager;

import com.twilightcitizen.whackapede.R;
import com.twilightcitizen.whackapede.application.Whackapede;

public class SoundUtility {
    // The singleton instance.
    private static SoundUtility instance = null;

    // Sound pool for sound effects.
    private SoundPool effectsPool;

    // Media player for background music.
    private MediaPlayer musicPlayer;

    // IDs for sound effects.
    private int soundIdHit;
    private int soundIdMiss;
    private int soundIdNewRound;
    private int soundIdGameOver;

    // Volume settings for sound effects and music;
    private float volumeEffects;
    private float volumeMusic;

    // Private constructor prevents instantiation.
    private SoundUtility() {
        setupVolumes();
        setupSoundEffects();
        setupBackgroundMusic();
    }

    // Return the singleton instance, instantiating as needed.
    public static SoundUtility getInstance() {
        if( instance == null ) instance = new SoundUtility();

        return instance;
    }

    // Setup the volumes of sound effects and music.
    public void setupVolumes() {
        Context context = Whackapede.getInstance().getContext();

        SharedPreferences sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences( context );

        volumeEffects = (float) sharedPreferences.getInt(
            context.getString( R.string.effects_volume_key ),
            context.getResources().getInteger( R.integer.volume_effects )
        ) / context.getResources().getInteger( R.integer.volume_max );

        volumeMusic = (float) sharedPreferences.getInt(
            context.getString( R.string.music_volume_key ),
            context.getResources().getInteger( R.integer.volume_music )
        ) / context.getResources().getInteger( R.integer.volume_max );
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

        // Load sounds effects into pool, saving IDs.
        soundIdHit = effectsPool.load( context, R.raw.hit, 1 );
        soundIdMiss = effectsPool.load( context, R.raw.miss, 1 );
        soundIdNewRound = effectsPool.load( context, R.raw.new_round, 1 );
        soundIdGameOver = effectsPool.load( context, R.raw.game_over, 1 );
    }

    // Setup all the sound effects in a sound pool.
    public void setupBackgroundMusic() {
        Context context = Whackapede.getInstance().getContext();

        SharedPreferences sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences( context );

        String musicTrackName = sharedPreferences.getString(
            context.getString( R.string.music_track_key ), "Mind Bender"
        ).toLowerCase().replaceAll( "[^a-zA-Z0-9]", "_" );

        int musicTrackID = context.getResources().getIdentifier(
            musicTrackName, "raw", context.getPackageName()
        );

        musicPlayer = MediaPlayer.create( context, musicTrackID );

        musicPlayer.setLooping( true );
        musicPlayer.setVolume( volumeMusic, volumeMusic );
    }

    // Play various sound effects.
    private void playEffect( int soundId ) {
        effectsPool.play( soundId, volumeEffects, volumeEffects, 1, 0, 1f );
    }

    public void playHit() { playEffect( soundIdHit ); }
    public void playMiss() { playEffect( soundIdMiss ); }
    public void playNewRound() { playEffect( soundIdNewRound ); }
    public void playGameOver() { playEffect( soundIdGameOver ); }

    // Control background music playback.
    public void playResumeMusic() { musicPlayer.start(); }
    public void pauseMusic() { musicPlayer.pause(); }
    public void stopMusic() { musicPlayer.pause(); musicPlayer.seekTo( 0 ); }
}
