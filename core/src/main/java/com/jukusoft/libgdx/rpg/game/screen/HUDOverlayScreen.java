package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jukusoft.libgdx.rpg.engine.font.BitmapFontFactory;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.data.CharacterData;
import com.jukusoft.libgdx.rpg.engine.hud.HUD;
import com.jukusoft.libgdx.rpg.game.hud.FilledIconBar;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 08.02.2017.
 */
public class HUDOverlayScreen extends BaseScreen {

    protected static final String HEART_ICON_PATH = AssetPathUtils.getImagePath("icons/heart/heart_32.png");

    protected CharacterData characterData = null;

    protected ShapeRenderer shapeRenderer = null;

    //HUD
    protected HUD hud = null;
    //protected FilledBar healthBar = null;
    protected static final int FONT_SIZE = 18;
    //protected ImageWidget heartImageWidget = null;
    //protected WidgetGroup heartWidgetGroup = null;
    protected FilledIconBar healthBar = null;

    //assets
    protected BitmapFont font = null;
    protected Texture heartTexture = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        this.font = BitmapFontFactory
            .createFont(AssetPathUtils.getFontPath("arial/arial.ttf"), FONT_SIZE, Color.WHITE);

        //load assets
        game.getAssetManager().load(HEART_ICON_PATH, Texture.class);

        //wait while all assets was loaded
        game.getAssetManager().finishLoading();

        this.heartTexture = game.getAssetManager().get(HEART_ICON_PATH, Texture.class);
    }

    @Override
    public void onResume () {
        this.characterData = game.getSharedData().get("character_data", CharacterData.class);

        if (this.characterData == null) {
            throw new IllegalStateException("character data wasnt initialized yet.");
        }

        this.shapeRenderer = new ShapeRenderer();

        //create new Head-up-Display (HUD)
        this.hud = new HUD();

        //create new widget group for health bar
        this.healthBar = new FilledIconBar(this.heartTexture, this.font);
        this.healthBar.setPosition(game.getViewportWidth() - 240, game.getViewportHeight() - 106);
        this.healthBar.setMaxValue(this.characterData.getMaxHealth());
        this.healthBar.setValue(this.characterData.getHealth());
        this.hud.addWidget(this.healthBar);
    }

    @Override
    public void onPause () {
        if (this.shapeRenderer != null) {
            this.shapeRenderer.dispose();
            this.shapeRenderer = null;
        }
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        //update health bar
        this.healthBar.setMaxValue(this.characterData.getMaxHealth());
        this.healthBar.setValue(this.characterData.getHealth());
        this.healthBar.setText(this.healthBar.getPercent() + "%");

        this.hud.update(game, time);
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //set user interface camera
        batch.setProjectionMatrix(game.getUICamera().combined);

        this.hud.drawLayer0(time, batch);

        batch.end();

        //set camera matrix to shape renderer
        this.shapeRenderer.setProjectionMatrix(game.getUICamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        this.hud.drawLayer1(time, shapeRenderer);

        shapeRenderer.end();

        batch.begin();

        this.hud.drawLayer2(time, batch);
    }

    @Override public void destroy() {
        this.heartTexture.dispose();
        this.heartTexture = null;
    }

}
