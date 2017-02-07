package com.jukusoft.libgdx.rpg.engine.story;

import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 07.02.2017.
 */
public class StoryPart {

    protected List<String> lines = new ArrayList<>();
    protected String[] lineArray  = new String[] {};

    protected long startTime = 0l;
    protected long timeToRead = 20000l;

    public StoryPart () {
        //
    }

    public void addLine (String line) {
        this.lines.add(line);
    }

    public List<String> listLines () {
        return this.lines;
    }

    public int countLines () {
        return lines.size();
    }

    public String[] getLineArray () {
        return this.lineArray;
    }

    public float getPartProgress (long now) {
        long timeLeft = now - startTime;

        if (timeLeft > timeToRead) {
            timeLeft = timeToRead;
        }

        float delta = timeToRead - timeLeft;
        delta = timeToRead - delta;

        return delta / timeToRead;
    }

    public void start () {
        this.startTime = GameTime.getInstance().getTime();

        this.lineArray = ArrayUtils.convertStringListToArray(this.lines);
    }

    public boolean hasFinished (long now) {
        return (this.startTime + this.timeToRead) < now;
    }

}
