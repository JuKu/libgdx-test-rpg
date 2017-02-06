package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.story.StoryTeller;
import com.jukusoft.libgdx.rpg.engine.story.impl.DefaultStoryTeller;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

import java.io.IOException;

/**
 * Created by Justin on 06.02.2017.
 */
public class IntroStoryScreen extends BaseScreen {

    protected final String MUSIC_PATH = AssetPathUtils.getMusicPath("EssentialGameAudiopack/FullScores/Orchestral_Scores/Ove_Melaa-Heaven_Sings.mp3");

    protected Music backgroundMusic = null;

    protected StoryTeller storyTeller = null;

    protected BitmapFont font = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //
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

        this.font = new BitmapFont();

        this.storyTeller = new DefaultStoryTeller(font);

        //load story
        try {
            this.storyTeller.load(AssetPathUtils.getStoryPath(game.getLang(), "intro1.lang"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException while loading story intro1.lang: " + e.getLocalizedMessage());
        }

        this.storyTeller.start();
    }

    @Override
    public void onPause () {
        this.backgroundMusic.stop();
        this.backgroundMusic.dispose();
        this.backgroundMusic = null;

        this.font.dispose();
        this.font = null;
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        this.storyTeller.update(time);

        //check, if story was telled now
        if (this.storyTeller.hasFinished()) {
            //next screen
            game.getScreenManager().leaveAllAndEnter("game");
        }
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        this.storyTeller.draw(time, batch, 100, 800);
    }

    @Override public void destroy() {

    }

}
