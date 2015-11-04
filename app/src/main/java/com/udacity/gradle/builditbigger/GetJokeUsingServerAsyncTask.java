package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.androidjokelibrary.JokeActivity;
import com.example.ranjeevmahtani.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by ranjeevmahtani on 11/4/15.
 */
public class GetJokeUsingServerAsyncTask extends AsyncTask<Context, Void, String> {

    private static final String LOG_TAG=GetJokeUsingServerAsyncTask.class.getSimpleName();

    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://build-it-bigger-1119.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.d(LOG_TAG, "doInBackground: caught IOException: " + e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_EXTRA, result);
        context.startActivity(intent);
    }
}