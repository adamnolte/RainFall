package com.redbeardstudios.rainfall.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


/**
 * Created by Adam on 2/12/2015.
 */
public class ImageProvider {

    private int SCREEN_WIDTH = 800;
    private int SCREEN_HEIGHT = 480;

    private Texture bucketImage;
    private Texture dropImage;
    private Texture startButton;
    private Texture background;
    private Texture logo;
    private Texture heart;
    private Texture timesTwoImage;
    private Texture bImage;
    private Texture sImage;
    private Texture bigBucketImage;
    private Texture smallBucketImage;
    private Texture scoreButton;
    private Texture help;
    private Texture soundOn;
    private Texture soundOff;
    private Texture gameOver;
    private Texture restartButton;
    private Texture menuButton;
    private Texture submitButton;

    public void load(){

        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        startButton = new Texture(Gdx.files.internal("start-button.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        background = new Texture(Gdx.files.internal("tropical-forest.png"));
        logo = new Texture(Gdx.files.internal("logo.png"));
        heart = new Texture(Gdx.files.internal("heart.png"));
        timesTwoImage = new Texture(Gdx.files.internal("times-two.png"));
        bImage = new Texture(Gdx.files.internal("b.png"));
        sImage = new Texture(Gdx.files.internal("s.png"));
        bigBucketImage = new Texture(Gdx.files.internal("big-bucket.png"));
        smallBucketImage = new Texture(Gdx.files.internal("small-bucket.png"));
        scoreButton = new Texture(Gdx.files.internal("score-button.png"));
        help = new Texture(Gdx.files.internal("help.png"));
        soundOn = new Texture(Gdx.files.internal("sound_on.png"));
        soundOff = new Texture(Gdx.files.internal("sound_off.png"));
        gameOver = new Texture(Gdx.files.internal("game-over.png"));
        menuButton = new Texture(Gdx.files.internal("menu-button.png"));
        restartButton = new Texture(Gdx.files.internal("restart-button.png"));
        submitButton = new Texture(Gdx.files.internal("submit-button.png"));

    }

    public Texture getSoundOff() {
        return soundOff;
    }

    public Texture getSoundOn() {
        return soundOn;
    }

    public Texture getHelp() {
        return help;
    }

    public Texture getScoreButton() {
        return scoreButton;
    }


    public Texture getBigBucketImage() {
        return bigBucketImage;
    }

    public Texture getSmallBucketImage() {
        return smallBucketImage;
    }

    public Texture getDropImage() {
        return dropImage;
    }

    public Texture getStartButton() {
        return startButton;
    }

    public Texture getBucketImage(){
        return bucketImage;
    }

    public Texture getBackground(){ return background;}

    public Texture getLogo(){ return logo;}

    public Texture getHeart(){ return heart;}

    public Texture getTimesTwoImage() {
        return timesTwoImage;
    }

    public Texture getBImage() {
        return bImage;
    }

    public Texture getSImage() {
        return sImage;
    }

    public Texture getGameOver() {
        return gameOver;
    }

    public Texture getMenuButton() {
        return menuButton;
    }

    public Texture getRestartButton() {
        return restartButton;
    }

    public Texture getSubmitButton(){ return submitButton;}

    public int getScreenWidth(){return SCREEN_WIDTH;}
    public int getScreenHeight(){return SCREEN_HEIGHT;}

    public void dispose(){
        dropImage.dispose();
        startButton.dispose();
        bucketImage.dispose();
        background.dispose();
        heart.dispose();
        logo.dispose();
        timesTwoImage.dispose();
        bImage.dispose();
        sImage.dispose();
        scoreButton.dispose();
        gameOver.dispose();
        menuButton.dispose();
        restartButton.dispose();
        soundOn.dispose();
        soundOff.dispose();
        help.dispose();
        submitButton.dispose();
    }
}
