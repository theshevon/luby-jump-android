package com.bonbon.lubyjump.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mendis on 7/17/18.
 */

public class Trophy {
    private Texture lossTrophy, winTrophy;
    private final float width, height, x, y;

    public Trophy(ScaleFactor factor, float bottomY){
        winTrophy = new Texture("trophy.png");
        lossTrophy = new Texture("empty_trophy.png");
        width = lossTrophy.getWidth()*factor.x;
        height = lossTrophy.getHeight()*factor.y;
        x = Gdx.graphics.getWidth()/2-width/2;
        y = bottomY;
    }

    public void render(SpriteBatch batch, boolean newHS){
        if (newHS){
            batch.draw(winTrophy, x, y, width, height);
        }else{
            batch.draw(lossTrophy, x, y, width, height);
        }
    }
}
