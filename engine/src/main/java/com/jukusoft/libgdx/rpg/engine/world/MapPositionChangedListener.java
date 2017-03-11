package com.jukusoft.libgdx.rpg.engine.world;

/**
 * Created by Justin on 09.02.2017.
 */
public interface MapPositionChangedListener<T extends BaseMap> {

    public void changedPosition (T map, float oldX, float oldY, float newX, float newY);

}
