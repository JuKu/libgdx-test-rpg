package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jukusoft.libgdx.rpg.engine.font.BitmapFontFactory;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.hud.ImageWidget;
import com.jukusoft.libgdx.rpg.engine.lighting.LightingEnvironment;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.data.CharacterData;
import com.jukusoft.libgdx.rpg.engine.hud.HUD;
import com.jukusoft.libgdx.rpg.engine.hud.FilledIconBar;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 08.02.2017.
 */
public class HUDOverlayScreen extends BaseScreen {

    protected static final String HEART_ICON_PATH = AssetPathUtils.getImagePath("icons/heart/heart_32.png");
    protected static final String DIAMOND_ICON_PATH = AssetPathUtils.getImagePath("icons/diamond/diamond_32.png");
    protected static final String LOGO_PATH = AssetPathUtils.getImagePath("general/icon_transparency_smallest.png");

    protected CharacterData characterData = null;

    protected ShapeRenderer shapeRenderer = null;

    //HUD
    protected HUD hud = null;
    protected static final int FONT_SIZE = 18;
    protected FilledIconBar healthBar = null;
    protected FilledIconBar manaBar = null;
    protected ImageWidget logoImageWidget = null;

    //assets
    protected BitmapFont font = null;
    protected Texture heartTexture = null;
    protected Texture diamondTexture = null;
    protected Texture logoTexture = null;

    //lighting environment
    protected LightingEnvironment lightingEnvironment = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        this.font = BitmapFontFactory
            .createFont(AssetPathUtils.getFontPath("arial/arial.ttf"), FONT_SIZE, Color.WHITE);

        //load assets
        game.getAssetManager().load(HEART_ICON_PATH, Texture.class);
        game.getAssetManager().load(DIAMOND_ICON_PATH, Texture.class);
        game.getAssetManager().load(LOGO_PATH, Texture.class);

        //wait while all assets was loaded
        game.getAssetManager().finishLoading();

        this.heartTexture = game.getAssetManager().get(HEART_ICON_PATH, Texture.class);
        this.diamondTexture = game.getAssetManager().get(DIAMOND_ICON_PATH, Texture.class);
        this.logoTexture = game.getAssetManager().get(LOGO_PATH, Texture.class);
    }

    @Override
    public void onResume () {
        this.characterData = game.getSharedData().get("character_data", CharacterData.class);

        if (this.characterData == null) {
            throw new IllegalStateException("character data wasnt initialized yet.");
        }

        this.shapeRenderer = new ShapeRenderer();

        //get lighting environment

        //create new Head-up-Display (HUD)
        this.hud = new HUD();

        //create new widget group for health bar
        this.healthBar = new FilledIconBar(this.heartTexture, this.font);
        this.healthBar.setPosition(game.getViewportWidth() - 540, game.getViewportHeight() - /*106*/60);
        this.healthBar.setMaxValue(this.characterData.getMaxHealth());
        this.healthBar.setValue(this.characterData.getHealth());
        this.hud.addWidget(this.healthBar);

        //create new mana bar
        this.manaBar = new FilledIconBar(this.diamondTexture, this.font);
        this.manaBar.setPosition(game.getViewportWidth() - 680, game.getViewportHeight() - /*156*/60);
        this.manaBar.setMaxValue(this.characterData.getMaxMana());
        this.manaBar.setValue(this.characterData.getMana());
        this.hud.addWidget(this.manaBar);

        //add logo image widget
        this.logoImageWidget = new ImageWidget(this.logoTexture);
        this.logoImageWidget.setPosition(20, game.getViewportHeight() - 70);
        this.hud.addWidget(this.logoImageWidget);
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

        //update mana bar
        this.manaBar.setMaxValue(this.characterData.getMaxMana());
        this.manaBar.setValue(this.characterData.getMana());
        this.manaBar.setText(this.manaBar.getPercent() + "%");

        this.hud.update(game, time);
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //set user interface camera
        batch.setProjectionMatrix(game.getUICamera().combined);

        //reset shader, so default shader will be used
        //batch.setShader(null);

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

        this.diamondTexture.dispose();
        this.diamondTexture = null;

        this.logoTexture.dispose();
        this.logoTexture = null;
    }

}
