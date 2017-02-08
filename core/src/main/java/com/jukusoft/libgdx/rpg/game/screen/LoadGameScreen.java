package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.skin.SkinFactory;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 08.02.2017.
 */
public class LoadGameScreen extends BaseScreen {

    protected final String BG_IMAGE_PATH = AssetPathUtils.getWallpaperPath("ocean_sunset/ocean.png");

    protected Texture backgroundTexture = null;

    //user interface
    protected Stage stage = null;
    protected Skin uiSkin = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {

    }

    @Override
    public void onResume () {
        //load assets
        this.assetManager.load(BG_IMAGE_PATH, Texture.class);

        //create and load ui skin from json file
        this.uiSkin = SkinFactory.createSkin(/*AssetPathUtils.getUISkinPath("libgdx", "uiskin.atlas"), */AssetPathUtils.getUISkinPath("create_character", "uiskin.json"));

        //create new stage
        this.stage = new Stage();

        //set input processor
        Gdx.input.setInputProcessor(this.stage);

        //wait while all assets was loaded
        this.assetManager.finishLoading();

        //get assets
        this.backgroundTexture = this.assetManager.get(BG_IMAGE_PATH, Texture.class);
    }

    @Override
    public void onPause () {
        //free memory for assets
        this.backgroundTexture.dispose();
        this.backgroundTexture = null;
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {

    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //draw background image
        batch.draw(this.backgroundTexture, 0, 0);

        //execute OpenGL render calls, because stage uses its own sprite batcher
        batch.flush();

        //draw user interface
        this.stage.draw();
    }

    @Override public void destroy() {

    }

}