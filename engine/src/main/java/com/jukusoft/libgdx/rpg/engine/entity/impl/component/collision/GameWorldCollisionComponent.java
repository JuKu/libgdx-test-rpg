package com.jukusoft.libgdx.rpg.engine.entity.impl.component.collision;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.MoveComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.PositionComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.GameWorldCollisionListener;
import com.jukusoft.libgdx.rpg.engine.entity.listener.MoveListener;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.utils.RectanglePoolPrototypeFactory;
import com.jukusoft.libgdx.rpg.engine.world.GameWorld;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 11.03.2017.
 */
public class GameWorldCollisionComponent extends BaseComponent implements MoveListener {

    protected PositionComponent positionComponent = null;
    protected MoveComponent moveComponent = null;
    protected CollisionBoxesComponent collisionBoxesComponent = null;

    protected GameWorld gameWorld = null;

    protected Pool<Rectangle> rectPool = null;

    protected List<GameWorldCollisionListener> collisionListenerList = new ArrayList<>();
    protected List<GameWorldCollisionListener> tempList = new ArrayList<>();

    public GameWorldCollisionComponent(GameWorld gameWorld) {
        this.gameWorld = gameWorld;

        //create new pool for rectangles
        this.rectPool = RectanglePoolPrototypeFactory.createRectanglePool();
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.moveComponent = entity.getComponent(MoveComponent.class);
        this.collisionBoxesComponent = entity.getComponent(CollisionBoxesComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        if (this.moveComponent == null) {
            throw new IllegalStateException("entity doesnt have an MoveComponent.");
        }

        //register listener as an hook, so collision component can avoid moving of entity
        this.moveComponent.addMoveHook(this);

        if (this.collisionBoxesComponent == null) {
            throw new IllegalStateException("entity doesnt have an CollisionBoxesComponent.");
        }
    }

    @Override public boolean canMove (float oldX, float oldY, float newX, float newY) {
        float dx = newX - oldX;
        float dy = newY - oldY;

        //get all hitboxes
        List<Rectangle> hitboxes = this.collisionBoxesComponent.listAllHitboxes();

        for (Rectangle rectangle : hitboxes) {
            //copy rectangle
            Rectangle copiedRectangle = rectPool.obtain();

            //copy values
            copiedRectangle.set(rectangle);
            copiedRectangle.setPosition(copiedRectangle.getX() + dx, copiedRectangle.getY() + dy);

            //test for overlapping
            if (this.gameWorld.isColliding(copiedRectangle)) {
                //recycle rectangle
                rectPool.free(copiedRectangle);

                //clear temporary list
                this.tempList.clear();

                //add all list entries to temporary list
                this.tempList.addAll(this.collisionListenerList);

                //call colliding listeners
                for (GameWorldCollisionListener listener : this.tempList) {
                    if (listener != null) {
                        //call colliding listener
                        listener.onGameWorldCollided(this.gameWorld, this.entity);
                    } else {
                        //remove listener
                        this.collisionListenerList.remove(listener);
                    }
                }

                return false;
            }

            //recycle rectangle
            rectPool.free(copiedRectangle);
        }

        return true;
    }

    public void addCollidingListener (GameWorldCollisionListener listener) {
        this.collisionListenerList.add(listener);
    }

    public void removeCollidingListener (GameWorldCollisionListener listener) {
        this.collisionListenerList.remove(listener);
    }
}
