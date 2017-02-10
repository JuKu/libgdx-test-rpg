package com.jukusoft.libgdx.rpg.engine.entity.impl;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.EntityManager;
import com.jukusoft.libgdx.rpg.engine.entity.IComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.EntityDrawOrderChangedListener;
import com.jukusoft.libgdx.rpg.engine.entity.priority.EntityUpdateOrderChangedListener;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 10.02.2017.
 */
public abstract class BaseECS implements EntityManager, EntityUpdateOrderChangedListener,
    EntityDrawOrderChangedListener {

    /**
    * list with entities
    */
    protected List<Entity> entityUpdateList = new ArrayList<>();

    /**
     * list with entities
     */
    protected List<Entity> entityDrawList = new ArrayList<>();

    /**
    * map with entities
    */
    protected Map<Long,Entity> entityMap = new ConcurrentHashMap<>();

    protected BaseGame game = null;

    public BaseECS (BaseGame game) {
        this.game = game;
    }

    @Override
    public void update (BaseGame game, GameTime time) {
        this.entityUpdateList.stream().forEach(entity -> {
            //update entity
            entity.update(game, time);
        });
    }

    @Override
    public void draw (GameTime time, Camera camera, SpriteBatch batch) {
        this.entityDrawList.stream().forEach(entity -> {
            //draw entity
            entity.draw(time, camera, batch);
        });
    }

    @Override
    public void onEntityUpdateOrderChanged() {
        //sort list
        Collections.sort(this.entityUpdateList, new Comparator<Entity>() {
            @Override public int compare(Entity o1, Entity o2) {
                return ((Integer) o1.getUpdateOrder().getValue()).compareTo(o2.getUpdateOrder().getValue());
            }
        });
    }

    @Override
    public void onEntityDrawOrderChanged() {
        //sort list
        Collections.sort(this.entityDrawList, new Comparator<Entity>() {
            @Override public int compare(Entity o1, Entity o2) {
                return ((Integer) o1.getDrawOrder().getValue()).compareTo(o2.getDrawOrder().getValue());
            }
        });
    }

    public void addEntity (Entity entity) {
        if (entity == null) {
            throw new NullPointerException("entity cannot be null.");
        }

        //initialize entity
        entity.init(this.game, this);

        synchronized (this.entityUpdateList) {
            this.entityUpdateList.add(entity);
        }

        synchronized (this.entityDrawList) {
            this.entityDrawList.add(entity);
        }

        //call listeners to sort lists
        this.onEntityUpdateOrderChanged();
        this.onEntityDrawOrderChanged();

        //call listeners
        this.onEntityAdded(entity);
    }

    public void removeEntity (Entity entity) {
        if (entity == null) {
            throw new NullPointerException("entity cannot be null.");
        }

        //get entityID
        final long entityID = entity.getEntityID();

        synchronized (this.entityUpdateList) {
            //remove entity
            this.entityUpdateList.remove(entity);
        }

        synchronized (this.entityDrawList) {
            //remove entity
            this.entityDrawList.remove(entity);
        }

        this.entityMap.remove(entityID);

        //call listeners
        onEntityRemoved(entity);

        //dispose entity
        entity.dispose();
    }

    @Override public void removeEntity(long entityID) {
        //get entity by entityID
        Entity entity = this.entityMap.get(entityID);

        if (entity != null) {
            this.removeEntity(entity);
        }
    }

    protected abstract void onEntityAdded (Entity entity);

    protected abstract void onEntityRemoved (Entity entity);

    public abstract <T extends IComponent> void onComponentAdded (Entity entity, T component, Class<T> cls);

    public abstract <T extends IComponent> void onComponentRemoved (Entity entity, T component, Class<T> cls);

}
