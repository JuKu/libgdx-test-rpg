package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.skin.SkinFactory;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 06.02.2017.
 */
public class MainMenuScreen extends BaseScreen {

    protected Texture bgImage = null;
    protected Image backgroundImage = null;
    protected final String BG_IMAGE_PATH = AssetPathUtils.getWallpaperPath("dragon1/dragon1.png");

    protected Stage uiStage = null;

    protected TextButton newGameButton = null;
    protected TextButton loadButton = null;
    protected TextButton creditsButton = null;
    protected TextButton settingsButton = null;

    protected Skin uiSkin = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        assetManager.load(BG_IMAGE_PATH, Texture.class);

        //create stage for user interface (UI)
        this.uiStage = new Stage();
        //this.uiStage.s
        Gdx.input.setInputProcessor(this.uiStage);

        //https://github.com/libgdx/libgdx/wiki/Scene2d.ui

        game.addResizeListener(((width, height) -> {
            //update viewport of stage
            uiStage.getViewport().update(width, height, true);
        }));

        //create and load ui skin from json file
        this.uiSkin = SkinFactory.createSkin(/*AssetPathUtils.getUISkinPath("libgdx", "uiskin.atlas"), */AssetPathUtils.getUISkinPath("libgdx", "uiskin.json"));

        //https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/badlogic/gdx/tests/UISimpleTest.java#L37

        //https://github.com/libgdx/libgdx/wiki/Scene2d.ui
    }

    @Override
    public void onResume () {
        //remove old screens
        game.getScreenManager().removeScreen("jukusoft_intro");
        game.getScreenManager().removeScreen("load_screen");

        //set input processor
        Gdx.input.setInputProcessor(this.uiStage);

        //wait while all assets was loaded
        game.getAssetManager().finishLoading();

        this.bgImage = game.getAssetManager().get(BG_IMAGE_PATH, Texture.class);

        this.backgroundImage = new Image(this.bgImage);
        this.backgroundImage.setPosition(0, 0);
        this.uiStage.addActor(this.backgroundImage);

        float startX = game.getViewportWidth() / 2 - 200;

        //create new game button
        this.newGameButton = new TextButton("New Game", this.uiSkin);
        this.newGameButton.setPosition(startX, 400);
        this.newGameButton.setWidth(400);
        this.newGameButton.setHeight(50);
        this.newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.getScreenManager().leaveAllAndEnter("new_game");
            }
        });
        this.uiStage.addActor(this.newGameButton);

        //create load game button
        this.loadButton = new TextButton("Load Game", this.uiSkin);
        this.loadButton.setPosition(startX, 320);
        this.loadButton.setWidth(400);
        this.loadButton.setHeight(50);
        this.loadButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.getScreenManager().leaveAllAndEnter("load_game");
            }
        });
        this.uiStage.addActor(this.loadButton);

        //create credits button
        this.creditsButton = new TextButton("Credits", this.uiSkin);
        this.creditsButton.setPosition(startX, 240);
        this.creditsButton.setWidth(400);
        this.creditsButton.setHeight(50);
        this.creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.getScreenManager().leaveAllAndEnter("credits");
            }
        });
        this.uiStage.addActor(this.creditsButton);

        //create credits button
        this.settingsButton = new TextButton("Settings", this.uiSkin);
        this.settingsButton.setPosition(startX, 160);
        this.settingsButton.setWidth(400);
        this.settingsButton.setHeight(50);
        this.settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                //game.getScreenManager().leaveAllAndEnter("settings");
            }
        });
        this.uiStage.addActor(this.settingsButton);
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {

    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //draw background
        batch.draw(this.bgImage, 0, 0);

        this.uiStage.draw();
    }

    @Override public void destroy() {

    }

}
