package com.jukusoft.libgdx.rpg.engine.lighting;

/**
 * Created by Justin on 09.02.2017.
 */
public interface LightingEnvironment {

    public void setAmbientColor (float r, float g, float b);

    public void setAmbientIntensity (float ambientIntensity);

    public void addAmbientLightListener (AmbientLightChangedListener listener);

    public void removeAmbientLightListener (AmbientLightChangedListener listener);

    public void addLighting (Lighting lighting);

    public void removeLighting (Lighting lighting);

}
