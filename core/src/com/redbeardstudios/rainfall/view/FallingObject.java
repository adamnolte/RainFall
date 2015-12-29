package com.redbeardstudios.rainfall.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Adam on 2/13/2015.
 */
public class FallingObject {

    private String type;

    private int DROP_SPEED = 200;

    private int width = 64;

    private int height = 64;

    private Rectangle rect;

    private Texture image;

    public Rectangle getPosition(){return rect;}

    public Texture getImage(){return image;}

    public FallingObject(Texture image, String type){
        this.image = image;
        this.type = type;

        rect = new Rectangle();

        rect.x = MathUtils.random(0, 800 - 64);
        rect.y = 480;
        rect.width = width;
        rect.height = height;
    }

    public void moveDown(float delta,int dropsGathered){

        rect.y -= (DROP_SPEED + (dropsGathered*2)) * delta;
    }

    public void draw(SpriteBatch batch){
        batch.draw(image, rect.x, rect.y);
    }

    public boolean isBottomOfScreen(){ return rect.y < 0;}

    public boolean isOverlapping(Rectangle otherRect){ return rect.overlaps(otherRect);}

    public String getType(){ return type;}

}
