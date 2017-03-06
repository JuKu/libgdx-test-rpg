package com.jukusoft.libgdx.rpg.engine.hud.actionbar;

import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 06.03.2017.
 */
public interface CustomHoverAdapter {

    public boolean isHovered (BaseGame game, ActionBarItem item, GameTime time);

}
