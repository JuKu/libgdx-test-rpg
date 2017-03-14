package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 14.03.2017.
 */
public class HPComponent extends BaseComponent {

    protected float currentHP = 100;
    protected float maxHP = 100;
    protected float percent = 0;

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
        if (currentHP < 0) {
            this.currentHP = 0;
        } else if (currentHP > maxHP) {
            this.currentHP = maxHP;
        } else {
            this.currentHP = currentHP;
        }

        //update percent
        updatePercent();
    }

}
