package com.jukusoft.libgdx.rpg.engine.entity.impl;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ComponentPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.input.InputManager;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public class MoveInputComponent extends BaseComponent implements IUpdateComponent {

    protected MoveComponent moveComponent = null;

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.moveComponent = entity.getComponent(MoveComponent.class);

        if (this.moveComponent == null) {
            throw new IllegalStateException("entity doesnt have an MoveComponent.");
        }
    }

    @Override public void update(BaseGame game, GameTime time) {
        //get input manager
        InputManager inputManager = game.getInputManager();
    }

    @Override public ComponentPriority getUpdateOrder() {
        return ComponentPriority.HIGH;
    }

}
