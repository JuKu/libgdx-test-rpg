package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.screen.IScreen;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 06.02.2017.
 */
public class JuKuSoftIntroScreen extends BaseScreen {

    protected final String INTRO_SCREEN_IMAGE_PATH = AssetPathUtils.getImagePath("intro/intro_screen.png");

    protected Texture bgImage = null;

    @Override public void init(BaseGame game, AssetManager assetManager) {
        //add listener for resizing
        game.addResizeListener((width, height) -> {
            //
        });

        //load background image
        assetManager.load(INTRO_SCREEN_IMAGE_PATH, Texture.class);

        //wait while all assets are loading
        assetManager.finishLoading();

        this.bgImage = assetManager.get(INTRO_SCREEN_IMAGE_PATH, Texture.class);
    }

    @Override public void update(GameTime time) {
        //
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //
    }

    @Override public void destroy() {
        //
    }

}
