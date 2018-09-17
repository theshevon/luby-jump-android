package com.bonbon.lubyjump.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mendis on 7/2/18.
 */

public class Poop {

    public static final float POOP_VELOCITY = 0.15f;

    private float x, y, width, height;

    private Texture img;
    private BoundingBox mask;
    private boolean onGround = false;

    public Poop(float birdCentreX, float birdCentreY, ScaleFactor factor){
        img = new Texture(Gdx.files.internal("poop.png"));
        x = birdCentreX;
        y = birdCentreY;
        width = img.getWidth()*factor.x;
        height = img.getHeight()*factor.y;
        mask = new BoundingBox(img, x, y, width, height);
    }

    public void update(float delta, float x){

        this.x = x-img.getWidth()/2;
        mask.setX(x);

        if (!onGround) {
            y -= POOP_VELOCITY * delta;
            mask.setY(y);
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(img, x, y, width, height);
    }

    public boolean isGrounded(){
        return  onGround;
    }

    public boolean collidedWithPlayer(Player player){
        return (player.getBB().intersects(mask));
    }

    public void dispose(){
        img.dispose();
    }
}
