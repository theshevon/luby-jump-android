package com.bonbon.lubyjump.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mendis on 6/30/18.
 */

public class ScoreNode {

    private float x, width, height;
    private Texture img;
    private boolean active = true;
    private BoundingBox mask;

    public ScoreNode(ScaleFactor factor){
        img = new Texture("node.png");
        width = img.getWidth()*factor.x;
        height = img.getHeight()*factor.y;
        mask = new BoundingBox(img, Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2, width, height);
    }

    public boolean isActive(){
        return active;
    }

    public boolean collidedWithPlayer(Player player){
        if (mask.intersects(player.getBB())){
            active = false;
            return true;
        }
        return false;
    }

    public void setX(float x){
        this.x = x;
        mask.setX(x);
    }

    /**
     * Draws the score node onto the screen. Only used for testing purposes
     * @param batch sprite batch used by the app's manager class
     */
    public void render(SpriteBatch batch){
        batch.draw(img, x, 0, width, height);
    }
}
