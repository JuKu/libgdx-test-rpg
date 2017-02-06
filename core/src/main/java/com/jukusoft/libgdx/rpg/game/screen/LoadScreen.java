package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jukusoft.libgdx.rpg.engine.font.BitmapFontFactory;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
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
public class LoadScreen extends BaseScreen {

    protected final String ICON_IMAGE_PATH = AssetPathUtils.getImagePath("general/icon_transparency_border.png");

    protected Texture logo = null;

    protected BitmapFont font = null;
    protected BitmapFont creditsFont = null;
    protected BitmapFont creditsLargeFont = null;

    protected float textStartPos = 200;

    protected final String[] LOADING_TEXT = {
        "Loading",
        "Loading.",
        "Loading..",
        "Loading..."
    };

    protected volatile int textIndex = 0;

    protected Timer timer = null;

    protected ShapeRenderer shapeRenderer = null;

    protected String[] creditsLines = {
        "#Credits",
        " - credits"
    };

    protected static final long MIN_LOAD_TIME = 5000l;
    protected long startTime = 0l;

    @Override public void onInit(ScreenBasedGame game, AssetManager assetManager) {
        assetManager.load(ICON_IMAGE_PATH, Texture.class);

        //generate fonts
        this.font = BitmapFontFactory.createFont(AssetPathUtils.getFontPath("spartakus/SparTakus.ttf"), 48, Color.WHITE, Color.BLUE, 3);
        this.creditsFont = BitmapFontFactory.createFont(AssetPathUtils.getFontPath("spartakus/SparTakus.ttf"), 18, Color.WHITE);
        this.creditsLargeFont = BitmapFontFactory.createFont(AssetPathUtils.getFontPath("spartakus/SparTakus.ttf"), 28, Color.WHITE, Color.RED, 3);

        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                textIndex = (textIndex + 1) % 4;
            }
        }, 0l, 300l);

        this.shapeRenderer = new ShapeRenderer();

        //read credits file
        try {
            this.creditsLines = ArrayUtils.convertStringListToArray(FileUtils.readLines("./data/credits/CREDITS.txt", StandardCharsets.UTF_8));
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
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        if (startTime == 0l) {
            startTime = time.getTime();
        }

        textStartPos += 50 * time.getDeltaTime();

        if (((startTime + MIN_LOAD_TIME) < time.getTime()) && this.hasLoadingFinished()) {
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
        this.shapeRenderer.setProjectionMatrix(game.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        //draw rectangle on top to overlay credits text
        shapeRenderer.rect(0, game.getViewportHeight() - 200, game.getViewportWidth(), 200);

        //draw rectangle on bottom
        shapeRenderer.rect(0, 0, game.getViewportWidth(), 150);

        shapeRenderer.end();

        batch.begin();

        //draw image to center
        batch.draw(this.logo, (game.getViewportWidth() - 400) / 2, game.getViewportHeight() - 200);

        //draw text
        this.font.draw(batch, LOADING_TEXT[textIndex], 50, 80);
    }

    @Override public void destroy() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
    }

    protected boolean hasLoadingFinished () {
        //check, if all assets was be loaded
        //this.assetManager.isLoaded()

        return true;
    }

}
