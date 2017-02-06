package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.jukusoft.libgdx.rpg.engine.font.BitmapFontFactory;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 06.02.2017.
 */
public class LoadScreen extends BaseScreen {

    protected final String ICON_IMAGE_PATH = AssetPathUtils.getImagePath("general/icon_transparency_border.png");

    protected Texture logo = null;

    protected BitmapFont font = null;

    @Override public void onInit(ScreenBasedGame game, AssetManager assetManager) {
        assetManager.load(ICON_IMAGE_PATH, Texture.class);

        //generate font
        this.font = BitmapFontFactory.createFont(AssetPathUtils.getFontPath("spartakus/SparTakus.ttf"), 48, Color.WHITE, Color.BLUE, 3);
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

        //draw text
        this.font.draw(batch, "Loading...", 50, 80);
    }

    @Override public void destroy() {

    }

}
