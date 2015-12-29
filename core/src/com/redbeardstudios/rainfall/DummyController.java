package com.redbeardstudios.rainfall;

/**
 * Created by Adam on 2/17/2015.
 */
public class DummyController implements AdsController, IGoogleServices {

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void showInterstitialAd(Runnable then) {

    }

    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public void rateGame() {

    }

    @Override
    public void submitScore(long score) {

    }

    @Override
    public void showScores() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }
}
