package com.jukusoft.libgdx.rpg.engine.camera;

/**
 * Created by Justin on 11.02.2017.
 */
public interface CameraModification {

    public void onUpdate (TempCameraPosition position, ModificationFinishedListener listener);

    public void dispose ();

}
