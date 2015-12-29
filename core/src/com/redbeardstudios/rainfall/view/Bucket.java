package com.redbeardstudios.rainfall.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Adam on 2/12/2015.
 */
public class Bucket {

    private int SPEED = 200;
    private int width = 64;
    private int height = 64;
    private int screenWidth;
    private Texture image;
    private Rectangle rect;

    public Rectangle getPosition(){return rect;}

    public Bucket(int screenWidth, int posX, Texture image, boolean big, boolean small){

        this.screenWidth = screenWidth;
        this.image = image;

        if(big){
            width = 128;
            height = 128;
        }

        else if(small){
            width = 32;
            height = 32;
        }

        rect = new Rectangle();
        if(posX < 0){
            rect.x = screenWidth/2 - width/2;
        }
        else{
            rect.x = posX - width/2;
        }
        rect.y = 20;
        rect.width = width;
        rect.height = height;
    }

    public void setPositonX(float x){

        rect.x = x - width/2;

        keepOnScreen();
    }

    public void moveX(float speedRatio, float delta){
        rect.x += speedRatio * SPEED * delta;

        keepOnScreen();
    }

    private void keepOnScreen() {
        if(rect.x < 0){
            rect.x = 0;
        }
        else if((rect.x + width) > screenWidth){
            rect.x = screenWidth - width;
        }
    }

    public Texture getImage(){return image;}

    public void draw(SpriteBatch batch){
        batch.draw(image,rect.x,rect.y);
    }

}
