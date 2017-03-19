package com.jukusoft.libgdx.rpg.engine.entity.impl;

import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.collision.CollisionBoxesComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw.LightMapComponent;
import com.jukusoft.libgdx.rpg.engine.fightingsystem.HitboxesSystem;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.lighting.LightingSystem;

/**
 * Created by Justin on 10.02.2017.
 */
public class ECS extends BaseECS {

    protected LightingSystem lightingSystem = null;
    protected HitboxesSystem hitboxesSystem = null;

    public ECS(BaseGame game, LightingSystem lightingSystem, HitboxesSystem hitboxesSystem) {
        super(game);

        this.lightingSystem = lightingSystem;
        this.hitboxesSystem = hitboxesSystem;
    }

    @Override protected void onEntityAdded(Entity entity) {

    }

    @Override protected void onEntityRemoved(Entity entity) {

    }

    @Override public <T extends IComponent> void onComponentAdded(Entity entity, T component, Class<T> cls) {
        if (component instanceof LightMapComponent) {
            LightMapComponent comp = (LightMapComponent) component;

            //add lighting
            this.lightingSystem.addLighting(comp.getLighting());
        }

        if (component instanceof CollisionBoxesComponent) {
            CollisionBoxesComponent collisionBoxesComponent = (CollisionBoxesComponent) component;

            this.hitboxesSystem.addEntity(entity, collisionBoxesComponent);
        }
    }

    @Override public <T extends IComponent> void onComponentRemoved(Entity entity, T component, Class<T> cls) {
        if (component instanceof LightMapComponent) {
            LightMapComponent comp = (LightMapComponent) component;

            //remove lighting
            this.lightingSystem.removeLighting(comp.getLighting());
        }

        if (component instanceof CollisionBoxesComponent) {
            CollisionBoxesComponent collisionBoxesComponent = (CollisionBoxesComponent) component;

            this.hitboxesSystem.removeEntity(entity, collisionBoxesComponent);
        }
    }
}
