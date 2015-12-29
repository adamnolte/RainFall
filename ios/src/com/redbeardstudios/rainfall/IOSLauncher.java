package com.redbeardstudios.rainfall;

import java.util.ArrayList;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.gamekit.GKAchievement;
import org.robovm.apple.gamekit.GKLeaderboard;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.bindings.gamecenter.GameCenterListener;
import org.robovm.bindings.gamecenter.GameCenterManager;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.redbeardstudios.rainfall.Drop;

public class IOSLauncher extends IOSApplication.Delegate implements IOSLeaderboard{
	private IOSApplication iOSApplication;
	private GameCenterManager gcManager;
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationLandscape = true;
        config.orientationPortrait = false;
        IOSAdsController adController = new Ads();
        iOSApplication = new IOSApplication(new Drop(null,null,adController,this), config);
        return iOSApplication;
        
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
    
	@Override
	public void showScores() {
		gcManager.login();
		gcManager.showLeaderboardView("rainfall_leaderboard");
		
	}

	@Override
	public void submitScore(long score) {
		System.out.println("Submit Score");
		gcManager.login();
		gcManager.reportScore("rainfall_leaderboard", score);
		gcManager.showLeaderboardView("rainfall_leaderboard");
		
	}

	@Override
	public void initialize() {
		System.out.println("Initializing");
		gcManager = new GameCenterManager(UIApplication.getSharedApplication().getKeyWindow(),new GameCenterListener(){

			@Override
			public void achievementReportCompleted() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void achievementReportFailed(NSError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void achievementViewDismissed() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void achievementsLoadCompleted(ArrayList<GKAchievement> arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void achievementsLoadFailed(NSError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void achievementsResetCompleted() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void achievementsResetFailed(NSError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void leaderboardViewDismissed() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void leaderboardsLoadCompleted(ArrayList<GKLeaderboard> arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void leaderboardsLoadFailed(NSError arg0) {
				System.out.println("scoresLoadFailed. error: " + arg0);
				
			}

			@Override
			public void playerLoginCompleted() {
				System.out.println("playerLoginCompleted");
				
			}

			@Override
			public void playerLoginFailed(NSError arg0) {
				System.out.println("playerLoginFailed. error: " + arg0);
				
			}

			@Override
			public void scoreReportCompleted() {
				System.out.println("Score Report completed");
				
			}

			@Override
			public void scoreReportFailed(NSError arg0) {
				System.out.println("Score Report Failed" + arg0);
				
			}
			
		});
		
	}
    
}