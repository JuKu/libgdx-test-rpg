package com.jukusoft.libgdx.rpg.engine.projectile;

import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.EntityManager;
import com.jukusoft.libgdx.rpg.engine.entity.factory.ProjectileFactory;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.*;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw.DrawTextureRegionComponent;
import com.jukusoft.libgdx.rpg.engine.fightingsystem.HitboxesSystem;
import com.jukusoft.libgdx.rpg.engine.points.AttachmentPoint;
import com.jukusoft.libgdx.rpg.engine.utils.Direction;
import com.jukusoft.libgdx.rpg.engine.utils.SpeedUtils;
import com.jukusoft.libgdx.rpg.engine.world.GameWorld;

/**
 * Created by Justin on 09.03.2017.
 */
public class ProjectileSpawner {

    protected EntityManager ecs = null;
    protected GameWorld gameWorld = null;
    protected HitboxesSystem hitboxesSystem = null;

    public ProjectileSpawner (EntityManager ecs, GameWorld gameWorld, HitboxesSystem hitboxesSystem) {
        this.ecs = ecs;
        this.gameWorld = gameWorld;
        this.hitboxesSystem = hitboxesSystem;
    }

    public void spawn (Entity ownerEntity, String atlasFile, float speed, long ttl) {
        //get x and y position
        PositionComponent positionComponent = ownerEntity.getComponent(PositionComponent.class);
        AttachmentPointsComponent attachmentPointsComponent = ownerEntity.getComponent(AttachmentPointsComponent.class);
        MoveComponent moveComponent = ownerEntity.getComponent(MoveComponent.class);

        if (positionComponent == null) {
            throw new IllegalStateException("PositionComponent is required to spawn an projectile.");
        }

        if (attachmentPointsComponent == null) {
            throw new IllegalStateException("AttachmentPointsComponent is required to spawn an projectile.");
        }

        if (moveComponent == null) {
            throw new IllegalStateException("MoveComponent is required to spawn an projectile.");
        }

        //get direction
        Direction direction = moveComponent.getDirection();

        //System.out.println("spawn projectile into direction: " + direction.name());

        //get attachment point and x and y coordinate
        AttachmentPoint attachmentPoint = attachmentPointsComponent.getAttachmentPoint(direction);
        float x = attachmentPoint.getX(positionComponent);
        float y = attachmentPoint.getY(positionComponent);

        float speedX = SpeedUtils.getSpeedX(direction, speed);
        float speedY = SpeedUtils.getSpeedY(direction, speed);

        //create projectile entity
        Entity projectileEntity = ProjectileFactory.createBasicProjectile(this.ecs, this.gameWorld, this.hitboxesSystem, atlasFile, direction, x, y, speedX, speedY);

        DrawTextureRegionComponent textureRegionComponent = projectileEntity.getComponent(DrawTextureRegionComponent.class);

        if (textureRegionComponent != null) {
            float normSpeedX = speedX / Math.abs(speedX);
            float normSpeedY = speedY / Math.abs(speedY);

            if (speedX == 0) {
                normSpeedX = 0;
            }

            if (speedY == 0) {
                normSpeedY = 0;
            }

            //correct position
            if (speedY > 0) {
                x -= normSpeedY * (textureRegionComponent.getTextureRegion().getRegionWidth() / 2);
            } else {
                x += normSpeedY * (textureRegionComponent.getTextureRegion().getRegionWidth() / 2);
            }

            if (speedX > 0) {
                y -= normSpeedX * (textureRegionComponent.getTextureRegion().getRegionHeight() / 2);
            } else {
                y += normSpeedX * (textureRegionComponent.getTextureRegion().getRegionHeight() / 2);
            }
        }

        projectileEntity.getComponent(PositionComponent.class).setPosition(x, y);

        //add auto remove component
        projectileEntity.addComponent(new TimedAutoRemoveComponent(ttl), TimedAutoRemoveComponent.class);

        //add entity
        this.ecs.addEntity(projectileEntity);
    }

}
