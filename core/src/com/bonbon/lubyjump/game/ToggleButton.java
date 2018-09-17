package com.bonbon.lubyjump.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mendis on 7/5/18.
 */

public class ToggleButton extends Button {

    private Texture img2;
    private int imageToDisplay = 1;

    public ToggleButton(String image1Src, String image2Src, float centreX, float centreY, ScaleFactor factor) {
        super(image1Src, centreX, centreY, factor);

        try {
            img2 = new Texture(Gdx.files.internal(image2Src));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPressed(float x, float y) {
        boolean isPressed = super.isPressed(x, y);
        if (isPressed) {
            if (imageToDisplay == 1) {
                imageToDisplay = 2;
            } else {
                imageToDisplay = 1;
            }
        }
        return isPressed;
    }

    public void render(SpriteBatch batch) {
        if (imageToDisplay == 1) {
            super.render(batch);
        } else {
            batch.draw(img2, getLeft(), getBottom(), getWidth(), getHeight());
        }
    }
}