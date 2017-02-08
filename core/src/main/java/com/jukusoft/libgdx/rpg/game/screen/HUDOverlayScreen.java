package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.data.CharacterData;
import com.jukusoft.libgdx.rpg.game.hud.HUD;

/**
 * Created by Justin on 08.02.2017.
 */
public class HUDOverlayScreen extends BaseScreen {

    protected CharacterData characterData = null;

    protected ShapeRenderer shapeRenderer = null;
    protected HUD hud = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //
    }

    @Override
    public void onResume () {
        this.characterData = game.getSharedData().get("character_data", CharacterData.class);

        if (this.characterData == null) {
            throw new IllegalStateException("character data wasnt initialized yet.");
        }

        this.shapeRenderer = new ShapeRenderer();

        this.hud = new HUD();
    }

    @Override
    public void onPause () {
        this.shapeRenderer.dispose();
        this.shapeRenderer = null;
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
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
    }

    @Override public void destroy() {

    }

}
