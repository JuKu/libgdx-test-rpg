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
public class MoveComponent extends BaseComponent implements IUpdateComponent {

    protected PositionComponent positionComponent = null;

    protected boolean isMoving = false;
    protected float speedX = 1f;
    protected float speedY = 1f;

    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }
    }

    @Override public void update(BaseGame game, GameTime time) {
        if (speedX == 0 && speedY == 0) {
            this.isMoving = false;
        } else {
            this.isMoving = true;
        }

        if (!isMoving) {
            return;
        }

        //get delta time
        float dt = time.getDeltaTime();

        //calculate new entity position
        float newX = positionComponent.getX() + (this.speedX * dt);
        float newY = positionComponent.getY() + (this.speedY * dt);

        //set new position
        positionComponent.setPosition(newX, newY);
    }

    @Override public ComponentPriority getUpdateOrder() {
        return ComponentPriority.NORMAL;
    }

    public boolean isMoving () {
        return this.isMoving;
    }

    public void setMoving (boolean moving) {
        this.isMoving = moving;
    }

    public float getSpeedX () {
        return this.speedX;
    }

    public void setSpeedX (float speedX) {
        this.speedX = speedX;

        if (speedX != 0) {
            this.isMoving = true;
        }
    }

    public float getSpeedY () {
        return this.speedY;
    }

    public void setSpeedY (float speedY) {
        this.speedY = speedY;

        if (speedY != 0) {
            this.isMoving = true;
        }
    }

    public void setSpeed (float speedX, float speedY) {
        this.speedX = speedX;
        this.speedY = speedY;

        if (speedX == 0 && speedY == 0) {
            this.isMoving = false;
        } else {
            this.isMoving = true;
        }
    }

}
