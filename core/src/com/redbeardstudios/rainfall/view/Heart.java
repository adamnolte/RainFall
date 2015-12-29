package com.redbeardstudios.rainfall.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Adam on 2/15/2015.
 */
public class Heart {

    private int width = 48;

    private int height = 48;

    private int screenHeight;

    private Rectangle rect;

    private Texture image;

    public Rectangle getPosition(){return rect;}

    public Texture getImage(){return image;}

    public Heart(Texture image, int posX, int screenHeight){
        this.image = image;

        rect = new Rectangle();

        rect.x = posX;

        rect.y = screenHeight - height;
    }

    public void draw(SpriteBatch batch){
        batch.draw(image, rect.x, rect.y);
    }
}
