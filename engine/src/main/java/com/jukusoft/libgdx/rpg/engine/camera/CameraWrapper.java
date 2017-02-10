package com.jukusoft.libgdx.rpg.engine.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Justin on 10.02.2017.
 */
public class CameraWrapper {

    protected float x = 0;
    protected float y = 0;
    protected float zoom = 0;

    protected OrthographicCamera camera = null;

    public CameraWrapper (OrthographicCamera camera) {
        this.camera = camera;

        this.sync();
    }

    public void translate (float x, float y, float zoom) {
        this.x += x;
        this.y += y;
        this.zoom += zoom;

        camera.translate(x, y, zoom);
    }

    public float getX () {
        return this.x;
    }

    public void setX (float x) {
        this.x = x;

        this.syncPosToCamera();
    }

    public float getY () {
        return this.y;
    }

    public void setY (float y) {
        this.y = y;

        this.syncPosToCamera();
    }

    public float getZoom () {
        return this.zoom;
    }

    public void setZoom (float zoom) {
        this.zoom = zoom;

        this.syncPosToCamera();
    }

    public void setPosition (float x, float y, float zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;

        this.syncPosToCamera();
    }

    protected void sync () {
        this.x = camera.position.x;
        this.y = camera.position.y;
        this.zoom = camera.zoom;
    }

    protected void syncPosToCamera () {
        this.camera.position.x = x;
        this.camera.position.y = y;
    }

    @Deprecated
    public OrthographicCamera getOriginalCamera () {
        return this.camera;
    }

}
