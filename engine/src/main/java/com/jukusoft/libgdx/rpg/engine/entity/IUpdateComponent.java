package com.jukusoft.libgdx.rpg.engine.entity;

import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public interface IUpdateComponent {

    /**
    * update
    */
    public void update (BaseGame game, GameTime time);

}
