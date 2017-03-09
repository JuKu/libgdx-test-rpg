package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.Direction;

/**
 * Created by Justin on 09.03.2017.
 */
public class BasicMovementAnimationControlComponent extends BaseComponent implements IUpdateComponent {

    protected MoveComponent moveComponent = null;
    protected AtlasAnimationComponent atlasAnimationComponent = null;

    protected Direction lastDirection = Direction.DOWN;
    protected boolean lastMovingFlag = false;

    public BasicMovementAnimationControlComponent() {
        //
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        //get required components
        this.moveComponent = entity.getComponent(MoveComponent.class);
        this.atlasAnimationComponent = entity.getComponent(AtlasAnimationComponent.class);

        if (this.moveComponent == null) {
            throw new IllegalStateException("You have to set an MoveComponent to entity to use BasicMovementAnimationControlComponent.");
        }

        if (this.atlasAnimationComponent == null) {
            throw new IllegalStateException("You have to set an AtlasAnimationComponent to entity to use BasicMovementAnimationControlComponent.");
        }
    }

    @Override public void update(BaseGame game, GameTime time) {
        //set current direction
        Direction direction = this.moveComponent.getDirection();

        if (direction == null) {
            //we cannot update, if now direction is set
            return;

            //throw new NullPointerException("direction from MoveComponent is null.");
        }

        //get flag, if character is moving
        boolean isMoving = this.moveComponent.isMoving();

        if (this.lastDirection == direction && this.lastMovingFlag == isMoving) {
            //same direction & movement, so we dont need to change animation state
            return;
        }

        StringBuilder sb = new StringBuilder();

        if (isMoving) {
            sb.append("walk");
        } else {
            sb.append("stand");
        }

        if (direction == Direction.DOWN || direction == Direction.DOWN_LEFT || direction == Direction.DOWN_RIGHT) {
            sb.append("Down");
        } else if (direction == Direction.UP || direction == Direction.UP_LEFT || direction == Direction.UP_RIGHT) {
            sb.append("Up");
        }

        if (direction == Direction.LEFT || direction == Direction.UP_LEFT || direction == Direction.DOWN_LEFT) {
            sb.append("Left");
        } else if (direction == Direction.RIGHT || direction == Direction.UP_RIGHT || direction == Direction.DOWN_RIGHT) {
            sb.append("Right");
        }

        String currentAnimation = sb.toString();
        System.out.println("current animation: " + currentAnimation);

        this.atlasAnimationComponent.setCurrentAnimationName(currentAnimation);

        //set last direction
        this.lastDirection = direction;
        this.lastMovingFlag = isMoving;
    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.LOW;
    }

}
