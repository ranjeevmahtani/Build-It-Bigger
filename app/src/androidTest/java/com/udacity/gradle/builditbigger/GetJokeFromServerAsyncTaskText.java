package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.concurrent.ExecutionException;

/**
 * Created by ranjeevmahtani on 11/4/15.
 */
public class GetJokeFromServerAsyncTaskText extends AndroidTestCase {

    public void testAsyncTaskNotEmpty() {
        try {
            String result = new GetJokeFromServerAsyncTask().execute(getContext()).get();
            assertNotSame("",result);
        } catch (InterruptedException | ExecutionException e) {
            Log.e(e.toString(), e.getMessage());
        }
    }

}
