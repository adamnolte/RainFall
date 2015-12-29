package com.redbeardstudios.rainfall;

import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.bindings.admob.GADInterstitial;
import org.robovm.bindings.admob.GADInterstitialDelegateAdapter;
import org.robovm.bindings.admob.GADRequest;
import org.robovm.bindings.admob.GADRequestError;

public class Ads extends NSObject implements IOSAdsController{
	private UIViewController rootViewController;
	private UIWindow window;
	private GADInterstitial interstitial;
	private static final String AD_ID = "ca-app-pub-9107232888348693/9018181547";
	
	
	public void showAd(){
		if(window != null && rootViewController != null && interstitial.isReady()){
			System.out.println("Showing Ad");
			window = new UIWindow(UIScreen.getMainScreen().getBounds());
			window.setRootViewController(rootViewController);
			window.addSubview(rootViewController.getView());
			window.makeKeyAndVisible();

			interstitial.present(rootViewController);
		}		
	}
	public void initializeAds(){
		rootViewController = new UIViewController();
		window = new UIWindow(UIScreen.getMainScreen().getBounds());
		System.out.println("Initializing Ads");
		
		loadAd();
	}
	
	public void loadAd(){
		interstitial = new GADInterstitial();
        interstitial.setAdUnitID(AD_ID);

        interstitial.setDelegate(new GADInterstitialDelegateAdapter() {
            @Override
            public void didReceiveAd (GADInterstitial ad) {
                System.out.println("Did receive ad.");
            }

            @Override
            public void didFailToReceiveAd (GADInterstitial ad, GADRequestError error) {
                System.out.println(error.description());
                System.out.println(error.getErrorCode());
            }
            @Override
            public void didDismissScreen(GADInterstitial ad){
            	window.setHidden(true);
            	loadAd();
            }
        });
        
        interstitial.loadRequest(GADRequest.create());
		
	}
}
