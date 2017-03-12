package com.jukusoft.libgdx.rpg.engine.entity.listener;

import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.world.GameWorld;

/**
 * Created by Justin on 11.03.2017.
 */
@FunctionalInterface
public interface GameWorldCollisionListener {

    public void onGameWorldCollided (GameWorld gameWorld, Entity entity);

    //public void onEntityCollided (Entity entity, Entity collidedWithEntity);

}
