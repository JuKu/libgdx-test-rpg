package com.jukusoft.libgdx.rpg.game.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 09.02.2017.
 */
public abstract class BaseMap {

    protected float x = 0;
    protected float y = 0;

    //pixel per tile
    protected float tileWidth = 32;
    protected float tileHeight = 32;

    //number of tiles
    protected float cols = 30;
    protected float rows = 30;

    protected BoundingBox boundingBox = null;

    protected MapPositionChangedListener changedListener = null;

    public BaseMap () {
        this.boundingBox = new BoundingBox(new Vector3(x, y, 0), new Vector3(x + getWidthInPixels(), y + getHeightInPixels(), 0));
    }

    public float getX () {
        return this.x;
    }

    public float getY () {
        return this.y;
    }

    public float getWidthInPixels () {
        return this.cols * tileWidth;
    }

    public float getHeightInPixels () {
        return this.rows * tileHeight;
    }

    public void setPosition (float x, float y) {
        //backup old position for listener
        float oldX = this.x;
        float oldY = this.y;

        //set new position
        this.x = x;
        this.y = y;

        //notify listener
        notifyPositionChangedListener(oldX, oldY, x, y);
    }

    protected void notifyPositionChangedListener (float oldX, float oldY, float newX, float newY) {
        if (this.changedListener != null) {
            this.changedListener.changedPosition(this, oldX, oldY, newX, newY);
        }
    }

    public <T extends BaseMap> void setPositionChangedListener (MapPositionChangedListener<T> listener) {
        this.changedListener = listener;
    }

    /**
    * check, if this coordinate is in map
    */
    public boolean isPointInFrustum (float x, float y) {
        if (x >= this.getX() && x < (this.getX() + this.getWidthInPixels())) {
            if (y >= this.getY() && y < (this.getY() + this.getHeightInPixels())) {
                return true;
            }
        }

        return false;
    }

    /**
    * check, if map is visible in current viewport
    */
    public boolean isMapVisibleInViewPort (CameraWrapper CameraWrapper) {
        //check borders
        //float CameraWrapperWidth = CameraWrapper.viewportWidth;
        //float CameraWrapperHeight = CameraWrapper.viewportHeight;

        //check, if map is in frustum (viewport)
        return CameraWrapper.getFrustum().boundsInFrustum(this.boundingBox);
    }

    public abstract void update (BaseGame game, CameraWrapper CameraWrapper, GameTime time);

    public abstract void draw (GameTime time, CameraWrapper CameraWrapper, ShaderProgram shader, SpriteBatch batch);

}
