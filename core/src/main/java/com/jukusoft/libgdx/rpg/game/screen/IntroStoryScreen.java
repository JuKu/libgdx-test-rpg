package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 06.02.2017.
 */
public class IntroStoryScreen extends BaseScreen {

    protected final String MUSIC_PATH = AssetPathUtils.getMusicPath("EssentialGameAudiopack/FullScores/Orchestral_Scores/Ove_Melaa-Heaven_Sings.mp3");

    protected Music backgroundMusic = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {

    }

    @Override
    public void onResume () {
        assetManager.load(MUSIC_PATH, Music.class);
        assetManager.finishLoading();

        assetManager.finishLoading();

        this.backgroundMusic = assetManager.get(MUSIC_PATH, Music.class);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.setVolume(game.getVolume());
        this.backgroundMusic.play();
    }

    @Override
    public void onPause () {
        this.backgroundMusic.stop();
        this.backgroundMusic.dispose();
        this.backgroundMusic = null;
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {

    }

    @Override public void draw(GameTime time, SpriteBatch batch) {

    }

    @Override public void destroy() {

    }

}
