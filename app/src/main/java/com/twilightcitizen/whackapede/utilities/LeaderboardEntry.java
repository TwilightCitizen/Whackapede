/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.utilities;

/*
Leaderboard Entry encapsulates the data for an entry on the Firebase Cloud Firestore
leaderboard more conveniently that Maps or Dictionaries of values.
*/
class LeaderboardEntry {
    private String displayName;
    private int    finalScore;

    public LeaderboardEntry() {}

    LeaderboardEntry( String displayName, int finalScore ) {
        this.displayName = displayName;
        this.finalScore  = finalScore;
    }

    public String getDisplayName() { return displayName; }
    public int    getFinalScore()  { return finalScore;  }
}