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

    protected final String BG_IMAGE_PATH = AssetPathUtils.getWallpaperPath("ocean/Ocean_large.png");

    protected Texture bgImage = null;

    @Override public void onInit(ScreenBasedGame game, AssetManager assetManager) {
        assetManager.load(BG_IMAGE_PATH, Texture.class);
    }

    @Override
    public void onResume () {
        assetManager.finishLoading();

        this.bgImage = assetManager.get(BG_IMAGE_PATH, Texture.class);
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {

    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        batch.draw(this.bgImage, 0, 0);
    }

    @Override public void destroy() {

    }

}
