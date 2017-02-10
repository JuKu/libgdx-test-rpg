package com.jukusoft.libgdx.rpg.engine.entity.impl;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ComponentPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public class FollowCameraComponent extends BaseComponent implements IUpdateComponent {

    protected float lerp = 0.1f;
    protected PositionComponent entityPosition = null;

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.entityPosition = entity.getComponent(PositionComponent.class);

        if (entityPosition == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }
    }

    @Override public void update(BaseGame game, GameTime time) {
        float dt = time.getDeltaTime();

        //get screen resolution
        float screenWidth = game.getViewportWidth();
        float screenHeight = game.getViewportHeight();

        //calculate camera middle
        float currentCameraMiddleX = game.getCamera().position.x + (screenWidth / 2);
        float currentCameraMiddleY = game.getCamera().position.y + (screenHeight / 2);

        float targetX = entityPosition.getX();
        float targetY = entityPosition.getY();

        //move camera
        float deltaX = targetX - currentCameraMiddleX;
        float deltaY = targetY - currentCameraMiddleY;

        float newCameraX = game.getCamera().position.x + deltaX * lerp * dt;
        float newCameraY = game.getCamera().position.y = deltaY * lerp * dt;

        game.getCamera().position.x = newCameraX;
        game.getCamera().position.y = newCameraY;
    }

    @Override public ComponentPriority getUpdateOrder() {
        return null;
    }

}
