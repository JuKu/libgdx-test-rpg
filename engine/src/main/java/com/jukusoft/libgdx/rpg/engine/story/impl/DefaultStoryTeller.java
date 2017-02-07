package com.jukusoft.libgdx.rpg.engine.story.impl;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.story.StoryPart;
import com.jukusoft.libgdx.rpg.engine.story.StoryTeller;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.ArrayUtils;
import com.jukusoft.libgdx.rpg.engine.utils.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 07.02.2017.
 */
public class DefaultStoryTeller implements StoryTeller {

    /**
    * all story parts
    */
    List<StoryPart> storyParts = new ArrayList<>();

    protected volatile StoryPart currentPart = null;
    protected int currentPartIndex = 0;

    protected BitmapFont font = null;

    public DefaultStoryTeller (BitmapFont font) {
        this.font = font;
    }

    @Override public void load(String storyFile) throws IOException {
        String[] lines = ArrayUtils.convertStringListToArray(FileUtils.readLines(storyFile, StandardCharsets.UTF_8));

        StoryPart part = new StoryPart();
        this.currentPart = part;

        //parse lines
        for (String line : lines) {
            if (line.startsWith("#")) {
                //next part
                storyParts.add(part);

                part = new StoryPart();

                continue;
            }

            part.addLine(line);
        }

        storyParts.add(part);
    }

    @Override public void start() {
        this.currentPart.start();
    }

    @Override public int countParts() {
        return this.storyParts.size();
    }

    @Override public StoryPart getCurrentPart() {
        return this.currentPart;
    }

    @Override public float getPartProgress(long now) {
        if (currentPart == null) {
            return 1f;
        } else {
            return currentPart.getPartProgress(now);
        }
    }

    @Override public void update(GameTime time) {
        if (currentPart.hasFinished(time.getTime())) {
            //switch to next part

            this.currentPartIndex++;

            if (this.currentPartIndex < this.storyParts.size()) {
                this.currentPart = this.storyParts.get(this.currentPartIndex);
                this.currentPart.start();

                System.out.println("next story part: " + this.currentPartIndex);
            } else {
                System.out.println("story finished!");
            }
        }
    }

    @Override public void draw(GameTime time, SpriteBatch batch, float x, float y, float spacePerLine) {
        if (currentPart == null) {
            return;
        }

        String[] lines = this.currentPart.getLineArray();

        for (int i = 0; i < lines.length; i++) {
            this.font.draw(batch, lines[i], /*(game.getViewportWidth() - 80) / 2*/x, y - (i * spacePerLine));
        }
    }

    @Override public boolean hasFinished() {
        return this.currentPartIndex > this.storyParts.size();
    }

}
