package com.jukusoft.libgdx.rpg.engine.entity.impl;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.PositionChangedListener;
import com.jukusoft.libgdx.rpg.engine.json.InvalideJSONException;
import com.jukusoft.libgdx.rpg.engine.json.JSONLoadable;
import com.jukusoft.libgdx.rpg.engine.json.JSONSerializable;
import com.jukusoft.libgdx.rpg.engine.world.SectorCoord;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * An Thread Safe Data Holder for entities to store their position
 *
 * Created by Justin on 10.02.2017.
 */
public class PositionComponent extends BaseComponent implements JSONSerializable, JSONLoadable {

    protected volatile float x = 0;
    protected volatile float y = 0;

    //list with listeners
    protected List<PositionChangedListener> changedListenerList = new ArrayList<>();
    protected ReentrantLock listenerLock = new ReentrantLock();
    protected final int LOCK_TIMEOUT = 200;

    protected static final String COMPONENT_NAME = "PositionComponent";

    protected AtomicBoolean readOnly = new AtomicBoolean(false);

    public float getX () {
        return this.x;
    }

    public void setX (float x) {
        float oldX = this.x;
        this.x = x;

        this.notifyPositionChangedListener(oldX, x, this.y, this.y);
    }

    public float getY () {
        return this.y;
    }

    public void setY (float y) {
        float oldY = this.y;
        this.y = y;

        this.notifyPositionChangedListener(this.x, oldY, this.x, this.y);
    }

    public void setPosition (float x, float y) {
        //save old values
        float oldX = this.x;
        float oldY = this.y;

        this.x = x;
        this.y = y;

        this.notifyPositionChangedListener(oldX, oldY, x, y);
    }

    protected void notifyPositionChangedListener (float oldX, float oldY, float newX, float newY) {
        try {
            //lock list to avoid ConcurrentModificationException
            this.listenerLock.tryLock(LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldnt lock changedListenerList on PositionComponent: " + e.getLocalizedMessage());
        }

        //call listeners
        this.changedListenerList.stream().forEach(listener -> {
            listener.onPositionChanged(oldX, oldY, newX, newY);
        });

        this.listenerLock.unlock();
    }

    public void addPositionChangedListener (PositionChangedListener listener) {
        try {
            //lock list to avoid ConcurrentModificationException
            this.listenerLock.tryLock(LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldnt lock changedListenerList on PositionComponent: " + e.getLocalizedMessage());
        }

        this.changedListenerList.add(listener);

        this.listenerLock.unlock();
    }

    public void removePositionChangedListener (PositionChangedListener listener) {
        try {
            //lock list to avoid ConcurrentModificationException
            this.listenerLock.tryLock(LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldnt lock changedListenerList on PositionComponent: " + e.getLocalizedMessage());
        }

        this.changedListenerList.remove(listener);

        this.listenerLock.unlock();
    }

    @Override public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        //add values
        json.put("component_name", COMPONENT_NAME);
        json.put("x", this.x);
        json.put("y", this.y);

        return json;
    }

    @Override public void loadFromJSON(JSONObject json) {
        if (json == null) {
            throw new NullPointerException("json cannot be null.");
        }

        if (!json.has("x") || !json.has("y")) {
            throw new InvalideJSONException("JSON for PositionComponent is invalide! x and y position has to be set.");
        }

        this.x = Float.parseFloat(json.getString("x"));
        this.y = Float.parseFloat(json.getString("y"));
    }

    @Override
    public String toString () {
        return toJSON().toString();
    }

    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof PositionComponent)) {
            throw new IllegalArgumentException("object has to be an instance of PositionComponent to check, if equals.");
        }

        PositionComponent comp = (PositionComponent) obj;

        return this.x == comp.getX() && this.y == comp.getY();
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder(17, 37).
            append(this.x).
            append(this.y).
            toHashCode();
    }
}
