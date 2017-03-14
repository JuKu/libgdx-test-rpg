package com.jukusoft.libgdx.rpg.engine.entity.listener;

import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.fightingsystem.AttackAction;

/**
 * Created by Justin on 14.03.2017.
 */
@FunctionalInterface
public interface AttackedListener {

    public void wasAttacked (Entity attackerEntity, Entity entity, AttackAction action);

}
