package com.jukusoft.libgdx.rpg.engine.camera.impl;

import com.jukusoft.libgdx.rpg.engine.camera.CameraModification;
import com.jukusoft.libgdx.rpg.engine.camera.ModificationFinishedListener;
import com.jukusoft.libgdx.rpg.engine.camera.TempCameraParams;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

import java.util.Random;

/**
 * Created by Justin on 11.02.2017.
 */
public class Shake1CameraModification implements CameraModification {

    protected volatile boolean isActive = false;

    protected float elapsed = 0;
    protected float intensity = 0;
    protected float duration = 0;

    protected Random random = new Random();

    @Override
    public void onUpdate(GameTime time, TempCameraParams camera, ModificationFinishedListener listener) {
        if (!isActive) {
            //mod isnt active, so we dont need to update mod
            return;
        }

        float delta = time.getDeltaTime();

        //http://www.netprogs.com/libgdx-screen-shaking/

        //shake only, if activated
        if(elapsed < duration) {
            // Calculate the amount of shake based on how long it has been shaking already
            float currentPower = intensity * camera.getZoom() * ((duration - elapsed) / duration);
            float x = (random.nextFloat() - 0.5f) * 2 * currentPower;
            float y = (random.nextFloat() - 0.5f) * 2 * currentPower;
            camera.translate(-x, -y);

            // Increase the elapsed time by the delta provided.
            elapsed += delta;
        } else {
            //shake was finsihed
            this.isActive = true;
            //listener.onModificationFinished(this, Shake1CameraModification.class);
        }
    }

    @Override
    public void dispose() {

    }

    public boolean isActive () {
        return this.isActive;
    }

    /**
    * Start the screen shaking with a given power and duration
     *
     * @param intensity How much intensity should the shaking use.
     * @param duration Time in milliseconds the screen should shake.
    */
    public void shake (float intensity, float duration) {
        this.elapsed = 0;
        this.intensity = intensity;
        this.duration = duration;

        this.isActive = true;
    }

}
