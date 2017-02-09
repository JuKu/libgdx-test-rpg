package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jukusoft.libgdx.rpg.engine.font.BitmapFontFactory;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.hud.FilledBar;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.data.CharacterData;
import com.jukusoft.libgdx.rpg.engine.hud.HUD;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 08.02.2017.
 */
public class HUDOverlayScreen extends BaseScreen {

    protected CharacterData characterData = null;

    protected ShapeRenderer shapeRenderer = null;

    //HUD
    protected HUD hud = null;
    protected FilledBar healthBar = null;
    protected static final int FONT_SIZE = 18;

    //assets
    protected BitmapFont font = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        this.font = BitmapFontFactory
            .createFont(AssetPathUtils.getFontPath("arial/arial.ttf"), FONT_SIZE, Color.WHITE);
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

        //add health widget
        this.healthBar = new FilledBar(this.font);
        this.healthBar.setPosition(game.getViewportWidth() - 200, game.getViewportHeight() - 100);
        this.healthBar.setDimension(80, 20);
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

    }

}
