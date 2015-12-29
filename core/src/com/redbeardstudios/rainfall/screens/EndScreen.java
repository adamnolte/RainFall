package com.redbeardstudios.rainfall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.redbeardstudios.rainfall.AdsController;
import com.redbeardstudios.rainfall.Drop;
import com.redbeardstudios.rainfall.images.ImageProvider;
import com.redbeardstudios.rainfall.view.Button;

/**
 * Created by Adam on 2/13/2015.
 */
public class EndScreen implements Screen, InputProcessor {

    final Drop game;
    private SpriteBatch batch;
    private ImageProvider imageProvider;
    private AdsController adsController;
    private Button restartButton;
    private Button menuButton;
    private Button soundOn;
    private Button soundOff;
    private Button help;
    private Button submitButton;
    private Texture background;
    private Texture gameOver;
    private int lastScore;
    private int soundX;
    private int helpX;
    private int buttonX;
    private int menuY;
    private int restartY;
    private int gameOverY;
    private int gameOverX;
    private int scoreY;
    private int scoreX;
    private int submitY;
    private int BUTTON_SPACING = 15;

    OrthographicCamera camera;

    public EndScreen(final Drop game,int lastScore){
        super();
        this.game = game;
        this.lastScore = lastScore;
    }

    @Override
    public void show() {
        batch = game.getBatch();
        imageProvider = game.getImageProvider();
        adsController = game.getAdsController();


        background = imageProvider.getBackground();
        gameOver = imageProvider.getGameOver();
        soundOff = new Button(imageProvider.getSoundOff());
        soundOn = new Button(imageProvider.getSoundOn());
        help = new Button(imageProvider.getHelp());
        menuButton = new Button(imageProvider.getMenuButton());
        restartButton = new Button(imageProvider.getRestartButton());
        submitButton = new Button(imageProvider.getSubmitButton());


        camera = new OrthographicCamera();
        camera.setToOrtho(false,imageProvider.getScreenWidth(),imageProvider.getScreenHeight());

        gameOverX = (imageProvider.getScreenWidth() - gameOver.getWidth())/2;
        gameOverY = ((imageProvider.getScreenHeight()-10) - gameOver.getHeight());
        buttonX = (imageProvider.getScreenWidth() - restartButton.getWidth())/2;
        scoreY = gameOverY - 2;
        submitY = scoreY - submitButton.getHeight() - 75;
        restartY = submitY - restartButton.getHeight() - BUTTON_SPACING;
        menuY = restartY - menuButton.getHeight() - BUTTON_SPACING;

        if(lastScore < 10){
            scoreX = buttonX - 15;
        }
        else if(lastScore < 100){
            scoreX = buttonX - 27;
        }
        else{
            scoreX = buttonX - 43;
        }

        soundX = imageProvider.getScreenWidth() - soundOn.getWidth();
        helpX = soundX - help.getWidth() - 5;

        menuButton.setPos(buttonX,menuY);
        restartButton.setPos(buttonX,restartY);
        submitButton.setPos(buttonX,submitY);
        soundOn.setPos(soundX,0);
        soundOff.setPos(soundX,0);
        help.setPos(helpX,0);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background,0,0);
        game.endScoreFont.draw(batch,"SCORE: " + lastScore,scoreX,scoreY);
        if(game.getSoundManager().isSoundOn()){
            soundOn.draw(batch);
        }
        else{
            soundOff.draw(batch);
        }
/*        help.draw(batch);*/
        batch.draw(gameOver,gameOverX,gameOverY);
        restartButton.draw(batch);
        menuButton.draw(batch);
        submitButton.draw(batch);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            Gdx.app.exit();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3();
        touchPos.set(screenX,screenY,0);
        camera.unproject(touchPos);
        if(this.restartButton.isPressed(touchPos)) {
            if(adsController.isConnected()){
                adsController.showInterstitialAd(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
            game.getIOSAdsController().showAd();
            game.setScreen(new GameScreen(game));
            return true;
        }
        else if(this.menuButton.isPressed(touchPos)){
            if(adsController.isConnected()){
                adsController.showInterstitialAd(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
            game.getIOSAdsController().showAd();
            game.setScreen(new MainMenuScreen(game));
            return true;
        }
        else if(this.soundOn.isPressed(touchPos)){
            if(game.getSoundManager().isSoundOn())
                game.getSoundManager().setSoundOn(false);
            else
                game.getSoundManager().setSoundOn(true);
        }
        else if(this.help.isPressed(touchPos)){
            return true;
        }
        else if(this.submitButton.isPressed(touchPos)){
            game.getiGoogleServices().submitScore(lastScore);
            game.getIOSLeaderboard().submitScore(lastScore);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
