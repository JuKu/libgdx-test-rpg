package com.jukusoft.libgdx.rpg.game;

import com.badlogic.gdx.Input;
import com.jukusoft.libgdx.rpg.engine.camera.impl.Shake1CameraModification;
import com.jukusoft.libgdx.rpg.engine.camera.impl.Shake2CameraModification;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.input.InputMapper;
import com.jukusoft.libgdx.rpg.engine.save.impl.DefaultSavedGameManager;
import com.jukusoft.libgdx.rpg.engine.screen.IScreen;
import com.jukusoft.libgdx.rpg.engine.screen.ScreenManager;
import com.jukusoft.libgdx.rpg.engine.version.GameVersion;
import com.jukusoft.libgdx.rpg.game.input.GameActionsConst;
import com.jukusoft.libgdx.rpg.game.screen.*;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

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

        //load version information
        this.setVersion(new GameVersion(settingsDir + "version.cfg"));

        //set saved game manager to support saved games
        this.setSavedGameManager(new DefaultSavedGameManager(AssetPathUtils.getSavePath()));

        //set shader path
        this.shaderPath = AssetPathUtils.getShaderPath("");

        //create input mapping
        InputMapper inputMapper = getInputManager().getInputMapper();

        //add mappings for movements
        inputMapper.addMapping(Input.Keys.W, GameActionsConst.MOVE_UP);
        inputMapper.addMapping(Input.Keys.S, GameActionsConst.MOVE_DOWN);
        inputMapper.addMapping(Input.Keys.A, GameActionsConst.MOVE_LEFT);
        inputMapper.addMapping(Input.Keys.D, GameActionsConst.MOVE_RIGHT);

        inputMapper.addMapping(Input.Keys.UP, GameActionsConst.MOVE_UP);
        inputMapper.addMapping(Input.Keys.DOWN, GameActionsConst.MOVE_DOWN);
        inputMapper.addMapping(Input.Keys.LEFT, GameActionsConst.MOVE_LEFT);
        inputMapper.addMapping(Input.Keys.RIGHT, GameActionsConst.MOVE_RIGHT);

        //activate camera shake modifications
        this.getCamera().activateMod(Shake1CameraModification.class);
        this.getCamera().activateMod(Shake2CameraModification.class);

        //create screen
        screenManager.addScreen("jukusoft_intro", new JuKuSoftIntroScreen());
        screenManager.addScreen("load_screen", new LoadScreen());
        screenManager.addScreen("menu", new MainMenuScreen());
        screenManager.addScreen("credits", new CreditsScreen());
        screenManager.addScreen("settings", new SettingsScreen());
        screenManager.addScreen("new_game", new CreateCharacterScreen());
        screenManager.addScreen("load_game", new LoadGameScreen());
        screenManager.addScreen("intro_story", new IntroStoryScreen());
        screenManager.addScreen("game", new GameScreen());
        screenManager.addScreen("hud", new HUDOverlayScreen());

        //push screen
        screenManager.push("jukusoft_intro");
    }

}
