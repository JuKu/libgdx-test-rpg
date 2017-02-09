package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.data.CharacterData;
import com.jukusoft.libgdx.rpg.game.world.GameWorld;

/**
 * Created by Justin on 07.02.2017.
 */
public class GameScreen extends BaseScreen {

    protected CharacterData characterData = null;

    protected GameWorld gameWorld = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //
    }

    @Override
    public void onResume () {
        this.characterData = new CharacterData();

        game.getSharedData().put("character_data", this.characterData);

        //add hud screen overlay
        game.getScreenManager().push("hud");

        //create game world
        this.gameWorld = new GameWorld();
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

        //update game world
        this.gameWorld.update(game, game.getCamera(), time);
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        batch.setProjectionMatrix(game.getCamera().combined);

        //draw game world
        this.gameWorld.draw(time, game.getCamera(), batch);
    }

    @Override public void destroy() {

    }

}
