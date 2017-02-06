package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 06.02.2017.
 */
public class CreateCharacterScreen extends BaseScreen {

    protected final String MUSIC_PATH = AssetPathUtils.getMusicPath("EssentialGameAudiopack/Loops/Drum_Only_Loops/Ove_Melaa-DrumLoop_1_64BPM.mp3");

    protected Image bgImage = null;

    protected Music backgroundMusic = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {

    }

    @Override
    public void onResume () {
        //load all assets
        game.getAssetManager().load(MUSIC_PATH, Music.class);

        game.getAssetManager().finishLoading();

        //get and play background music as loop
        this.backgroundMusic = game.getAssetManager().get(MUSIC_PATH, Music.class);
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
