package com.jukusoft.libgdx.rpg.engine.entity.listener;

import com.jukusoft.libgdx.rpg.engine.utils.Direction;

/**
 * Created by Justin on 10.02.2017.
 */
public interface DirectionChangedListener {

    public void onDirectionChanged (Direction oldDirection, Direction newDirection);

}
