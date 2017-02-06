package com.jukusoft.libgdx.rpg.engine.story;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

import java.io.IOException;

/**
 * Created by Justin on 07.02.2017.
 */
public interface StoryTeller {

    public void load (String storyFile) throws IOException;

    public void start ();

    public int countParts ();

    public StoryPart getCurrentPart ();

    public void update (GameTime time);

    public void draw (GameTime time, SpriteBatch batch, float x, float y);

    public boolean hasFinished ();

}
