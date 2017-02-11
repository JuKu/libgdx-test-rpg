package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.DirectionChangedListener;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 10.02.2017.
 */
public class MoveComponent extends BaseComponent implements IUpdateComponent {

    protected PositionComponent positionComponent = null;

    protected boolean isMoving = false;
    protected float speedX = 1f;
    protected float speedY = 1f;

    protected float speedMultiplicatorX = 1f;
    protected float speedMultiplicatorY = 1f;

    //calculated direction
    protected Direction direction = null;

    //list with direction changed listener for example for animation system
    protected List<DirectionChangedListener> directionChangedListenerList = new ArrayList<>();

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
        float newX = positionComponent.getX() + (this.speedX * dt * 10);
        float newY = positionComponent.getY() + (this.speedY * dt * 10);

        //set new position
        positionComponent.setPosition(newX, newY);

        //update player direction
        this.updateDirection();
    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.NORMAL;
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
        this.speedX = speedX * speedMultiplicatorX;

        if (speedX != 0) {
            this.isMoving = true;
        }
    }

    public float getSpeedY () {
        return this.speedY;
    }

    public void setSpeedY (float speedY) {
        this.speedY = speedY * speedMultiplicatorY;

        if (speedY != 0) {
            this.isMoving = true;
        }
    }

    public void setSpeed (float speedX, float speedY) {
        this.speedX = speedX * speedMultiplicatorX;
        this.speedY = speedY * speedMultiplicatorY;

        if (speedX == 0 && speedY == 0) {
            this.isMoving = false;
        } else {
            this.isMoving = true;
        }
    }

    protected void updateDirection () {
        Direction newDirection = Direction.DOWN;

        if (!isMoving) {
            //use last direction
            newDirection = this.direction;

            return;
        }

        boolean movingUp = false;
        boolean movingDown = false;
        boolean movingLeft = false;
        boolean movingRight = false;

        if (speedX < 0) {
            movingLeft = true;
        } else if (speedX > 0) {
            movingRight = true;
        }

        if (speedY < 0) {
            movingDown = true;
        } else if (speedY > 0) {
            movingUp = true;
        }

        if (movingLeft) {
            if (!movingUp && !movingDown) {
                newDirection = Direction.LEFT;
            } else if (movingUp) {
                newDirection = Direction.UP_LEFT;
            } else if (movingDown) {
                newDirection = Direction.DOWN_LEFT;
            }
        } else if (movingRight) {
            if (!movingUp && !movingDown) {
                newDirection = Direction.RIGHT;
            } else if (movingUp) {
                newDirection = Direction.UP_RIGHT;
            } else if (movingDown) {
                newDirection = Direction.DOWN_RIGHT;
            }
        } else if (movingUp) {
            newDirection = Direction.UP;
        } else {
            newDirection = Direction.DOWN;
        }

        if (this.direction != newDirection) {
            //direction has changed
            notifyDirectionChanged(this.direction, newDirection);
        }

        //set new direction
        this.direction = newDirection;
    }

    public Direction getDirection () {
        return this.direction;
    }

    protected void notifyDirectionChanged (Direction oldDirection, Direction newDirection) {
        this.directionChangedListenerList.stream().forEach(listener -> {
            listener.onDirectionChanged(oldDirection, newDirection);
        });
    }

    public void addDirectionChangedListener (DirectionChangedListener listener) {
        this.directionChangedListenerList.add(listener);
    }

    public void removeDirectionChangedListener (DirectionChangedListener listener) {
        this.directionChangedListenerList.remove(listener);
    }

}
