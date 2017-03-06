package com.jukusoft.libgdx.rpg.engine.pivot;

/**
 * Created by Justin on 06.03.2017.
 */
public class PivotPoint {

    //relative position of pivot point to entity (0, 0) is left bottom
    protected float x = 0;
    protected float y = 0;

    protected float offsetX = 0;
    protected float offsetY = 0;

    public PivotPoint (float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Deprecated
    public float[] get () {
        return new float[] { this.x, this.y };
    }

    public float getX (float offsetX) {
        this.offsetX = offsetX;
        return this.x + this.offsetX;
    }

    @Deprecated
    public float getX () {
        return this.x + this.offsetX;
    }

    public void setX (float x) {
        this.x = x;
    }

    public float getY (float offsetY) {
        this.offsetY = offsetY;
        return this.y + this.offsetY;
    }

    @Deprecated
    public float getY () {
        return this.y + this.offsetY;
    }

    public void setPos (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setOffset (float offsetX, float offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

}
