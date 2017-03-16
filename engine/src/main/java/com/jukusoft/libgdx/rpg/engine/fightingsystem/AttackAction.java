package com.jukusoft.libgdx.rpg.engine.fightingsystem;

import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.HPComponent;

/**
 * Created by Justin on 14.03.2017.
 */
public class AttackAction {

    protected float bruttoDamage = 10;
    protected float magicDamage = 10;

    public AttackAction (float damage, float magicDamage) {
        this.bruttoDamage = damage;
        this.magicDamage = magicDamage;
    }

    public void attack (Entity attacker, Entity enemyEntity, HPComponent enemyHPComponent) {
        float damage = calculateDamage();

        System.out.println("NPC damage: " + damage);

        //reduce HP by damage
        enemyHPComponent.subHP(damage);
    }

    protected float calculateDamage () {
        return this.bruttoDamage + this.magicDamage;
    }

}
