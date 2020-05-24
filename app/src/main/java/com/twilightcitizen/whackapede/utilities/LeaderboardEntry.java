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
public class LeaderboardEntry {
    private String displayName;
    private String avatar;
    private int finalScore;
    private int totalRounds;
    private long totalTime;

    public LeaderboardEntry() {}

    LeaderboardEntry(
        String displayName, String avatar, int finalScore, int totalRounds, long totalTime
    ) {
        this.displayName = displayName;
        this.avatar = avatar;
        this.finalScore = finalScore;
        this.totalRounds = totalRounds;
        this.totalTime = totalTime;
    }

    public String getDisplayName() { return displayName; }
    public String getAvatar() { return avatar; }
    public int getFinalScore() { return finalScore; }
    public int getTotalRounds() { return totalRounds; }
    public long getTotalTime() { return totalTime; }
}