package com.bonbon.lubyjump.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mendis on 6/23/18.
 */

public class Wall {

    public static final float X_VELOCITY = 0.33f;

    private Texture top, bottom;
    private BoundingBox topBB, bottomBB;
    private ScoreNode node;

    private float GAP = 400f;
    private float centreX = Gdx.graphics.getWidth()+100;
    private float topX, topY, bottomX, bottomY;
    private float width, height;

    public Wall(float centreY, ScaleFactor factor){

        GAP *= factor.y;

        top = new Texture(Gdx.files.internal("topWall.png"));
        bottom = new Texture(Gdx.files.internal("bottomWall.png"));
        width = top.getWidth()*factor.x;
        height = bottom.getHeight()*factor.y;

        topX = bottomX = centreX;
        topY = centreY + GAP/2 + height/2;
        bottomY = centreY - (GAP/2 + height/2);

        topBB = new BoundingBox(top, topX, topY, width, height);
        bottomBB = new BoundingBox(bottom, topY, bottomY, width, height);

        node = new ScoreNode(factor);

    }

    public void setTopX(float x){
        this.topX = x;
        topBB.setX(x);
    }

    public void setBottomX(float x){
        this.bottomX = x;
        bottomBB.setX(x);
    }

    public void setTopY(float y){
        this.topY = y;
        topBB.setY(y);
    }

    public void setBottomY(float y){
        this.bottomY = y;
        bottomBB.setY(y);
    }

    public float getCentreX(){
        return centreX;
    }

    public float getTopX(){ return topX;}

    public float getTopY(){
        return topY;
    }

    public float getBottomX(){ return bottomX; }

    public float getBottomY(){
        return bottomY;
    }

    public float getHalfHeight(){
        return height;
    }
    public float getWidth(){ return width;}

    public void update(float delta){

        centreX -= X_VELOCITY * delta;
        node.setX(centreX);
        setTopX(getTopX() - X_VELOCITY * delta);
        setBottomX(getBottomX() - X_VELOCITY * delta);
    }

    public void render(SpriteBatch batch){
        batch.draw(top, topX - width/2, topY - height/2, width, height);
        batch.draw(bottom, bottomX - width/2, bottomY - height/2, width, height);
    }

    public boolean hasLeftScreen(){
        return (topX <= -width) && (bottomX <= -width);
    }

    public boolean collidedWithWall(Player player){
        return (topBB.intersects(player.getBB()) || bottomBB.intersects(player.getBB()));
    }

    public boolean collidedWithScoreNode(Player player){
        return node.isActive() && node.collidedWithPlayer(player);
    }

    public void dispose(){
        top.dispose();
        bottom.dispose();
    }
}
