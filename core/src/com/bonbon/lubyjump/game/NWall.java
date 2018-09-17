package com.bonbon.lubyjump.game;

/**
 * Created by Mendis on 6/25/18.
 */

public class NWall extends Wall {

    public static final float MAX_Y_CHANGE = 500;

    private final float INIT_TOP_Y;

    private boolean goRight = true;
    private int posReached = 0;

    public NWall(float centreY, ScaleFactor factor){
        super(centreY, factor);

        INIT_TOP_Y = getTopY();
        setTopY(INIT_TOP_Y - MAX_Y_CHANGE);
        setTopX(getTopX()-getWidth());
    }

    public void update(float delta){
        super.update(delta);

        if (goRight){
            moveTop(1, delta);
        }else{
            moveTop(-1, delta);
        }
    }

    private void moveTop(int i, float delta){
        switch (posReached){
            case 0:
                setTopY(getTopY() + delta*X_VELOCITY);
                if (getTopY() - INIT_TOP_Y >= 0){
                    posReached = 1;
                }
                break;
            case 1:
                setTopX(getTopX() + i*delta*X_VELOCITY);
                if (i*(getTopX() - getCentreX()) > getWidth()){
                    setTopX(getCentreX() + i * getWidth());
                    posReached = 2;
                }
                break;
            case 2:
                setTopY(getTopY() - delta*X_VELOCITY);
                if (INIT_TOP_Y - getTopY() >= MAX_Y_CHANGE){
                    posReached = 0;
                    if (goRight){
                        goRight = false;
                    }else{
                        goRight = true;
                    }
                }
        }
    }
}
