package com.jukusoft.libgdx.rpg.game;

import com.badlogic.gdx.Input;
import com.jukusoft.libgdx.rpg.engine.camera.impl.Shake1CameraModification;
import com.jukusoft.libgdx.rpg.engine.camera.impl.Shake2CameraModification;
import com.jukusoft.libgdx.rpg.engine.camera.impl.Shake3CameraModification;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.input.InputMapper;
import com.jukusoft.libgdx.rpg.engine.save.impl.DefaultSavedGameManager;
import com.jukusoft.libgdx.rpg.engine.screen.IScreen;
import com.jukusoft.libgdx.rpg.engine.screen.ScreenManager;
import com.jukusoft.libgdx.rpg.engine.version.GameVersion;
import com.jukusoft.libgdx.rpg.game.input.GameActionsConst;
import com.jukusoft.libgdx.rpg.game.screen.*;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;
import org.apache.log4j.BasicConfigurator;

/**
 * Created by Justin on 06.02.2017.
 */
public class Game extends ScreenBasedGame {

    public Game () {
        super();

        //configure log4j
        BasicConfigurator.configure();
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

        //WASD keys
        inputMapper.addMapping(Input.Keys.W, GameActionsConst.MOVE_UP);
        inputMapper.addMapping(Input.Keys.S, GameActionsConst.MOVE_DOWN);
        inputMapper.addMapping(Input.Keys.A, GameActionsConst.MOVE_LEFT);
        inputMapper.addMapping(Input.Keys.D, GameActionsConst.MOVE_RIGHT);

        //arrow keys
        inputMapper.addMapping(Input.Keys.UP, GameActionsConst.MOVE_UP);
        inputMapper.addMapping(Input.Keys.DOWN, GameActionsConst.MOVE_DOWN);
        inputMapper.addMapping(Input.Keys.LEFT, GameActionsConst.MOVE_LEFT);
        inputMapper.addMapping(Input.Keys.RIGHT, GameActionsConst.MOVE_RIGHT);

        //action keys
        inputMapper.addMapping(Input.Keys.NUM_1, GameActionsConst.ACTION_3);
        inputMapper.addMapping(Input.Keys.NUM_2, GameActionsConst.ACTION_4);
        inputMapper.addMapping(Input.Keys.NUM_3, GameActionsConst.ACTION_5);
        inputMapper.addMapping(Input.Keys.NUM_4, GameActionsConst.ACTION_6);
        inputMapper.addMapping(Input.Keys.NUM_5, GameActionsConst.ACTION_7);
        inputMapper.addMapping(Input.Keys.Q, GameActionsConst.ACTION_8);
        inputMapper.addMapping(Input.Keys.E, GameActionsConst.ACTION_9);
        inputMapper.addMapping(Input.Keys.R, GameActionsConst.ACTION_10);


        //activate camera shake modifications
        this.getCamera().activateMod(Shake1CameraModification.class);
        this.getCamera().activateMod(Shake2CameraModification.class);
        this.getCamera().activateMod(Shake3CameraModification.class);

        //create screen
        screenManager.addScreen("jukusoft_intro", new JuKuSoftIntroScreen());
        screenManager.addScreen("load_screen", new LoadScreen());
        screenManager.addScreen("menu", new MainMenuScreen());
        screenManager.addScreen("credits", new CreditsScreen());
        screenManager.addScreen("settings", new SettingsScreen());
        screenManager.addScreen("new_game", new CreateCharacterScreen());
        screenManager.addScreen("load_game", new LoadGameScreen());
        screenManager.addScreen("multiplayer_login", new MultiplayerLoginScreen());
        screenManager.addScreen("multiplayer_game", new MultiplayerGameScreen());
        screenManager.addScreen("intro_story", new IntroStoryScreen());
        screenManager.addScreen("game", new GameScreen());
        screenManager.addScreen("hud", new HUDOverlayScreen());

        //push screen
        screenManager.push("jukusoft_intro");
    }

}
