package com.jukusoft.libgdx.rpg.engine.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.screen.IScreen;
import com.jukusoft.libgdx.rpg.engine.screen.ScreenManager;
import com.jukusoft.libgdx.rpg.engine.screen.impl.DefaultScreenManager;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 06.02.2017.
 */
public abstract class ScreenBasedGame extends BaseGame {

    protected ScreenManager<IScreen> screenManager = null;

    public ScreenBasedGame () {
        super();

        this.screenManager = new DefaultScreenManager(this);
    }

    @Override
    protected final void update (GameTime time) {
        //update all screens
        this.screenManager.listActiveScreens().stream().forEach(screen -> {
            screen.update(time);
        });
    }

    @Override
    protected final void draw (GameTime time, SpriteBatch batch) {
        //draw all screens
        this.screenManager.listActiveScreens().stream().forEach(screen -> {
            screen.draw(time, batch);
        });
    }

    @Override
    protected final void initGame () {
        this.onCreateScreens(this.screenManager);
    }

    @Override
    protected final void destroyGame () {
        //pause all active screens
        this.screenManager.listActiveScreens().forEach(screen -> {
            screen.onPause();
        });

        this.onDestroyGame();
    }

    protected abstract void onCreateScreens (ScreenManager<IScreen> screenManager);

    protected void onDestroyGame () {
        //
    }

}
