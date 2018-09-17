package com.bonbon.lubyjump.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * BoundingBox complete class for SWEN20003: Object Oriented Software Development 2018
 * Written by Eleanor McMurtry, University of Melbourne
 * Modified by Shevon Mendis
 */

public class BoundingBox {

    private float left;
    private float top;
    private float width;
    private float height;
    private Texture img;

    public BoundingBox(Texture img, float x, float y, float width, float height) {
        this.img = img;
        this.width = width;
        this.height = height;
        setX(x);
        setY(y);
    }

    public void setX(float x) {
        left = x - width / 2;
    }
    public void setY(float y) {
        top = y - height / 2;
    }

    public float getLeft() {
        return left;
    }
    public float getTop() {
        return top;
    }
    public float getRight() {
        return left + width;
    }
    public float getBottom() {
        return top + height;
    }

    public boolean intersects(BoundingBox other) {
        return !(other.getLeft() > getRight()
                || other.getRight()  < left
                || other.getTop() > getBottom()
                || other.getBottom() < top);
    }

    //Strictly for testing
    public void render(Batch batch){
        batch.draw(img, left, top-height);
    }
}
