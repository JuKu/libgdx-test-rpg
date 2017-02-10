package com.jukusoft.libgdx.rpg.engine.entity;

import com.jukusoft.libgdx.rpg.engine.entity.annotation.SharableComponent;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;

/**
 * Created by Justin on 10.02.2017.
 */
public abstract class BaseComponent implements IComponent {

    protected BaseGame game = null;
    protected Entity entity = null;

    @Override
    public void init (BaseGame game, Entity entity) {
        this.game = game;
        this.entity = entity;

        if (getClass().isAnnotationPresent(SharableComponent.class)) {
            //you cannot access entity on this way
            this.entity = null;
        }
    }

    @Override
    public void onAddedToEntity(Entity entity) {

    }

    @Override
    public void onRemovedFromEntity(Entity entity) {

    }

    @Override
    public void dispose() {

    }

}
