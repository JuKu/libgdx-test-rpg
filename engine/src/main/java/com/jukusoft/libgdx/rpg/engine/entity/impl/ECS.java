package com.jukusoft.libgdx.rpg.engine.entity.impl;

import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IComponent;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;

/**
 * Created by Justin on 10.02.2017.
 */
public class ECS extends BaseECS {

    public ECS(BaseGame game) {
        super(game);
    }

    @Override protected void onEntityAdded(Entity entity) {

    }

    @Override protected void onEntityRemoved(Entity entity) {

    }

    @Override public <T extends IComponent> void onComponentAdded(Entity entity, T component, Class<T> cls) {

    }

    @Override public <T extends IComponent> void onComponentRemoved(Entity entity, T component, Class<T> cls) {

    }
}
