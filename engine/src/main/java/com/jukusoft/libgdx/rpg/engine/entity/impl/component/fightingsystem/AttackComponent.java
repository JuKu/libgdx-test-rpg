package com.jukusoft.libgdx.rpg.engine.entity.impl.component.fightingsystem;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.fightingsystem.AttackAction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 14.03.2017.
 */
public class AttackComponent extends BaseComponent {

    protected Map<Integer,AttackAction> actionMap = new ConcurrentHashMap<>();

    public AttackComponent (AttackAction... actions) {
        if (actions.length == 0) {
            System.out.println("no attack action specified.");
        }

        int i = 1;

        for (AttackAction action : actions) {
            actionMap.put(i, action);
            i++;
        }
    }

    public AttackAction getAttackAction (int attackAction) {
        return this.actionMap.get(attackAction);
    }

}
