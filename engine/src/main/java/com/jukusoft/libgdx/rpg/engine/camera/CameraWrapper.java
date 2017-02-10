package com.jukusoft.libgdx.rpg.engine.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by Justin on 10.02.2017.
 */
public class CameraWrapper {

    protected float x = 0;
    protected float y = 0;
    protected float zoom = 0;

    protected float cameraOffsetX = 0;
    protected float cameraOffsetY = 0;

    protected OrthographicCamera camera = null;

    public CameraWrapper (OrthographicCamera camera) {
        this.camera = camera;

        this.cameraOffsetX = this.camera.position.x;
        this.cameraOffsetY = this.camera.position.y;

        //this.sync();
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
        this.x = x + cameraOffsetX;

        this.syncPosToCamera();
    }

    public float getY () {
        return this.y;
    }

    public void setY (float y) {
        this.y = y + cameraOffsetY;

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
        this.x = x + cameraOffsetX;
        this.y = y + cameraOffsetY;
        this.zoom = zoom;

        this.syncPosToCamera();
    }

    public void setPosition (float x, float y) {
        this.x = x;
        this.y = y;

        this.syncPosToCamera();
    }

    protected void sync () {
        this.x = camera.position.x;
        this.y = camera.position.y;
        this.zoom = camera.zoom;

        this.cameraOffsetX = 0;
        this.cameraOffsetY = 0;
    }

    protected void syncPosToCamera () {
        this.camera.position.x = x;
        this.camera.position.y = y;
    }

    public Matrix4 getCombined () {
        return this.camera.combined;
    }

    public Frustum getFrustum () {
        return this.camera.frustum;
    }

    public void update () {
        this.camera.update();
    }

    @Deprecated
    public OrthographicCamera getOriginalCamera () {
        return this.camera;
    }

}
