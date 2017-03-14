package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.HPHitListener;
import com.jukusoft.libgdx.rpg.engine.entity.listener.UpdateHPListener;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 14.03.2017.
 */
public class HPComponent extends BaseComponent {

    protected float currentHP = 100;
    protected float maxHP = 100;
    protected float percent = 0;

    //HP update listener list
    protected List<UpdateHPListener> updateHPListenerList = new ArrayList<>();

    //HP hit listener list
    protected List<HPHitListener> hpHitListenerList = new ArrayList<>();

    public HPComponent (float currentHP, float maxHP) {
        setCurrentHP(currentHP);
        setMaxHP(maxHP);

        //update percent
        this.updatePercent();
    }

    public float getPercent () {
        return this.percent;
    }

    protected void updatePercent () {
        this.percent = this.currentHP / this.maxHP;
    }

    public float getMaxHP () {
        return this.maxHP;
    }

    public void setMaxHP (float maxHP) {
        this.maxHP = maxHP;

        this.updatePercent();
    }

    public float getCurrentHP () {
        return this.currentHP;
    }

    public void setCurrentHP (float currentHP) {
        float oldValue = this.currentHP;

        if (currentHP < 0) {
            this.currentHP = 0;
        } else if (currentHP > maxHP) {
            this.currentHP = maxHP;
        } else {
            this.currentHP = currentHP;
        }

        //update percent
        updatePercent();

        //notify update listeners
        this.notifyUpdateListener(oldValue, this.currentHP, this.maxHP);

        if (this.currentHP <= 0) {
            //character was hit
            this.notifyHitListener(oldValue, this.currentHP, this.maxHP);
        }
    }

    public void addHP (float hp) {
        setCurrentHP(getCurrentHP() + hp);
    }

    public void subHP (float hp) {
        setCurrentHP(getCurrentHP() - hp);
    }

    protected void notifyUpdateListener (float oldValue, float newValue, float maxValue) {
        this.updateHPListenerList.stream().forEach(listener -> {
            listener.onHPUpdate(oldValue, newValue, maxValue);
        });
    }

    public void addUpdateListener (UpdateHPListener listener) {
        this.updateHPListenerList.add(listener);
    }

    public void removeUpdateListener (UpdateHPListener listener) {
        this.updateHPListenerList.remove(listener);
    }

    protected void notifyHitListener (float oldValue, float newValue, float maxValue) {
        this.hpHitListenerList.stream().forEach(listener -> {
            listener.onHit(oldValue, newValue, maxValue);
        });
    }

    public void addHitListener (HPHitListener listener) {
        this.hpHitListenerList.add(listener);
    }

    public void removeHitListener (HPHitListener listener) {
        this.hpHitListenerList.remove(listener);
    }

}
