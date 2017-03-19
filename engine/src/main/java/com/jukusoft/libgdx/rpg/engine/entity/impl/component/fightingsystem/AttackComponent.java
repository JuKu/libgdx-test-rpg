package com.jukusoft.libgdx.rpg.engine.entity.impl.component.fightingsystem;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.collision.CollisionBoxesComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.MoveComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.PositionComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.MoveListener;
import com.jukusoft.libgdx.rpg.engine.fightingsystem.AttackAction;
import com.jukusoft.libgdx.rpg.engine.fightingsystem.HitboxesSystem;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.utils.RectanglePoolPrototypeFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 14.03.2017.
 */
public class AttackComponent extends BaseComponent implements MoveListener {

    protected PositionComponent positionComponent = null;
    protected MoveComponent moveComponent = null;
    protected CollisionBoxesComponent collisionBoxesComponent = null;

    protected Pool<Rectangle> rectPool = null;

    protected Map<Integer,AttackAction> actionMap = new ConcurrentHashMap<>();

    protected HitboxesSystem hitboxesSystem = null;

    public AttackComponent (HitboxesSystem hitboxesSystem, AttackAction... actions) {
        if (actions.length == 0) {
            System.out.println("no attack action specified.");
        }

        int i = 1;

        for (AttackAction action : actions) {
            actionMap.put(i, action);
            i++;
        }

        this.hitboxesSystem = hitboxesSystem;

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
            if (this.hitboxesSystem.isHitboxColliding(copiedRectangle)) {
                //recycle rectangle
                rectPool.free(copiedRectangle);

                //clear temporary list
                /*this.tempList.clear();

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
                }*/

                System.out.println("hitbox collision.");

                return true;
            }

            //recycle rectangle
            rectPool.free(copiedRectangle);
        }

        return true;
    }

    public AttackAction getAttackAction (int attackAction) {
        return this.actionMap.get(attackAction);
    }
}
