package com.bonbon.lubyjump.game;

/**
 * Created by Mendis on 6/25/18.
 */

public class UWall extends Wall {

    public static final float MAX_Y_CHANGE = 500;

    private final float INIT_BOTTOM_Y;

    private boolean goRight = true;
    private int posReached = 0;

    public UWall(float centreY, ScaleFactor factor){
        super(centreY, factor);

        INIT_BOTTOM_Y = getBottomY();
        setBottomY(INIT_BOTTOM_Y + MAX_Y_CHANGE);
        setBottomX(getBottomX() - getWidth());
    }

    public void update(float delta){
        super.update(delta);

        if (goRight){
            moveBottom(1, delta);
        }else{
            moveBottom(-1, delta);
        }
    }

    private void moveBottom(int i, float delta) {

        switch (posReached) {
            case 0:
                setBottomY(getBottomY() - delta * X_VELOCITY);
                if (getBottomY() - INIT_BOTTOM_Y <= 0) {
                    posReached = 1;
                }
                break;
            case 1:
                setBottomX(getBottomX() + i * delta * X_VELOCITY);
                if (i*(getBottomX() - getCentreX()) > getWidth()){
                    setBottomX(getCentreX() + i * getWidth());
                    posReached = 2;
                }
                break;
            case 2:
                setBottomY(getBottomY() + delta * X_VELOCITY);
                if (getBottomY() - INIT_BOTTOM_Y >= MAX_Y_CHANGE) {
                    posReached = 0;
                    if (goRight) {
                        goRight = false;
                    } else {
                        goRight = true;
                    }
                }
                break;
        }
    }

}
