package com.udacity.gradle.builditbigger.paid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.udacity.gradle.builditbigger.GetJokeFromServerAsyncTask;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements GetJokeFromServerAsyncTask.ProgressBarCallback{

    ProgressBar mProgressBar;
    GetJokeFromServerAsyncTask.ProgressBarCallback mThisCallback;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mThisCallback = this;

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        setUpButton(rootView);

        return rootView;
    }

    private void setUpButton(View rootView){
        Button button = (Button) rootView.findViewById(R.id.joke_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                    new GetJokeFromServerAsyncTask(mThisCallback).execute(getActivity());
            }
        });
    }

    @Override
    public void hideProgressBar() {
        if (mProgressBar != null && mProgressBar.getVisibility()!=View.GONE) {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
