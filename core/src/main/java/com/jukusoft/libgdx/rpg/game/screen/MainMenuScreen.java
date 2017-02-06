package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 06.02.2017.
 */
public class MainMenuScreen extends BaseScreen {

    protected Texture bgImage = null;

    protected final String BG_IMAGE_PATH = "dragon1/dragon1.png";

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        assetManager.load(AssetPathUtils.getWallpaperPath(BG_IMAGE_PATH), Texture.class);
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
    }

    @Override public void destroy() {

    }

}
