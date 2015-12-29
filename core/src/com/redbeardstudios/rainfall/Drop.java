package com.redbeardstudios.rainfall;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.redbeardstudios.rainfall.images.ImageProvider;
import com.redbeardstudios.rainfall.screens.MainMenuScreen;
import com.redbeardstudios.rainfall.sound.SoundManager;

/**
 * Created by Adam on 2/8/2015.
 */
public class Drop extends Game {

    private SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont endScoreFont;
    public BitmapFont pauseFont;
    private ImageProvider imageProvider;
    private SoundManager soundManager;
    private AdsController adsController;
    private IGoogleServices iGoogleServices;
    private IOSAdsController iOSAdsController;
    private IOSLeaderboard iOSLeaderboard;

    public Drop(AdsController adsController, IGoogleServices iGoogleServices, IOSAdsController iOSAdsController, IOSLeaderboard iOSLeaderboard){
        if(adsController != null) {
            this.adsController = adsController;
            this.iGoogleServices = iGoogleServices;
        }
        else{
            this.adsController = new DummyController();
            this.iGoogleServices = new DummyController();
        }
        if(iOSAdsController != null){
        	this.iOSAdsController = iOSAdsController;
        	this.iOSAdsController.initializeAds();
        	this.iOSLeaderboard = iOSLeaderboard;
        }
        else{
        	this.iOSAdsController = new IOSDummy();
        	this.iOSLeaderboard = new IOSDummy();
        }
    }

    public void create(){

        //Load Images
        imageProvider = new ImageProvider();
        imageProvider.load();

        //Load Sounds
        soundManager = new SoundManager();
        soundManager.load();
        soundManager.setSoundOn(true);

        //Create Sprite Batch
        batch = new SpriteBatch();

        //Use Arial font
        font = new BitmapFont(Gdx.files.internal("scoreFont.fnt"));
        endScoreFont = new BitmapFont(Gdx.files.internal("endScoreFont.fnt"));
        pauseFont = new BitmapFont(Gdx.files.internal("pauseFont.fnt"));
        
        this.iOSLeaderboard.initialize();

        //Start Menu Screen
        this.setScreen(new MainMenuScreen(this));
    }

    public void render(){
        super.render();
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
        soundManager.dispose();
        imageProvider.dispose();
    }

    public ImageProvider getImageProvider(){return imageProvider;}

    public SoundManager getSoundManager(){return soundManager;}

    public SpriteBatch getBatch() {
        return batch;
    }

    public AdsController getAdsController(){ return adsController;}

    public IGoogleServices getiGoogleServices(){ return iGoogleServices;}
    
    public IOSAdsController getIOSAdsController(){ return iOSAdsController;}
    
    public IOSLeaderboard getIOSLeaderboard(){ return iOSLeaderboard;}
}
