package com.jukusoft.libgdx.rpg.engine.game;

import com.jukusoft.libgdx.rpg.engine.screen.IScreen;
import com.jukusoft.libgdx.rpg.engine.screen.ScreenManager;
import com.jukusoft.libgdx.rpg.engine.screen.impl.DefaultScreenManager;

/**
 * Created by Justin on 06.02.2017.
 */
public class ScreenBasedGame extends BaseGame {

    protected ScreenManager<IScreen> screenManager = null;

    public ScreenBasedGame () {
        super();

        this.screenManager = new DefaultScreenManager(this);
    }

}
