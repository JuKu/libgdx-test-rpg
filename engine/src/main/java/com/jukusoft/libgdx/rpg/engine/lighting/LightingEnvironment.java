package com.jukusoft.libgdx.rpg.engine.lighting;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Justin on 09.02.2017.
 */
public interface LightingEnvironment {

    public Vector3 getAmbientColor ();

    public void setAmbientColor (float r, float g, float b);

    public float getAmbientIntensity ();

    public void setAmbientIntensity (float ambientIntensity);

    public void addAmbientLightListener (AmbientLightChangedListener listener);

    public void removeAmbientLightListener (AmbientLightChangedListener listener);

    public void addLighting (Lighting lighting);

    public void removeLighting (Lighting lighting);

    public boolean isLightingEnabled ();

    public void setLightingEnabled (boolean lightingEnabled);

}
