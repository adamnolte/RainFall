package com.redbeardstudios.rainfall;

/**
 * Created by Adam on 2/17/2015.
 */
public interface AdsController {

    public boolean isConnected();
    public void showInterstitialAd (Runnable then);
}
