package com.bonbon.lubyjump.game;

/**
 * Created by Mendis on 6/24/18.
 */

public class UpDownWall extends Wall {

    public static float Y_VELOCITY = 0.3f;
    public static float MAX_Y_CHANGE = 300;

    private final float INIT_Y;
    private boolean goingUp = true;

    public UpDownWall(float centreY, ScaleFactor factor){
        super(centreY, factor);
        INIT_Y = getTopY();
    }

    public void update(float delta) {

        super.update(delta);

        if (goingUp) {
            setTopY(getTopY() + delta * Y_VELOCITY);
            setBottomY(getBottomY() + delta * Y_VELOCITY);
            if (getTopY() - INIT_Y >= MAX_Y_CHANGE) {
                goingUp = false;
            }
        } else {
            setTopY(getTopY() - delta * Y_VELOCITY);
            setBottomY(getBottomY() - delta * Y_VELOCITY);
            if ((INIT_Y - getTopY()) >= MAX_Y_CHANGE) {
                goingUp = true;
            }
        }

    }
}
