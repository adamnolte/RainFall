package com.redbeardstudios.rainfall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.redbeardstudios.rainfall.Drop;
import com.redbeardstudios.rainfall.images.ImageProvider;
import com.redbeardstudios.rainfall.sound.SoundManager;
import com.redbeardstudios.rainfall.view.Bucket;
import com.redbeardstudios.rainfall.view.FallingObject;
import com.redbeardstudios.rainfall.view.Heart;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by Adam on 2/8/2015.
 */
public class GameScreen implements Screen, InputProcessor {
    final Drop game;

    private SpriteBatch batch;
    private int screenWidth;
    ImageProvider imageProvider;
    SoundManager soundManager;
    Texture dropImage;
    Texture heart;
    Texture timesTwoImage;
    Texture bImage;
    Texture sImage;
    OrthographicCamera camera;
    Bucket bucket;
    Bucket smallBucket;
    Bucket bigBucket;
    Bucket regBucket;
    Array<FallingObject> fallingObjects;
    Array<Heart> hearts;
    Random rand;
    long lastDropTime;
    long powerupTime;
    int dropsGathered;
    boolean bigBucketFlag;
    boolean smallBucketFlag;
    boolean timesTwoFlag;
    boolean gameStarted;

    public GameScreen(final Drop game) {
        this.game = game;
    }

    private void initLives(){
        for(int i = 1; i <= 3; ++i){
            hearts.add(new Heart(heart, screenWidth - (i*48), imageProvider.getScreenHeight()));
        }
    }

    private void spawnFallingObject() {
        int prob = rand.nextInt(100);

        //85% chance raindrop
        if (prob <= 85 || bigBucketFlag || smallBucketFlag || timesTwoFlag) {
            spawnRaindrop();
        }

        //5% X2
        else if (prob > 85 && prob <= 90) {
            spawnTimesTwo();
        }

        //5% Big Bucket
        else if (prob > 90 && prob <= 95){
            spawnBigB();
        }

        //5% Small Bucket
        else{
            spawnSmallB();
        }
    }

    private void spawnTimesTwo(){
        FallingObject timesTwo = new FallingObject(timesTwoImage,"timesTwo");
        fallingObjects.add(timesTwo);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void spawnBigB(){
        FallingObject b = new FallingObject(bImage,"bigB");
        fallingObjects.add(b);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void spawnSmallB(){
        FallingObject s = new FallingObject(sImage,"smallB");
        fallingObjects.add(s);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void spawnRaindrop() {
        FallingObject raindrop = new FallingObject(dropImage,"raindrop");
        fallingObjects.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void updatePowerUp(){
        if((TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - powerupTime) > 10000){
            timesTwoFlag = false;
            bigBucketFlag = false;
            smallBucketFlag = false;
            regBucket.getPosition().x = bucket.getPosition().x;
            bucket = regBucket;
        }
    }

    private void deductLife(){
        hearts.removeIndex(hearts.size - 1);
        if(hearts.size == 0) {
            game.setScreen(new EndScreen(game, dropsGathered));
        }
    }

    @Override
    public void show() {

        rand = new Random();

        // load the drop sound effect and the rain background "music"
        soundManager = game.getSoundManager();

        //Get the SpriteBatch for game
        batch = game.getBatch();

        //Initialize the image Provider
        imageProvider = game.getImageProvider();

        screenWidth = imageProvider.getScreenWidth();

        //Load Power up Images, Raindrop, and heart
        timesTwoImage = imageProvider.getTimesTwoImage();
        sImage = imageProvider.getSImage();
        bImage = imageProvider.getBImage();
        dropImage = imageProvider.getDropImage();
        heart = imageProvider.getHeart();

        //Load different buckets
        regBucket = new Bucket(screenWidth,screenWidth/2,imageProvider.getBucketImage(),false,false);
        smallBucket = new Bucket(screenWidth,screenWidth/2,imageProvider.getSmallBucketImage(),false,true);
        bigBucket = new Bucket(screenWidth,screenWidth/2,imageProvider.getBigBucketImage(),true,false);
        bucket = regBucket;

        // create the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,screenWidth,imageProvider.getScreenHeight());

        // create the raindrops array and spawn the first raindrop
        fallingObjects = new Array<FallingObject>();

        //Create hearts for life
        hearts = new Array<Heart>();
        initLives();

        gameStarted = false;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        batch.begin();

        batch.draw(imageProvider.getBackground(), 0, 0);
        game.font.draw(batch, "" + dropsGathered, 0, 480);
        bucket.draw(batch);
        for (FallingObject fallingObject : fallingObjects) {
            fallingObject.draw(batch);
        }
        for (Heart heart1 : hearts) {
            heart1.draw(batch);
        }

        if(!gameStarted){
            game.pauseFont.draw(batch,"Touch Screen to Start",120,imageProvider.getScreenHeight()/2);
        }
        batch.end();



        // make sure the bucket stays within the screen bounds
        if(gameStarted) {
            processInput();

            //update if power up needs to be disabled
            updatePowerUp();
            // check if we need to create a new falling object
            if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
                spawnFallingObject();

            // move the raindrops, remove any that are beneath the bottom edge of
            // the screen or that hit the bucket. In the later case we increase the
            // value our drops counter and add a sound effect.
            try {
                Iterator<FallingObject> iter = fallingObjects.iterator();
                while (iter.hasNext()) {
                    FallingObject fallingObject = iter.next();
                    fallingObject.moveDown(delta, dropsGathered);
                    if (fallingObject.isBottomOfScreen()) {
                        if (fallingObject.getType().equalsIgnoreCase("raindrop"))
                            deductLife();
                        iter.remove();

                    }
                    if (fallingObject.isOverlapping(bucket.getPosition())) {
                        if (fallingObject.getType().equalsIgnoreCase("raindrop")) {
                            dropsGathered++;
                            if (timesTwoFlag) {
                                dropsGathered++;
                            }
                        } else if (fallingObject.getType().equalsIgnoreCase("timesTwo")) {
                            timesTwoFlag = true;
                            powerupTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
                        } else if (fallingObject.getType().equalsIgnoreCase("bigB")) {
                            bigBucketFlag = true;
                            bigBucket.setPositonX(bucket.getPosition().x);
                            bucket = bigBucket;
                            powerupTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
                        } else {
                            smallBucketFlag = true;
                            smallBucket.setPositonX(bucket.getPosition().x);
                            bucket = smallBucket;
                            powerupTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
                        }

                        soundManager.playDropSound();
                        iter.remove();
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
    }

    private void processInput(){
        float delta = Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            bucket.moveX(-1,delta * 2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            bucket.moveX(1,delta * 2);
        }
        if(Gdx.input.getRotation() == 270) {
            bucket.moveX(Gdx.input.getAccelerometerY() * -1, delta);
        }
        else{
            bucket.moveX(Gdx.input.getAccelerometerY(),delta);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
        gameStarted = false;
    }

    @Override
    public void pause() {
        gameStarted = false;
    }

    @Override
    public void resume() {
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

        gameStarted = true;

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
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
