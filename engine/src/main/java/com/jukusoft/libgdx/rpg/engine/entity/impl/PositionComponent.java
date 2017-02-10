package com.jukusoft.libgdx.rpg.engine.entity.impl;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.PositionChangedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * An Thread Safe Data Holder for entities to store their position
 *
 * Created by Justin on 10.02.2017.
 */
public class PositionComponent extends BaseComponent {

    protected volatile float x = 0;
    protected volatile float y = 0;

    //list with listeners
    protected List<PositionChangedListener> changedListenerList = new ArrayList<>();
    protected ReentrantLock listenerLock = new ReentrantLock();
    protected final int LOCK_TIMEOUT = 200;

    public float getX () {
        return this.x;
    }

    public void setX (float x) {
        this.x = x;
    }

    public float getY () {
        return this.y;
    }

    public void setY (float y) {
        this.y = y;
    }

    public void setPosition (float x, float y) {
        this.x = x;
        this.y = y;
    }

    protected void notifyPositionChangedListener () {
        //
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

}
