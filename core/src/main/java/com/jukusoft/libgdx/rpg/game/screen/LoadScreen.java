package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 06.02.2017.
 */
public class LoadScreen extends BaseScreen {

    protected final String ICON_IMAGE_PATH = AssetPathUtils.getImagePath("general/icon_transparency.png");

    protected Texture logo = null;

    @Override public void onInit(ScreenBasedGame game, AssetManager assetManager) {
        assetManager.load(ICON_IMAGE_PATH, Texture.class);
    }

    @Override
    public void onResume () {
        assetManager.finishLoading();

        this.logo = assetManager.get(ICON_IMAGE_PATH, Texture.class);
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {

    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //draw image to center
        batch.draw(this.logo, (game.getViewportWidth() - 400) / 2, game.getViewportHeight() - 200);
    }

    @Override public void destroy() {

    }

}
