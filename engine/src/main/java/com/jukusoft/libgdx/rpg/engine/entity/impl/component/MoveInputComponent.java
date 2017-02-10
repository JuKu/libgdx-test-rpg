package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.input.BaseGameActionConst;
import com.jukusoft.libgdx.rpg.engine.input.InputManager;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public class MoveInputComponent extends BaseComponent implements IUpdateComponent {

    protected MoveComponent moveComponent = null;

    protected float baseSpeedX = 1f;
    protected float baseSpeedY = 1f;

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

        float speedX = 0f;
        float speedY = 0;

        if (inputManager.isActionKeyPressed(BaseGameActionConst.MOVE_LEFT)) {
            speedX = -baseSpeedX;
        } else if (inputManager.isActionKeyPressed(BaseGameActionConst.MOVE_RIGHT)) {
            speedX = baseSpeedX;
        }

        if (inputManager.isActionKeyPressed(BaseGameActionConst.MOVE_UP)) {
            speedY = baseSpeedY;
        } else {
            speedY = -baseSpeedY;
        }

        //set speed
        this.moveComponent.setSpeed(speedX, speedY);
    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.HIGH;
    }

    public float getBaseSpeedX () {
        return this.baseSpeedX;
    }

    public void setBaseSpeedX (float speedX) {
        if (speedX <= 0) {
            throw new IllegalArgumentException("speed has to be greater than 0.");
        }

        this.baseSpeedX = speedX;
    }

    public float getBaseSpeedY () {
        return this.baseSpeedY;
    }

    public void setBaseSpeedY (float speedY) {
        if (speedY <= 0) {
            throw new IllegalArgumentException("speed has to be greater than 0.");
        }

        this.baseSpeedY = speedY;
    }

}
