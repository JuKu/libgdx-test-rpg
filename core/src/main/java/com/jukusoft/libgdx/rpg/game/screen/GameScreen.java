package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.lighting.Lighting;
import com.jukusoft.libgdx.rpg.engine.lighting.LightingSystem;
import com.jukusoft.libgdx.rpg.engine.lighting.TextureLighting;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.data.CharacterData;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;
import com.jukusoft.libgdx.rpg.game.world.GameWorld;

/**
 * Created by Justin on 07.02.2017.
 */
public class GameScreen extends BaseScreen {

    protected CharacterData characterData = null;

    protected GameWorld gameWorld = null;

    protected Texture testTexture = null;
    protected String testTexturePath = AssetPathUtils.getImagePath("test/water.png");
    protected String lightMapPath = AssetPathUtils.getLightMapPath("lightmap1/light.png");
    protected Texture lightMap = null;

    //lighting system
    LightingSystem lightingSystem = null;

    //lightings
    Lighting testLighting = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        game.getAssetManager().load(testTexturePath, Texture.class);
        game.getAssetManager().load(lightMapPath, Texture.class);
        game.getAssetManager().finishLoading();

        this.testTexture = game.getAssetManager().get(testTexturePath, Texture.class);
        this.lightMap = game.getAssetManager().get(lightMapPath, Texture.class);

        //create game world
        this.gameWorld = new GameWorld(this.testTexture);

        //create new lighting system
        this.lightingSystem = new LightingSystem(game, game.getViewportWidth(), game.getViewportHeight());

        //create new test lighting
        this.testLighting = new TextureLighting(this.lightMap, 200, 200);
        this.lightingSystem.addLighting(this.testLighting);
    }

    @Override
    public void onResume () {
        this.characterData = new CharacterData();

        game.getSharedData().put("character_data", this.characterData);

        //set correct input processor
        game.getInputManager().setInputProcessor();

        //add hud screen overlay
        game.getScreenManager().push("hud");
    }

    @Override
    public void onPause () {
        //remove hud screen overlay
        game.getScreenManager().pop();
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            game.getCamera().translate(-1, 0, 0);

            System.out.println("");
        }

        //update lighting system
        this.lightingSystem.update(game, game.getCamera(), time);

        //update game world
        this.gameWorld.update(game, game.getCamera(), time);
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //draw lighting framebuffer first
        this.lightingSystem.drawFBO(time, game.getCamera(), batch);

        batch.setProjectionMatrix(game.getCamera().combined);

        //check, if lighting is enabled
        if (this.lightingSystem.isLightingEnabled()) {
            //set lighting shader, so lighting shader will be used
            this.gameWorld.setCurrentShader(this.lightingSystem.getLightingShader());
        } else {
            //reset shader
            this.gameWorld.setCurrentShader(null);
        }

        lightingSystem.getFBO().getColorBufferTexture().bind(1); //this is important! bind the FBO to the 2nd texture unit
        this.lightMap.bind(0); //we force the binding of a texture on first texture unit to avoid artefacts
        //this is because our default and ambiant shader dont use multi texturing...
        //youc can basically bind anything, it doesnt matter

        //draw game world
        this.gameWorld.draw(time, game.getCamera(), batch);

        batch.draw(testTexture, 0, 0);

        //draw lightmap (only for testing purposes)
        //batch.draw(lightingSystem.getFBO().getColorBufferTexture(), 0, 0);

        batch.flush();

        //reset shader, so default shader is used
        batch.setShader(null);
    }

    @Override public void destroy() {
        this.gameWorld.dispose();
        this.gameWorld = null;

        this.lightingSystem.dispose();
        this.lightingSystem = null;
    }

}
