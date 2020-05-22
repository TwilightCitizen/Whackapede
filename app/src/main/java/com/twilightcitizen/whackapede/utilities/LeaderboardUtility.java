/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.utilities;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/*
Leaderboard Utility abstracts the publication and retrieval of player scores to and from a Firebase
Cloud Firestore.  It methods accept callbacks that conform to event interfaces for reporting the
success or failure of these transactions so that callers can act accordingly.
*/
public class LeaderboardUtility {
    // The singleton instance.
    private static LeaderboardUtility instance = null;

    // Leaderboard constants.
    private static final String FINAL_SCORE  = "finalScore";
    private static final String LEADERBOARD  = "leaderboard";

    // Private constructor prevents instantiation.
    private LeaderboardUtility() {}

    // Return the singleton instance, instantiating as needed.
    public static LeaderboardUtility getInstance() {
        if( instance == null ) instance = new LeaderboardUtility();

        return instance;
    }

    // Inter-class communication interfaces for handling Leaderboard events.
    public interface OnGetLeadersListener {
        void onGetLeaderSucceeded( ArrayList< LeaderboardEntry > topLimitLeaders );
        void onGetLeadersFailed( Exception e );
    }

    public interface OnPutScoreListener {
        void onPutScoreSucceeded();
        void onPutScoreFailed( Exception e );
    }

    private FirebaseFirestore getConfiguredFirestore( Context context ) {
        // Initialize Firebase.
        FirebaseApp.initializeApp( context );

        // Get the Firestore instance.
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        // Configure the instance without persistence
        FirebaseFirestoreSettings firebaseFirestoreSettings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled( false ).build();

        firebaseFirestore.setFirestoreSettings( firebaseFirestoreSettings );

        return firebaseFirestore;
    }

    public void publishScoreToLeaderboard(
        Context context, GoogleSignInAccount googleSignInAccount,
        int finalScore, int totalRounds, long totalTime, OnPutScoreListener onPutScoreListener
    ) {
        // Guard against publishing the score of a guest user.
        if( googleSignInAccount == null ) return;

        // Get the Google Sign In account ID.
        String googleSignInId = googleSignInAccount.getId();
        String displayName = googleSignInAccount.getDisplayName();
        Uri photoUrl = googleSignInAccount.getPhotoUrl();
        String avatar = photoUrl == null ? "" : photoUrl.toString();

        // Guard against no Google Sign In account ID.
        if( googleSignInId == null ) return;

        // Get an initialized, configured Firestore instance.
        FirebaseFirestore firebaseFirestore = getConfiguredFirestore( context );

        // Get any leaderboard entry for the signed in user.
        firebaseFirestore.collection( LEADERBOARD ).document( googleSignInId ).get()

            .addOnSuccessListener( ( DocumentSnapshot documentSnapshot ) -> {
                // Create the leaderboard entry if it does not exist.
                if( !documentSnapshot.exists() ) {
                    putLeaderboardEntry(
                        firebaseFirestore, googleSignInId, displayName, avatar,
                        finalScore, totalRounds, totalTime, onPutScoreListener
                    );
                // If it is greater than the final score the user just got, do nothing.
                } else if( existingScoreBeatsNewScore( documentSnapshot, finalScore ) ){
                    onPutScoreListener.onPutScoreSucceeded();
                    // Otherwise, update the leaderboard.
                } else updateLeaderboardEntry(
                    firebaseFirestore, googleSignInId, finalScore,
                        onPutScoreListener
                );
            } )

            // Notify the caller of unsuccessful score publication to the leaderboard.
            .addOnFailureListener( onPutScoreListener::onPutScoreFailed );
    }

    private boolean existingScoreBeatsNewScore(
        DocumentSnapshot retrievedLeaderBoardEntry, int finalScore
    ) {
        // Evil cast the final score on the existing leaderboard entry.
        int retrievedFinalScore = (int) (long) retrievedLeaderBoardEntry.get( FINAL_SCORE );

        return retrievedFinalScore > finalScore;
    }

    private void putLeaderboardEntry(
        FirebaseFirestore firebaseFirestore, String googleSignInId, String displayName,
        String avatar, int finalScore, int totalRounds, long totalTime,
        OnPutScoreListener onPutScoreListener
    ) {
        // Create a new entry for the leaderboard.
        LeaderboardEntry leaderboardEntry = new LeaderboardEntry(
            displayName, avatar, finalScore, totalRounds, totalTime
        );

        // Write the new entry to the leaderboard.
        firebaseFirestore.collection( LEADERBOARD ).document( googleSignInId ).set( leaderboardEntry )
        // Notify the caller of successful score publication to the leaderboard.
        .addOnSuccessListener( ( Void aVoid ) -> onPutScoreListener.onPutScoreSucceeded() )
        // Notify the caller of unsuccessful score publication to the leaderboard.
        .addOnFailureListener( onPutScoreListener::onPutScoreFailed );
    }

    private void updateLeaderboardEntry(
        FirebaseFirestore firebaseFirestore, String googleSignInId,
        int finalScore, OnPutScoreListener onPutScoreListener
    ) {
        // Otherwise, update the leaderboard.
        firebaseFirestore.collection( LEADERBOARD ).document( googleSignInId ).update( FINAL_SCORE, finalScore )
            // Notify the caller of successful score publication to the leaderboard.
            .addOnSuccessListener( ( Void aVoid ) -> onPutScoreListener.onPutScoreSucceeded() )
            // Notify the caller of unsuccessful score publication to the leaderboard.
            .addOnFailureListener( onPutScoreListener::onPutScoreFailed );
    }

    @SuppressWarnings( "SameParameterValue" ) public void getTopLimitLeaders(
        Context context, int limit, OnGetLeadersListener onGetLeadersListener
    ) {
        // Top limit leaders on the leaderboard, if any.
        ArrayList< LeaderboardEntry > topLimitLeaders = new ArrayList<>();

        // Get an initialized, configured Firestore instance.
        FirebaseFirestore firebaseFirestore = getConfiguredFirestore( context );

        // Get the top limit leaders, sorted by final score descending.
        firebaseFirestore.collection( LEADERBOARD )
            .orderBy( FINAL_SCORE, Query.Direction.DESCENDING )
            .limit( limit ).get()

            .addOnSuccessListener( ( QuerySnapshot queryDocumentSnapshots ) -> {
                // Put the top limit leaders in the list.
                for( QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots ) {
                    topLimitLeaders.add( queryDocumentSnapshot.toObject( LeaderboardEntry.class ) );
                }

                // Notify the caller that the top limit leaders were retrieved.
                onGetLeadersListener.onGetLeaderSucceeded( topLimitLeaders );
            } )

            // Notify the caller that the top limit leaders could not be retrieved.
            // This almost certainly will never get called.  Instead, the success listener
            // will provide an empty query.
            .addOnFailureListener( onGetLeadersListener::onGetLeadersFailed );
    }
}