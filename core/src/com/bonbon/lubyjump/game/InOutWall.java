package com.bonbon.lubyjump.game;

/**
 * Created by Mendis on 6/23/18.
 */

public class InOutWall extends Wall {

    public static float MIN_IN_OUT_VELOCTIY = 0.3f;
    public static float MAX_VEL_CHANGE= 0.4f;

    private float MAX_GAP = 600;
    private float MIN_GAP = 350;
    private float velocity;
    private boolean movingIn = false;

    public InOutWall(float centreY, float velocity, ScaleFactor factor){
        super(centreY, factor);
        this.velocity = velocity;
        MAX_GAP *= factor.y;
        MIN_GAP *= factor.y;
    }

    public void update(float delta){
        super.update(delta);

        float gapSize = getTopY() - (getHalfHeight()+getBottomY());
        if (!movingIn){
            setTopY(getTopY() + delta*velocity);
            setBottomY(getBottomY() - delta*velocity);
            if (gapSize >= MAX_GAP){
                movingIn = true;
            }
        }else{
            setTopY(getTopY() - delta*velocity);
            setBottomY(getBottomY() + delta*velocity);
            if (gapSize <= MIN_GAP){
                movingIn = false;
            }
        }
    }
}
