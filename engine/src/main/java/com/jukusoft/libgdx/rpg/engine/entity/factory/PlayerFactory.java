package com.jukusoft.libgdx.rpg.engine.entity.factory;

import com.badlogic.gdx.graphics.Color;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.EntityManager;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.*;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.camera.SmoothFollowCameraComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.input.HoverComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.input.MoveInputComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.input.RelativeMousePositionComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.shadow.BlobShadowComponent;

/**
 * Created by Justin on 10.02.2017.
 */
public class PlayerFactory {

    public static Entity createPlayer (EntityManager ecs, String atlasPath, String startAnimationName, float x, float y) {
        //create new entity
        Entity playerEntity = new Entity(ecs);

        //add new position component, because every entity has an position
        playerEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add an movement component, so entity can be moved by update() method
        playerEntity.addComponent(new MoveComponent(), MoveComponent.class);

        //add movement input component, so entity can be moved by player input (keys depends on your input mapping)
        playerEntity.addComponent(new MoveInputComponent(), MoveInputComponent.class);

        //add follow camera component, so camera is following player
        playerEntity.addComponent(new SmoothFollowCameraComponent(), SmoothFollowCameraComponent.class);

        //add texture component to draw player
        //playerEntity.addComponent(new DrawTextureComponent(texture), DrawTextureComponent.class);
        playerEntity.addComponent(new DrawTextureRegionComponent(), DrawTextureRegionComponent.class);

        //add animation component
        playerEntity.addComponent(new AtlasAnimationComponent(atlasPath, startAnimationName, 1f), AtlasAnimationComponent.class);

        //add component to get mouse position relative to entity
        playerEntity.addComponent(new RelativeMousePositionComponent(), RelativeMousePositionComponent.class);

        //add component to control movement animations
        playerEntity.addComponent(new BasicMovementAnimationControlComponent(), BasicMovementAnimationControlComponent.class);

        //add hitbox component
        playerEntity.addComponent(new HitBoxesComponent(true, 36, 74), HitBoxesComponent.class);

        //add attachment points component so projectiles can be spawn at entity
        playerEntity.addComponent(new AttachmentPointsComponent(), AttachmentPointsComponent.class);

        //add hover component
        playerEntity.addComponent(new HoverComponent(Color.BLUE), HoverComponent.class);

        //add shadow component
        //playerEntity.addComponent(new ShadowComponent(), ShadowComponent.class);
        playerEntity.addComponent(new BlobShadowComponent(10 + 32 + 10, 32), BlobShadowComponent.class);

        return playerEntity;
    }

}
