package com.jukusoft.libgdx.rpg.engine.story;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 07.02.2017.
 */
public interface StoryTeller {

    public void load (String storyFile);

    public void start ();

    public void update (GameTime time);

    public void draw (GameTime time, SpriteBatch batch);

    public boolean hasFinished ();

}
