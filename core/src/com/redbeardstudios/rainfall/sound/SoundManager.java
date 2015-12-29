package com.redbeardstudios.rainfall.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Adam on 2/12/2015.
 */
public class SoundManager {

    private boolean isSoundOn;

    private Sound dropSound;

    public void setSoundOn(boolean isSoundOn){this.isSoundOn = isSoundOn;}

    public boolean isSoundOn(){return isSoundOn;}

    public void load(){
        dropSound = Gdx.audio.newSound(Gdx.files.internal("sounds/drop.wav"));
    }

    public void dispose(){
        dropSound.dispose();
    }

    public void playDropSound(){
        if(isSoundOn){
            dropSound.play();
        }
    }
}
