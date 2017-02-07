package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.jukusoft.libgdx.rpg.engine.font.BitmapFontFactory;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.story.StoryTeller;
import com.jukusoft.libgdx.rpg.engine.story.impl.DefaultStoryTeller;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;
import com.sun.javafx.font.FontFactory;

import java.io.IOException;

/**
 * Created by Justin on 06.02.2017.
 */
public class IntroStoryScreen extends BaseScreen {

    protected final String MUSIC_PATH = AssetPathUtils.getMusicPath("EssentialGameAudiopack/FullScores/Orchestral_Scores/Ove_Melaa-Heaven_Sings.mp3");
    protected final String BG_IMAGE_PATH = AssetPathUtils.getWallpaperPath("ocean_sunset/ocean.png");

    protected Music backgroundMusic = null;

    protected StoryTeller storyTeller = null;

    protected BitmapFont font = null;

    protected ShapeRenderer shapeRenderer = null;

    protected Color progressbarColor = Color.GREEN;

    protected Texture backgroundTexture = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //
    }

    @Override
    public void onResume () {
        assetManager.load(MUSIC_PATH, Music.class);
        assetManager.load(BG_IMAGE_PATH, Texture.class);

        assetManager.finishLoading();

        this.backgroundMusic = assetManager.get(MUSIC_PATH, Music.class);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.setVolume(game.getVolume());
        this.backgroundMusic.play();

        this.font = BitmapFontFactory.createFont(AssetPathUtils.getFontPath("arial/arial.ttf"), 26, Color.WHITE, new Color(0x1b52f1ff), 3);
        this.backgroundTexture = assetManager.get(BG_IMAGE_PATH, Texture.class);

        this.storyTeller = new DefaultStoryTeller(font);

        //load story
        try {
            this.storyTeller.load(AssetPathUtils.getStoryPath(game.getLang(), "intro1.lang"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException while loading story intro1.lang: " + e.getLocalizedMessage());
        }

        this.shapeRenderer = new ShapeRenderer();

        this.storyTeller.start();
    }

    @Override
    public void onPause () {
        this.backgroundMusic.stop();
        this.backgroundMusic.dispose();
        this.backgroundMusic = null;

        this.font.dispose();
        this.font = null;

        this.shapeRenderer.dispose();
        this.shapeRenderer = null;

        this.backgroundTexture.dispose();
        this.backgroundTexture = null;
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
        batch.draw(this.backgroundTexture, 0, 0);

        /*batch.end();

        this.shapeRenderer.setProjectionMatrix(game.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0x82828255));

        shapeRenderer.rect(20, 20, 400, 400);

        shapeRenderer.end();

        batch.begin();*/

        this.storyTeller.draw(time, batch, 100, 600, 40);

        batch.end();

        this.shapeRenderer.setProjectionMatrix(game.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(this.progressbarColor);

        //draw rectangle as progressbar
        shapeRenderer.rect(0, 20, game.getViewportWidth() * storyTeller.getPartProgress(time.getTime()), 10);

        shapeRenderer.end();

        batch.begin();
    }

    @Override public void destroy() {

    }

}
