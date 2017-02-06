package com.jukusoft.libgdx.rpg.game;

import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.IScreen;
import com.jukusoft.libgdx.rpg.engine.screen.ScreenManager;
import com.jukusoft.libgdx.rpg.game.screen.*;

/**
 * Created by Justin on 06.02.2017.
 */
public class Game extends ScreenBasedGame {

    public Game () {
        super();
    }

    @Override
    protected void onCreateScreens(ScreenManager<IScreen> screenManager) {
        //load settings
        this.loadSettings("game", "game.cfg");

        //create screen
        screenManager.addScreen("jukusoft_intro", new JuKuSoftIntroScreen());
        screenManager.addScreen("load_screen", new LoadScreen());
        screenManager.addScreen("menu", new MainMenuScreen());
        screenManager.addScreen("credits", new CreditsScreen());
        screenManager.addScreen("settings", new SettingsScreen());
        screenManager.addScreen("new_game", new CreateCharacterScreen());

        //push screen
        screenManager.push("jukusoft_intro");
    }

}
