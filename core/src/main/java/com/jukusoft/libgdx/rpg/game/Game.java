package com.jukusoft.libgdx.rpg.game;

import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.IScreen;
import com.jukusoft.libgdx.rpg.engine.screen.ScreenManager;
import com.jukusoft.libgdx.rpg.game.screen.JuKuSoftIntroScreen;

/**
 * Created by Justin on 06.02.2017.
 */
public class Game extends ScreenBasedGame {

    @Override
    protected void onCreateScreens(ScreenManager<IScreen> screenManager) {
        //create screen
        screenManager.addScreen("jukusoft_intro", new JuKuSoftIntroScreen());

        //push screen
        screenManager.push("jukusoft_intro");
    }

}
