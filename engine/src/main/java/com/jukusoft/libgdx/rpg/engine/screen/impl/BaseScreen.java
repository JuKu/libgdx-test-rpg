package com.jukusoft.libgdx.rpg.engine.screen.impl;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.IScreen;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 06.02.2017.
 */
public abstract class BaseScreen implements IScreen {

    protected ScreenBasedGame game;
    protected AssetManager assetManager;

    public final void init (ScreenBasedGame game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;

        this.onInit(game, assetManager);
    }

    protected abstract void onInit (ScreenBasedGame game, AssetManager assetManager);

    @Override public void onPause() {

    }

    @Override public void onResume() {

    }

}
