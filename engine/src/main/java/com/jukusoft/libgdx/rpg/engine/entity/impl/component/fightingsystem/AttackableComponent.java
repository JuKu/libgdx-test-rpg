package com.jukusoft.libgdx.rpg.engine.entity.impl.component.fightingsystem;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.HPComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.AttackedListener;
import com.jukusoft.libgdx.rpg.engine.fightingsystem.AttackAction;
import com.jukusoft.libgdx.rpg.engine.fightingsystem.AttackActions;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 14.03.2017.
 */
public class AttackableComponent extends BaseComponent {

    protected HPComponent hpComponent = null;

    //attacked listener list
    protected List<AttackedListener> attackedListenerList = new ArrayList<>();

    public AttackableComponent () {
        //
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.hpComponent = entity.getComponent(HPComponent.class);

        if (this.hpComponent == null) {
            throw new IllegalStateException("entity doesnt have an HPComponent.");
        }
    }

    /**
    * attack entity
     *
     * @param attackerEntity entity which attacks this entity
     * @param attackAction attack action
     *
     * @see AttackActions
    */
    public void attack (Entity attackerEntity, int attackAction) {
        //get attack component
        AttackComponent attackComponent = attackerEntity.getComponent(AttackComponent.class);

        if (attackComponent == null) {
            throw new IllegalStateException("entity cannot attack, because it has no AttackComponent.");
        }

        //get attack action
        AttackAction action = attackComponent.getAttackAction(attackAction);

        if (action == null) {
            throw new IllegalStateException("attack action doesnt exists.");
        }

        //calculate damage
        float damage = this.calculateDamage(attackerEntity, action);

        //reduce HP by damage
        this.hpComponent.subHP(damage);

        //call listeners
        this.notifyAttackedListeners(attackerEntity, this.entity, action);
    }

    protected float calculateDamage (Entity attackerEntity, AttackAction action) {
        throw new UnsupportedOperationException("method isnt implemented yet.");
    }

    protected void notifyAttackedListeners (Entity attackerEntity, Entity entity, AttackAction action) {
        this.attackedListenerList.stream().forEach(listener -> {
            listener.wasAttacked(attackerEntity, entity, action);
        });
    }

    public void addAttackedListener (AttackedListener listener) {
        this.attackedListenerList.add(listener);
    }

    public void removeAttackedListener (AttackedListener listener) {
        this.attackedListenerList.remove(listener);
    }

}
