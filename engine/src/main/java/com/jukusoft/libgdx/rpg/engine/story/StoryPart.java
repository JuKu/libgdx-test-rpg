package com.jukusoft.libgdx.rpg.engine.story;

import com.jukusoft.libgdx.rpg.engine.time.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 07.02.2017.
 */
public class StoryPart {

    protected List<String> lines = new ArrayList<>();

    protected long startTime = 1000l;
    protected long timeToRead = 10000l;

    public StoryPart () {
        //
    }

    public void addLine (String line) {
        this.lines.add(line);
    }

    public List<String> listLines () {
        return this.lines;
    }

    public void start () {
        this.startTime = GameTime.getInstance().getTime();
    }

    public boolean hasFinished (long now) {
        return (this.startTime + this.timeToRead) < now;
    }

}
