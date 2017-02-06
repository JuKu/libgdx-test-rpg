package com.jukusoft.libgdx.rpg.game;

import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.IScreen;
import com.jukusoft.libgdx.rpg.engine.screen.ScreenManager;
import com.jukusoft.libgdx.rpg.game.screen.JuKuSoftIntroScreen;
import com.jukusoft.libgdx.rpg.game.screen.LoadScreen;

/**
 * Created by Justin on 06.02.2017.
 */
public class Game extends ScreenBasedGame {

    public Game () {
        super();
    }

    @Override
    protected void onCreateScreens(ScreenManager<IScreen> screenManager) {
        //create screen
        screenManager.addScreen("jukusoft_intro", new JuKuSoftIntroScreen());
        screenManager.addScreen("load_screen", new LoadScreen());

        //push screen
        screenManager.push("jukusoft_intro");
    }

}
