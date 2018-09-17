package com.bonbon.lubyjump.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mendis on 7/1/18.
 */

public class Button {

    private Texture img;
    private float top, bottom, left, right, width, height;

    public Button(String imageSrc, float centreX, float centreY, ScaleFactor factor){

        try{
            img = new Texture(Gdx.files.internal(imageSrc));
        }catch (Exception e){
            e.printStackTrace();
        }

        width = img.getWidth()*factor.x;
        height = img.getHeight()*factor.y;

        top = centreY + height/2;
        bottom = centreY - height/2;
        left = centreX - width/2;
        right = centreX + width/2;
    }

    public boolean isPressed(float x, float y){
        y = Gdx.graphics.getHeight() - y;
        return (x <= right && x>=left && y>=bottom && y<=top);
    }

    public float getLeft(){ return  left;}

    public float getBottom(){ return  bottom;}

    public float getWidth(){ return  width;}

    public  float getHeight(){ return height;}

    public void render(SpriteBatch batch){
        batch.draw(img, left, bottom, width, height);
    }
}
