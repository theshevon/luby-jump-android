package com.bonbon.lubyjump.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by Mendis on 7/17/18.
 */

public class ScaleFactor {
    private static float DEFAULT_SCREEN_HEIGHT = 1920;
    private static float DEFAULT_SCREEN_WIDTH = 1080;

    public final float x, y;

    public ScaleFactor(){
        x = Gdx.graphics.getWidth() / DEFAULT_SCREEN_WIDTH;
        y = Gdx.graphics.getHeight() / DEFAULT_SCREEN_HEIGHT;
    }
}
