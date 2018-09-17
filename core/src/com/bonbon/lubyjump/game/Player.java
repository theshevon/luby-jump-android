package com.bonbon.lubyjump.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Mendis on 6/23/18.
 */

public class Player {

    private static final float MAX_VELOCITY = -15;

    private final float INIT_PLAYER_Y;

    private Texture jumpImg;
    private BoundingBox mask;
    private Animation<TextureRegion> runAnimation;
    private Texture runSheet;
    private TextureRegion currRunFrame;

    private float velocity;
    private float centreX, centreY, width, height;
    private boolean inAir = false;
    private float stateTime = 0f;

    public Player(float centreX, float centreY, ScaleFactor factor){

        this.centreX = centreX;
        INIT_PLAYER_Y = centreY;
        this.centreY = INIT_PLAYER_Y;

        try {
            runSheet = new Texture(Gdx.files.internal("run-sheet.png"));
            jumpImg = new Texture(Gdx.files.internal("luby_jump.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
        width = jumpImg.getWidth()*factor.x;
        height = jumpImg.getHeight()*factor.y;
        mask = new BoundingBox(jumpImg, centreX, centreY, width, height);

        initialiseAnimations();
    }

    private void initialiseAnimations(){
        TextureRegion[][] tmp = TextureRegion.split(runSheet, runSheet.getWidth()/4,
                runSheet.getHeight());
        TextureRegion[] runFrames = new TextureRegion[4];

        int index = 0;
        for (int i=0; i<tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                runFrames[index++] = tmp[i][j];
            }
        }

        runAnimation = new Animation<TextureRegion>(125, runFrames);
    }



    public void update(float delta){
        if (getY() > INIT_PLAYER_Y) {
            velocity++;
            float stoppingHeight = INIT_PLAYER_Y;
            if (getY() - velocity < stoppingHeight) {
                setY(stoppingHeight);
            } else {
                setY(getY() - velocity);
            }
        } else {
            inAir = false;
        }

        if (centreY == INIT_PLAYER_Y){
            stateTime += delta;
            currRunFrame = runAnimation.getKeyFrame(stateTime, true);
        }
    }

    public void jump(){

        inAir = true;
        velocity = MAX_VELOCITY;

        if (getY() - velocity > Gdx.graphics.getHeight()){
            setY(Gdx.graphics.getHeight() - jumpImg.getHeight()/2);
        }else {
            setY(getY() - velocity);
        }
    }

    public void jump(float vel){

        inAir = true;
        velocity = vel;

        if (getY() - velocity > Gdx.graphics.getHeight()){
            setY(Gdx.graphics.getHeight() - jumpImg.getHeight()/2);
        }else {
            setY(getY() - velocity);
        }
    }

    public void setX(float centreX){
        this.centreX = centreX;
        mask.setX(centreX);
    }

    public void setY(float centreY){

        if (centreY >= INIT_PLAYER_Y && centreY<= Gdx.graphics.getHeight()-jumpImg.getHeight()/2){
            this.centreY = centreY;
            mask.setY(centreY);
        }
    }

    public float getY(){ return centreY;}

    public float getX(){ return centreX;}

    public BoundingBox getBB(){
        return new BoundingBox(jumpImg, centreX, centreY, width, height);
    }

    public void render(SpriteBatch batch){
        if (centreY == INIT_PLAYER_Y){
            batch.draw(currRunFrame, centreX-width/2, centreY-height/2, width, height);
        }else {
            batch.draw(jumpImg, centreX-width/2, centreY-height/2, width, height);
        }
    }

}


