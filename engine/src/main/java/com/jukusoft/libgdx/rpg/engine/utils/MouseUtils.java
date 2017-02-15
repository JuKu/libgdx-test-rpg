package com.jukusoft.libgdx.rpg.engine.utils;

import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;

/**
 * Created by Justin on 12.02.2017.
 */
public class MouseUtils {

    public static float getMouseX (float x, CameraWrapper camera) {
        return (x + camera.getX()) * 1 / camera.getZoom();/* - (viewportWidth / 2)*/
    }

    public static float getMouseY (float y, CameraWrapper camera) {
        y = camera.getOriginalCamera().viewportHeight - y;
        return (y + camera.getY()) * 1 / camera.getZoom();
    }

}
