package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    protected final String BG_IMAGE_PATH = AssetPathUtils.getWallpaperPath("dragon1/dragon1.png");

    protected Stage uiStage = null;

    protected TextButton newGameButton = null;

    protected Skin uiSkin = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        assetManager.load(BG_IMAGE_PATH, Texture.class);

        //create stage for user interface (UI)
        this.uiStage = new Stage();
        Gdx.input.setInputProcessor(this.uiStage);

        //https://github.com/libgdx/libgdx/wiki/Scene2d.ui

        game.addResizeListener(((width, height) -> {
            //update viewport of stage
            uiStage.getViewport().update(width, height, true);
        }));

        //create and load ui skin from json file
        this.uiSkin = SkinFactory.createSkin(/*AssetPathUtils.getUISkinPath("libgdx", "uiskin.atlas"), */AssetPathUtils.getUISkinPath("libgdx", "uiskin.json"));

        this.newGameButton = new TextButton("New Game", this.uiSkin);
        this.uiStage.addActor(this.newGameButton);
    }

    @Override
    public void onResume () {
        //remove old screens
        game.getScreenManager().removeScreen("jukusoft_intro");
        game.getScreenManager().removeScreen("load_screen");

        //wait while all assets was loaded
        game.getAssetManager().finishLoading();

        this.bgImage = game.getAssetManager().get(BG_IMAGE_PATH, Texture.class);
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
