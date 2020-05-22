/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.utilities;

import java.util.Locale;

/*
Time Utility provide utility methods through a singleton instance for the manipulation of elements
of time.
*/
public class TimeUtility {
    // The singleton instance.
    private static final TimeUtility instance = new TimeUtility();
    // Last marked time, if any.
    private static Long lastMark = null;

    // Singleton cannot be instantiated.
    private TimeUtility() {}

    // Singleton instance must be retrieved.
    public static TimeUtility getInstance() { return instance; }

    // Convert milliseconds into minutes and seconds as a string in the format of M:SS.
    public String millisToMinutesAndSeconds( long millis ) {
        long minutes = ( millis / 1000 ) / 60;
        long seconds = ( millis / 1000 ) % 60;

        return String.format( Locale.getDefault(), "%d:%02d", minutes, seconds );
    }

    // Convert milliseconds into a fractional interval of seconds.
    public Double millisToIntervalOfSeconds( long millis ) { return millis / 1000d; }

    // Convert seconds to milliseconds.
    public long secondsToMillis( int seconds ) { return seconds * 1000; }

    // Convert milliseconds to whole seconds.
    public int millisToSeconds( long millis ) { return (int) millis / 60; }

    // Mark current system time and return the elapsed time in milliseconds since last mark.
    public long getTimeElapsedMillis() {
        // No elapsed time can be tracked without a first mark.
        if( lastMark == null ) {
            lastMark = System.currentTimeMillis();

            return 0;
        }

        long mark = System.currentTimeMillis();
        long elapsed = mark - lastMark;
        lastMark = mark;

        return  elapsed;
    }
}