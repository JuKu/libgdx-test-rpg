package com.jukusoft.libgdx.rpg.engine.entity.factory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.EntityManager;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.*;

/**
 * Created by Justin on 15.02.2017.
 */
public class NPCFactory {

    public static Entity createDummyNPC (EntityManager ecs, Texture texture, Pixmap cursor, float x, float y) {
        //create new entity
        Entity npcEntity = new Entity(ecs);

        //add new position component, because every entity has an position
        npcEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add an movement component, so entity can be moved by update() method
        npcEntity.addComponent(new MoveComponent(), MoveComponent.class);

        //add texture component to draw player
        npcEntity.addComponent(new DrawTextureComponent(texture), DrawTextureComponent.class);

        //add hover component
        npcEntity.addComponent(new HoverComponent(Color.RED), HoverComponent.class);

        //add specific attack cursor
        npcEntity.addComponent(new CursorComponent(cursor), CursorComponent.class);

        //add shadow component
        npcEntity.addComponent(new ShadowComponent(), ShadowComponent.class);

        return npcEntity;
    }

    public static Entity createDummyWithBlobShadowNPC (EntityManager ecs, Texture texture, Pixmap cursor, float x, float y) {
        //create new entity
        Entity npcEntity = new Entity(ecs);

        //add new position component, because every entity has an position
        npcEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add an movement component, so entity can be moved by update() method
        npcEntity.addComponent(new MoveComponent(), MoveComponent.class);

        //add texture component to draw player
        npcEntity.addComponent(new DrawTextureComponent(texture), DrawTextureComponent.class);

        //add hover component
        npcEntity.addComponent(new HoverComponent(Color.RED), HoverComponent.class);

        //add specific attack cursor
        npcEntity.addComponent(new CursorComponent(cursor), CursorComponent.class);

        //add shadow component
        npcEntity.addComponent(new BlobShadowComponent(10), BlobShadowComponent.class);

        return npcEntity;
    }

}
