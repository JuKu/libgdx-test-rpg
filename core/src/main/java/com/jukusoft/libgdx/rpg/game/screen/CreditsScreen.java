package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jukusoft.libgdx.rpg.engine.font.BitmapFontFactory;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.ArrayUtils;
import com.jukusoft.libgdx.rpg.engine.utils.FileUtils;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Justin on 06.02.2017.
 */
public class CreditsScreen extends BaseScreen {

    protected final String ICON_IMAGE_PATH = AssetPathUtils.getImagePath("general/icon_transparency_border.png");
    protected final String MUSIC_PATH = AssetPathUtils.getMusicPath("EssentialGameAudiopack/FullScores/Orchestral_Scores/Ove_Melaa-Heaven_Sings.mp3");

    protected Texture logo = null;

    protected BitmapFont font = null;
    protected BitmapFont creditsFont = null;
    protected BitmapFont creditsLargeFont = null;

    protected float textStartPos = 200;

    protected Timer timer = null;

    protected ShapeRenderer shapeRenderer = null;

    protected String[] creditsLines = {
        "#Credits",
        " - credits"
    };

    protected Music backgroundMusic = null;

    @Override public void onInit(ScreenBasedGame game, AssetManager assetManager) {
        assetManager.load(ICON_IMAGE_PATH, Texture.class);

        //generate fonts
        this.font = BitmapFontFactory
            .createFont(AssetPathUtils.getFontPath("spartakus/SparTakus.ttf"), 48, Color.WHITE, Color.BLUE, 3);
        this.creditsFont = BitmapFontFactory.createFont(AssetPathUtils.getFontPath("spartakus/SparTakus.ttf"), 18, Color.WHITE);
        this.creditsLargeFont = BitmapFontFactory.createFont(AssetPathUtils.getFontPath("spartakus/SparTakus.ttf"), 28, Color.WHITE, Color.RED, 3);

        this.shapeRenderer = new ShapeRenderer();

        //read credits file
        try {
            this.creditsLines = ArrayUtils
                .convertStringListToArray(FileUtils.readLines("./data/credits/CREDITS.txt", StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            this.creditsLines = new String[]{
                "Could not load CREDITS file."
            };
        }
    }

    @Override
    public void onResume () {
        assetManager.finishLoading();

        this.logo = assetManager.get(ICON_IMAGE_PATH, Texture.class);

        //reset text start position
        this.textStartPos = 200;

        assetManager.load(MUSIC_PATH, Music.class);
        assetManager.finishLoading();

        this.backgroundMusic = assetManager.get(MUSIC_PATH, Music.class);
        this.backgroundMusic.setVolume(game.getVolume());
        this.backgroundMusic.play();
    }

    @Override
    public void onPause () {
        this.backgroundMusic.stop();
        this.backgroundMusic.dispose();
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        textStartPos += 50 * time.getDeltaTime();

        if ((this.textStartPos - (creditsLines.length * 40)) > 500) {
            //leave and enter new game state
            game.getScreenManager().leaveAllAndEnter("menu");
        }

        //check, if mouse is clicked
        if (Gdx.input.isTouched()) {
            //leave and enter new game state
            game.getScreenManager().leaveAllAndEnter("menu");
        }
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //this.creditsFont.draw(batch, "Credits", (game.getViewportWidth() - 80) / 2, this.textStartPos);

        //draw credits
        for (int i = 0; i < this.creditsLines.length; i++) {
            if (creditsLines[i].trim().startsWith("#")) {
                //larger text
                this.creditsLargeFont.draw(batch, creditsLines[i], /*(game.getViewportWidth() - 80) / 2*/80, this.textStartPos - (i * 40));
            } else {
                this.creditsFont.draw(batch, creditsLines[i], /*(game.getViewportWidth() - 80) / 2*/80, this.textStartPos - (i * 40));
            }
        }

        batch.end();

        //set camera matrix to shape renderer
        this.shapeRenderer.setProjectionMatrix(game.getCamera().getCombined());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        //draw rectangle on top to overlay credits text
        shapeRenderer.rect(0, game.getViewportHeight() - 200, game.getViewportWidth(), 200);

        //draw rectangle on bottom
        shapeRenderer.rect(0, 0, game.getViewportWidth(), 50);

        shapeRenderer.end();

        batch.begin();

        //draw image to center
        batch.draw(this.logo, (game.getViewportWidth() - 400) / 2, game.getViewportHeight() - 200);
    }

    @Override public void destroy() {
        this.timer.cancel();
        this.timer = null;

        this.font.dispose();
        this.creditsFont.dispose();
        this.creditsLargeFont.dispose();
        this.shapeRenderer.dispose();
        this.logo.dispose();
        this.backgroundMusic.dispose();
    }

}
