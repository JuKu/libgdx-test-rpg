package com.jukusoft.libgdx.rpg.engine.entity.impl;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ComponentPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public class MoveInputComponent extends BaseComponent implements IUpdateComponent {

    @Override public void update(BaseGame game, GameTime time) {

    }

    @Override public ComponentPriority getUpdateOrder() {
        return ComponentPriority.HIGH;
    }

}
