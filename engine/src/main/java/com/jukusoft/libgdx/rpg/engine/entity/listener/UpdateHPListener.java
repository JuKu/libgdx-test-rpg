package com.jukusoft.libgdx.rpg.engine.entity.listener;

/**
 * Created by Justin on 14.03.2017.
 */
@FunctionalInterface
public interface UpdateHPListener {

    public void onHPUpdate (float oldValue, float newValue, float maxValue);

}
