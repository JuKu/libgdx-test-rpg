package com.jukusoft.libgdx.rpg.engine;

import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.EntityManager;
import com.jukusoft.libgdx.rpg.engine.entity.factory.ProjectileFactory;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.AttachmentPointsComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.MoveComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.PositionComponent;
import com.jukusoft.libgdx.rpg.engine.points.AttachmentPoint;
import com.jukusoft.libgdx.rpg.engine.utils.Direction;
import com.jukusoft.libgdx.rpg.engine.utils.SpeedUtils;

/**
 * Created by Justin on 09.03.2017.
 */
public class ProjectileSpawner {

    protected EntityManager ecs = null;

    public ProjectileSpawner (EntityManager ecs) {
        this.ecs = ecs;
    }

    public void spawn (Entity ownerEntity, String atlasFile, float speed) {
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

        System.out.println("spawn projectile into direction: " + direction.name());

        //get attachment point and x and y coordinate
        AttachmentPoint attachmentPoint = attachmentPointsComponent.getAttachmentPoint(direction);
        float x = attachmentPoint.getX(positionComponent);
        float y = attachmentPoint.getY(positionComponent);

        float speedX = SpeedUtils.getSpeedX(direction, speed);
        float speedY = SpeedUtils.getSpeedY(direction, speed);

        //create projectile entity
        Entity projectileEntity = ProjectileFactory.createBasicProjectile(this.ecs, atlasFile, direction, x, y, speedX, speedY);

        //add entity
        this.ecs.addEntity(projectileEntity);
    }

}
