package com.bonbon.lubyjump.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mendis on 7/2/18.
 */

public class Bird {

    private float OFFSET = 350;
    private float BIRD_Y = 1600;

    private Texture img;
    private Poop poop;
    private float x, width, height;

    public Bird(float centreX, ScaleFactor factor){
        OFFSET *= factor.x;
        BIRD_Y *= factor.y;

        img = new Texture(Gdx.files.internal("bird.png"));
        width = img.getWidth()*factor.x;
        height = img.getHeight()*factor.y;

        x = centreX + OFFSET;

        poop = new Poop(x, BIRD_Y, factor);
    }

    public void update(float delta){
        x -= delta*Wall.X_VELOCITY;
        poop.update(delta,  x);
    }

    public void render(SpriteBatch batch){
        poop.render(batch);
        batch.draw(img, x-img.getWidth()/2, BIRD_Y, width, height);
    }

    public float getX(){
        return x;
    }

    public boolean hasLeftScreen(){ return x <= -img.getWidth()/2;
    }

    public boolean collidedWithPoop(Player player){
        return poop.collidedWithPlayer(player);
    }

    public void dispose(){
        img.dispose();
        poop.dispose();
    }
}
