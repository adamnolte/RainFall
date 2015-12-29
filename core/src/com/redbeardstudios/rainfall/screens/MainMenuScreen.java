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
import com.redbeardstudios.rainfall.Drop;
import com.redbeardstudios.rainfall.images.ImageProvider;
import com.redbeardstudios.rainfall.view.Button;

/**
 * Created by Adam on 2/8/2015.
 */
public class MainMenuScreen implements Screen, InputProcessor {

    final Drop game;
    
    private Button startButton;
    private Button scoreButton;
    private ImageProvider imageProvider;
    private SpriteBatch batch;
    private Texture background;
    private Texture logo;
    private Button soundOn;
    private Button soundOff;
    private Button help;
    private int logoY;
    private int logoX;
    private int buttonX;
    private int startY;
    private int scoreY;
    private int soundX;
    private int helpX;
    private int BUTTON_SPACING = 25;

    OrthographicCamera camera;

    public MainMenuScreen(final Drop game){
        super();
        this.game = game;
    }

    @Override
    public void show() {
        batch = game.getBatch();
        imageProvider = game.getImageProvider();
        startButton = new Button(imageProvider.getStartButton());
        scoreButton = new Button(imageProvider.getScoreButton());
        background = imageProvider.getBackground();
        logo = imageProvider.getLogo();
        soundOff = new Button(imageProvider.getSoundOff());
        soundOn = new Button(imageProvider.getSoundOn());
        help = new Button(imageProvider.getHelp());

        camera = new OrthographicCamera();
        camera.setToOrtho(false,imageProvider.getScreenWidth(),imageProvider.getScreenHeight());

        logoX = (imageProvider.getScreenWidth() - logo.getWidth())/2;
        logoY = ((imageProvider.getScreenHeight()-10) - logo.getHeight());
        buttonX = (imageProvider.getScreenWidth() - startButton.getWidth())/2;
        startY = logoY - startButton.getHeight() - 75;
        scoreY = startY - scoreButton.getHeight() - BUTTON_SPACING;

        soundX = imageProvider.getScreenWidth() - soundOn.getWidth();
        helpX = soundX - help.getWidth() - 5;

        startButton.setPos(buttonX,startY);
        scoreButton.setPos(buttonX,scoreY);
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
        startButton.draw(batch);
        scoreButton.draw(batch);
        if(game.getSoundManager().isSoundOn()){
            soundOn.draw(batch);
        }
        else{
            soundOff.draw(batch);
        }
/*        help.draw(batch);*/
        batch.draw(logo,logoX,logoY);
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
        if(this.startButton.isPressed(touchPos)) {
            game.setScreen(new GameScreen(game));
            return true;
        }

        else if(this.scoreButton.isPressed(touchPos)){
            game.getiGoogleServices().showScores();
            game.getIOSLeaderboard().showScores();
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

        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
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
