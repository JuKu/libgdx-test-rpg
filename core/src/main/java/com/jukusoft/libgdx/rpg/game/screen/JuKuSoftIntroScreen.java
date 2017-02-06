package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.IScreen;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 06.02.2017.
 */
public class JuKuSoftIntroScreen extends BaseScreen {

    protected final String INTRO_SCREEN_IMAGE_PATH = AssetPathUtils.getImagePath("intro/intro_screen.png");
    protected final String INTRO_SCREEN_SOUND = AssetPathUtils.getMusicPath("intro/rpg-intro-test.ogg");

    protected Texture bgImage = null;
    protected Sound sound = null;

    protected long startTime = 0l;

    @Override public void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //add listener for resizing
        game.addResizeListener((width, height) -> {
            //
        });

        //load background image
        assetManager.load(INTRO_SCREEN_IMAGE_PATH, Texture.class);

        //load intro music
        assetManager.load(INTRO_SCREEN_SOUND, Sound.class);

        //wait while all assets are loading
        assetManager.finishLoading();

        this.bgImage = assetManager.get(INTRO_SCREEN_IMAGE_PATH, Texture.class);
        this.sound = assetManager.get(INTRO_SCREEN_SOUND, Sound.class);
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        if (startTime == 0l) {
            this.startTime = time.getTime();
        }

        if (startTime + 2000 < time.getTime()) {
            game.getScreenManager().leaveAllAndEnter("load_screen");
        }
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        batch.draw(this.bgImage, 0, 0);


    }

    @Override
    public void onResume () {
        this.sound.play(1.0f);
    }

    @Override public void destroy() {
        this.bgImage.dispose();
        this.sound.dispose();
    }

}
