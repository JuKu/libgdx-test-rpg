package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public class FollowCameraComponent extends BaseComponent implements IUpdateComponent {

    protected float lerp = 0.5f;
    protected PositionComponent entityPosition = null;

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.entityPosition = entity.getComponent(PositionComponent.class);

        if (this.entityPosition == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }
    }

    @Override public void update(BaseGame game, GameTime time) {
        float dt = time.getDeltaTime();

        //get screen resolution
        float screenWidth = game.getViewportWidth();
        float screenHeight = game.getViewportHeight();

        //calculate camera middle
        float currentCameraMiddleX = game.getCamera().getX() + (screenWidth / 2);
        float currentCameraMiddleY = game.getCamera().getY() + (screenHeight / 2);

        float targetX = entityPosition.getMiddleX() - (game.getViewportWidth() / 2);
        float targetY = entityPosition.getMiddleY() - (game.getViewportHeight() / 2);
        //System.out.println("targetX: " + targetX + ", targetY: " + targetY);

        //move camera
        float deltaX = targetX - currentCameraMiddleX;
        float deltaY = targetY - currentCameraMiddleY;

        if (Math.abs(deltaX) <= 10) {
            deltaX = 0;
        }

        if (Math.abs(deltaY) <= 10) {
            deltaY = 0;
        }

        //System.out.println("deltaX: " + deltaX + ", deltaY: " + deltaY + ", delta: " + dt);

        float newCameraX = game.getCamera()/*.position.x*/.getX() + (deltaX * lerp * dt);
        float newCameraY = game.getCamera()/*.position.y*/.getY() + (deltaY * lerp * dt);

        if (Math.abs(deltaX) <= 1) {
            newCameraX = targetX;
        } else if (Math.abs(deltaX) <= 10) {
            newCameraX = game.getCamera().getX() + deltaX;
        }

        if (Math.abs(deltaY) <= 1) {
            newCameraY = targetY;
        } else if (Math.abs(deltaY) <= 10) {
            newCameraY = game.getCamera().getY() + deltaY;
        }

        //game.getCamera().position.x = newCameraX;
        //game.getCamera().position.y = newCameraY;

        //game.getCamera().setPosition(newCameraX, newCameraY);
        game.getCamera().setPosition(entityPosition.getX() - (game.getViewportWidth() / 2), entityPosition.getY() - (game.getViewportHeight() / 2));
    }

    @Override public ECSPriority getUpdateOrder() {
        //camera should be updated after all entities
        return ECSPriority.VERY_LOW;
    }

}
