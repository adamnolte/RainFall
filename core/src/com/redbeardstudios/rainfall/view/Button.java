package com.redbeardstudios.rainfall.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Adam on 2/12/2015.
 */
public class Button {

    private Texture image;

    private Texture suffix = null;

    private int margin = 0;

    private float startX;

    private float endX;

    private float startY;

    private float endY;

    private float textX;

    private float textY;

    private float suffixX;

    private float suffixY;

    private static long timePressed = 0;


    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public Texture getImage(){return image;}

    public Button(Texture image){
        this.image = image;
    }

    public boolean isPressed(Vector3 touchPos){
        float x = touchPos.x;
        float y = touchPos.y;

        if(x > startX && x < endX && y > startY && y < endY){
            long now = TimeUtils.millis();
            if(now - timePressed > 200){
                timePressed = TimeUtils.millis();
                return true;
            }
        }

        return false;
    }

    public void setPos(float x, float y){
        startX = x;
        startY = y;

        endX = x + getWidth();
        endY = y + getHeight();
    }

    public void draw(SpriteBatch batch){
        batch.draw(image,startX,startY);
    }

    public int getWidth(){return image.getWidth();}
    public int getHeight(){return image.getHeight();}

}
