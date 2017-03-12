package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.annotation.SharableComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.GameWorldCollisionListener;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.world.GameWorld;

/**
 * Created by Justin on 12.03.2017.
 */
@SharableComponent
public class RemoveOnGameWorldCollisionComponent extends BaseComponent implements GameWorldCollisionListener {

    protected GameWorldCollisionComponent collisionComponent = null;

    public RemoveOnGameWorldCollisionComponent () {
        //
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.collisionComponent = entity.getComponent(GameWorldCollisionComponent.class);

        if (this.collisionComponent == null) {
            throw new IllegalStateException("entity doesnt have an GameWorldCollisionComponent.");
        }

        this.collisionComponent.addCollidingListener(this);
    }

    @Override public void onGameWorldCollided(GameWorld gameWorld, Entity entity) {
        //remove entity on next gameloop cycle
        this.game.runOnUIThread(() -> {
            //remove entity
            entity.getEntityComponentSystem().removeEntity(entity);
        });
    }

    @Override
    public void dispose () {
        if (this.collisionComponent != null) {
            this.collisionComponent.removeCollidingListener(this);
        }
    }
}
