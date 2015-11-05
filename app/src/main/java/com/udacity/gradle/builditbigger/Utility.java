package com.udacity.gradle.builditbigger;

import android.content.Context;

/**
 * Created by ranjeevmahtani on 11/4/15.
 */
public class Utility {

    public static boolean isFreeVersion(Context context) {
        return (context.getString(R.string.this_flavor).equals(context.getString(R.string.flavor_free)));
    }
}
