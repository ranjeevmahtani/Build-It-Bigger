package com.udacity.gradle.builditbigger.free;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.GetJokeFromServerAsyncTask;
import com.udacity.gradle.builditbigger.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends android.support.v4.app.Fragment
        implements GetJokeFromServerAsyncTask.ProgressBarCallback{

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    InterstitialAd mInterstitialAd;
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

        setUpBannerAd(rootView);
        setUpInterstitialAd();
        requestNewInterstitial();

        return rootView;
    }

    private void setUpBannerAd(View root) {
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private void setUpInterstitialAd(){
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                mProgressBar.setVisibility(View.VISIBLE);
                new GetJokeFromServerAsyncTask(mThisCallback).execute(getActivity());

            }
        });
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void setUpButton(View rootView){
        Button button = (Button) rootView.findViewById(R.id.joke_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                Log.d(LOG_TAG, "mProgressBar.getVisibility(): " + mProgressBar.getVisibility());
                if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    new GetJokeFromServerAsyncTask(mThisCallback).execute(getActivity());
                }
            }
        });
    }

    //GetJokeFromServerAsyncTask.ProgressBarCallback method
    public void hideProgressBar() {
        if (mProgressBar != null && mProgressBar.getVisibility() != View.GONE) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

}
