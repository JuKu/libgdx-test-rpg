package com.jukusoft.libgdx.rpg.engine.input.impl;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.input.InputPriority;
import com.jukusoft.libgdx.rpg.engine.input.listener.ScrollListener;

/**
 * Created by Justin on 09.02.2017.
 */
public class CameraZoomListener implements ScrollListener {

    protected float ZOOM_SENSITY = 0.04f;

    protected CameraWrapper camera = null;

    public CameraZoomListener (CameraWrapper camera) {
        this.camera = camera;
    }

    @Override public boolean scrolled(float amount) {
        //calculate camera zoom
        this.camera.setZoom(camera.getZoom() + amount * 0.04f);//.zoom += amount * 0.04f;
        this.camera.update();

        //input event was used, so we return true
        return true;
    }

    @Override public InputPriority getInputOrder() {
        return InputPriority.NORMAL;
    }

    @Override public int compareTo(ScrollListener o) {
        return o.getInputOrder().compareTo(this.getInputOrder());
    }

}
