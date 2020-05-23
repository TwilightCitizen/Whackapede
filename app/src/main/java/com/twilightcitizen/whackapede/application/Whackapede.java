/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.application;

import android.app.Application;
import android.content.Context;

/*
Whackapede provides application-wide to functionality and resources.  Presently, it provides
access to the Application Context where it makes little sense to couple calling modules to
an Activity or other Context, such as the Sound Utility.
*/
public class Whackapede extends Application {
    // The singleton instance.
    private static Whackapede instance;

    // Return the singleton instance, instantiating as needed.
    public static Whackapede getInstance() { return instance; }

    // Return the application context.
    public Context getContext() { return instance.getApplicationContext(); }

    // Save the instance on creation.
    @Override public void onCreate() {
        instance = this;

        super.onCreate();
    }
}
