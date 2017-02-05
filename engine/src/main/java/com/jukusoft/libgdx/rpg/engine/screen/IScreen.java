package com.jukusoft.libgdx.rpg.engine.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 06.02.2017.
 */
public interface IScreen {

    /**
    * initialize game screen
    */
    public void init (BaseGame game);

    /**
    * update game screen
    */
    public void update (GameTime time);

    /**
    * draw game screen
    */
    public void draw (GameTime time, SpriteBatch batch);

    public void onResize ();

    /**
    * destroy game screen
    */
    public void destroy ();

}
