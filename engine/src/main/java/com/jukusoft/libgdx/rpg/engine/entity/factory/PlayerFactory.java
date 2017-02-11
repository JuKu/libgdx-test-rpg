package com.jukusoft.libgdx.rpg.engine.entity.factory;

import com.badlogic.gdx.graphics.Texture;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.EntityManager;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.*;

/**
 * Created by Justin on 10.02.2017.
 */
public class PlayerFactory {

    public static Entity createPlayer (EntityManager ecs, Texture texture, float x, float y) {
        //create new entity
        Entity playerEntity = new Entity(ecs);

        //add new position component, because every entity has an position
        playerEntity.addComponent(new PositionComponent(200, 200), PositionComponent.class);

        //add an movement component, so entity can be moved by update() method
        playerEntity.addComponent(new MoveComponent(), MoveComponent.class);

        //add movement input component, so entity can be moved by player input (keys depends on your input mapping)
        playerEntity.addComponent(new MoveInputComponent(), MoveInputComponent.class);

        //add follow camera component, so camera is following player
        playerEntity.addComponent(new FollowCameraComponent(), FollowCameraComponent.class);

        //add texture component to draw player
        playerEntity.addComponent(new DrawTextureComponent(texture), DrawTextureComponent.class);

        return playerEntity;
    }

}
