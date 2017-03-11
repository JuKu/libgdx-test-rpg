package com.jukusoft.libgdx.rpg.engine.entity.factory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.EntityManager;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.*;
import com.jukusoft.libgdx.rpg.engine.utils.Direction;
import com.jukusoft.libgdx.rpg.engine.world.GameWorld;

/**
 * Created by Justin on 09.03.2017.
 */
public class ProjectileFactory {

    public static Entity createBasicProjectile (EntityManager ecs, GameWorld gameWorld, String atlasPath, Direction direction, float x, float y, float speedX, float speedY) {
        //create new entity
        Entity entity = new Entity(ecs);

        //add new position component, because every entity has an position
        entity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add an movement component, so projectile can be moved by update() method
        entity.addComponent(new MoveComponent(speedX, speedY), MoveComponent.class);

        //set direction
        entity.getComponent(MoveComponent.class).setDirection(direction);

        //add texture region component to draw projectile
        entity.addComponent(new DrawTextureRegionComponent(), DrawTextureRegionComponent.class);

        //add animation component
        entity.addComponent(new AtlasAnimationComponent(atlasPath, "standDown", 1f), AtlasAnimationComponent.class);

        //add component to control movement animations
        entity.addComponent(new BasicMovementAnimationControlComponent(), BasicMovementAnimationControlComponent.class);

        //add hitbox component
        entity.addComponent(new HitBoxesComponent(true), HitBoxesComponent.class);

        //add gameworld collision component
        entity.addComponent(new GameWorldCollision(gameWorld), GameWorldCollision.class);

        //add shadow component
        entity.addComponent(new BlobShadowComponent(10, 32), BlobShadowComponent.class);

        return entity;
    }

    public static Entity createBasicProjectile (EntityManager ecs, TextureRegion textureRegion, float x, float y, float speedX, float speedY) {
        //create new entity
        Entity entity = new Entity(ecs);

        //add new position component, because every entity has an position
        entity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add an movement component, so projectile can be moved by update() method
        entity.addComponent(new MoveComponent(), MoveComponent.class);

        //add texture region component to draw projectile
        entity.addComponent(new DrawTextureRegionComponent(textureRegion), DrawTextureRegionComponent.class);

        //add shadow component
        entity.addComponent(new BlobShadowComponent(10, 32), BlobShadowComponent.class);

        return entity;
    }

}
