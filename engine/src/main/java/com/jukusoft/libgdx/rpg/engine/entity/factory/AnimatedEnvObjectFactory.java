package com.jukusoft.libgdx.rpg.engine.entity.factory;

import com.badlogic.gdx.graphics.Texture;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.EntityManager;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.*;

/**
 * Created by Justin on 15.02.2017.
 */
public class AnimatedEnvObjectFactory {

    public static Entity createBasicAnimatedEntity (EntityManager ecs, Texture texture, float x, float y, float duration, int rows, int cols) {
        if (texture == null) {
            throw new NullPointerException("texture cannot be null.");
        }

        //create new entity
        Entity entity = new Entity(ecs);

        //add new position component, because every entity has an position
        entity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add texture region component
        entity.addComponent(new DrawTextureRegionComponent(), DrawTextureRegionComponent.class);

        //add basic animation component
        entity.addComponent(new BasicAnimationComponent(texture, duration, rows, cols), BasicAnimationComponent.class);

        //add shadow component
        //entity.addComponent(new ShadowComponent(), ShadowComponent.class);

        return  entity;
    }

    public static Entity createBasicAnimatedLightingEntity (EntityManager ecs, Texture texture, Texture lightMap, float x, float y, float duration, int rows, int cols) {
        if (texture == null) {
            throw new NullPointerException("texture cannot be null.");
        }

        //create new entity
        Entity entity = new Entity(ecs);

        //add new position component, because every entity has an position
        entity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add texture region component
        entity.addComponent(new DrawTextureRegionComponent(), DrawTextureRegionComponent.class);

        //add basic animation component
        entity.addComponent(new BasicAnimationComponent(texture, duration, rows, cols), BasicAnimationComponent.class);

        //add lighting component
        entity.addComponent(new LightMapComponent(lightMap, 0, 0), LightMapComponent.class);

        //add shadow component
        //entity.addComponent(new ShadowComponent(), ShadowComponent.class);

        return  entity;
    }

    public static Entity createParticlesEntity (EntityManager ecs, String particleFile, Texture lightMap, float x, float y) {
        //create new entity
        Entity entity = new Entity(ecs);

        //add new position component, because every entity has an position
        entity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add particles component
        entity.addComponent(new DrawParticlesComponent(particleFile, true, true), DrawParticlesComponent.class);

        //add lighting component
        entity.addComponent(new LightMapComponent(lightMap, 0, 0), LightMapComponent.class);

        return entity;
    }

    public static Entity createParticlesEntity (EntityManager ecs, String particleFile, float x, float y) {
        //create new entity
        Entity entity = new Entity(ecs);

        //add new position component, because every entity has an position
        entity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add particles component
        entity.addComponent(new DrawParticlesComponent(particleFile, true, true), DrawParticlesComponent.class);

        return entity;
    }

}
