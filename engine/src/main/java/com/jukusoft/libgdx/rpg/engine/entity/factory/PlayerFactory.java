package com.jukusoft.libgdx.rpg.engine.entity.factory;

import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.FollowCameraComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.MoveComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.MoveInputComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.PositionComponent;

/**
 * Created by Justin on 10.02.2017.
 */
public class PlayerFactory {

    public static Entity createPlayer (float x, float y) {
        //create new entity
        Entity playerEntity = new Entity();

        //add new position component, because every entity has an position
        playerEntity.addComponent(new PositionComponent(200, 200), PositionComponent.class);

        //add an movement component, so entity can be moved by update() method
        playerEntity.addComponent(new MoveComponent(), MoveComponent.class);

        //add movement input component, so entity can be moved by player input (keys depends on your input mapping)
        playerEntity.addComponent(new MoveInputComponent(), MoveInputComponent.class);

        //add follow camera component, so camera is following player
        playerEntity.addComponent(new FollowCameraComponent(), FollowCameraComponent.class);

        return playerEntity;
    }

}
