package com.jukusoft.libgdx.rpg.engine.entity.factory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.EntityManager;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.*;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.collision.CollisionBoxesComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.collision.GameWorldCollisionComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw.AtlasAnimationComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw.BasicMovementAnimationControlComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw.DrawTextureRegionComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.fightingsystem.AttackComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.shadow.BlobShadowComponent;
import com.jukusoft.libgdx.rpg.engine.fightingsystem.AttackAction;
import com.jukusoft.libgdx.rpg.engine.fightingsystem.HitboxesSystem;
import com.jukusoft.libgdx.rpg.engine.utils.Direction;
import com.jukusoft.libgdx.rpg.engine.world.GameWorld;

/**
 * Created by Justin on 09.03.2017.
 */
public class ProjectileFactory {

    public static Entity createBasicProjectile (EntityManager ecs, GameWorld gameWorld, HitboxesSystem hitboxesSystem, String atlasPath, Direction direction, float x, float y, float speedX, float speedY) {
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
        entity.addComponent(new CollisionBoxesComponent(true), CollisionBoxesComponent.class);

        //add gameworld collision component
        entity.addComponent(new GameWorldCollisionComponent(gameWorld), GameWorldCollisionComponent.class);

        //add component to remove entity on gameworld collision
        entity.addComponent(new RemoveOnGameWorldCollisionComponent(), RemoveOnGameWorldCollisionComponent.class);

        //add attack component
        entity.addComponent(new AttackComponent(hitboxesSystem, new AttackAction(10, 0)), AttackComponent.class);

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
