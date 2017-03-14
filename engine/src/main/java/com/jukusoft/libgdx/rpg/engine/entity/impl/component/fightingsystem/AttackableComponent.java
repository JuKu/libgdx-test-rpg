package com.jukusoft.libgdx.rpg.engine.entity.impl.component.fightingsystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.HPComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.PositionComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw.DrawTextureComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw.DrawTextureRegionComponent;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;

/**
 * Created by Justin on 14.03.2017.
 */
public class AttackableComponent extends BaseComponent {

    protected HPComponent hpComponent = null;

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
     * @see com.jukusoft.libgdx.rpg.engine.fightingsystem.AttackAction
    */
    public void attack (Entity attackerEntity, int attackAction) {
        //
    }

    protected float calculateDamage (Entity attackerEntity, int attackAction) {
        throw new UnsupportedOperationException("method isnt implemented yet.");
    }

}
