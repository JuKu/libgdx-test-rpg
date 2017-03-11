package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IDrawComponent;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.DirectionChangedListener;
import com.jukusoft.libgdx.rpg.engine.entity.listener.MoveListener;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 10.02.2017.
 */
public class MoveComponent extends BaseComponent implements IUpdateComponent, IDrawComponent {

    protected PositionComponent positionComponent = null;

    protected boolean isMoving = false;
    protected float speedX = 0f;
    protected float speedY = 0f;

    protected float speedMultiplicatorX = 1f;
    protected float speedMultiplicatorY = 1f;

    //calculated direction
    protected Direction direction = null;

    protected boolean forceDirection = false;
    protected boolean forceDirectionForNextCycle = false;

    //list with direction changed listener for example for animation system
    protected List<DirectionChangedListener> directionChangedListenerList = new ArrayList<>();

    //list with all move listener hooks, for example for collision system
    protected List<MoveListener> moveListenerList = new ArrayList<>();

    public MoveComponent (float speedX, float speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public MoveComponent () {
        //
    }

    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        this.direction = Direction.DOWN;
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
        float newX = positionComponent.getX() + (this.speedX * dt * 100);
        float newY = positionComponent.getY() + (this.speedY * dt * 100);

        //check, if entity can move (per hooks, for example for collision system)
        if (!canMove(positionComponent.getX(), positionComponent.getY(), newX, positionComponent.getY())) {
            this.speedX = 0;
            newX = positionComponent.getX();
        }

        //check, if entity can move (per hooks, for example for collision system)
        if (!canMove(positionComponent.getX(), positionComponent.getY(), positionComponent.getX(), newY)) {
            this.speedY = 0;
            newY = positionComponent.getY();
        }

        if (speedX == 0 && speedY == 0) {
            this.isMoving = false;
        }

        //set new position
        positionComponent.setPosition(newX, newY);

        if (!forceDirection && !forceDirectionForNextCycle) {
            //update player direction
            this.updateDirection();
        }
    }

    protected boolean canMove (float oldX, float oldY, float newX, float newY) {
        //check, if entity can move (per hooks, for example for collision system)
        for (MoveListener listener : this.moveListenerList) {
            if (!listener.canMove(positionComponent.getX(), positionComponent.getY(), newX, newY)) {
                //an hook doesnt allow, that entity move
                return false;
            }
        }

        return true;
    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.HIGH;
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

    public void setDirection (Direction direction) {
        this.direction = direction;

        //use this direction for next gameloop cycle
        this.forceDirectionForNextCycle = true;
    }

    public void setForceDirection (boolean forceDirection) {
        this.forceDirection = forceDirection;
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

    public void addMoveHook (MoveListener listener) {
        this.moveListenerList.add(listener);
    }

    public void removeMoveHook (MoveListener listener) {
        this.moveListenerList.remove(listener);
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //only reset force direction flag
        this.forceDirectionForNextCycle = false;
    }

    @Override public ECSPriority getDrawOrder() {
        return ECSPriority.VERY_LOW;
    }
}
