package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.data.CharacterData;

/**
 * Created by Justin on 07.02.2017.
 */
public class GameScreen extends BaseScreen {

    protected CharacterData characterData = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //
    }

    @Override
    public void onResume () {
        this.characterData = new CharacterData();

        game.getSharedData().put("character_data", this.characterData);

        //add hud screen overlay
        game.getScreenManager().push("hud");
    }

    @Override
    public void onPause () {
        //remove hud screen overlay
        game.getScreenManager().pop();
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {

    }

    @Override public void draw(GameTime time, SpriteBatch batch) {

    }

    @Override public void destroy() {

    }

}
