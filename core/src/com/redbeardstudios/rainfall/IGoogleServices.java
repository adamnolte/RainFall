package com.redbeardstudios.rainfall;

/**
 * Created by Adam on 2/18/2015.
 */
public interface IGoogleServices {
    public void signIn();
    public void signOut();
    public void rateGame();
    public void submitScore(long score);
    public void showScores();
    public boolean isSignedIn();
}
