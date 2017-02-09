package com.jukusoft.libgdx.rpg.game.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    protected float widthInTiles = 30;
    protected float heightInTiles = 30;

    protected MapPositionChangedListener changedListener = null;

    public BaseMap () {
        //
    }

    public float getX () {
        return this.x;
    }

    public float getY () {
        return this.y;
    }

    public float getWidthInPixels () {
        return this.widthInTiles * tileWidth;
    }

    public float getHeightInPixels () {
        return this.heightInTiles * tileHeight;
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
    public boolean isMapVisibleInViewPort (Camera camera) {
        //check borders
        float cameraWidth = camera.viewportWidth;
        float cameraHeight = camera.viewportHeight;

        return false;
    }

    public abstract void update (BaseGame game, Camera camera, GameTime time);

    public abstract void draw (GameTime time, Camera camera, SpriteBatch batch);

}
