package com.jukusoft.libgdx.rpg.engine.entity.factory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.EntityManager;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.*;
import com.jukusoft.libgdx.rpg.engine.world.ShadowEnv;

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
        playerEntity.addComponent(new FollowCameraComponent(), FollowCameraComponent.class);

        //add texture component to draw player
        //playerEntity.addComponent(new DrawTextureComponent(texture), DrawTextureComponent.class);
        playerEntity.addComponent(new DrawTextureRegionComponent(), DrawTextureRegionComponent.class);

        //add animation component
        playerEntity.addComponent(new AtlasAnimationComponent(atlasPath, startAnimationName, 1f), AtlasAnimationComponent.class);

        //add attachment points component so projectiles can be spawn at entity
        playerEntity.addComponent(new AttachmentPointsComponent(), AttachmentPointsComponent.class);

        //add hover component
        playerEntity.addComponent(new HoverComponent(Color.BLUE), HoverComponent.class);

        //add shadow component
        //playerEntity.addComponent(new ShadowComponent(), ShadowComponent.class);
        playerEntity.addComponent(new BlobShadowComponent(10 + 32, 32), BlobShadowComponent.class);

        return playerEntity;
    }

}
