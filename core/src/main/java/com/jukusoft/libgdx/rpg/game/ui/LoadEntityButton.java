package com.jukusoft.libgdx.rpg.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInfo;

/**
 * Created by Justin on 08.02.2017.
 */
public class LoadEntityButton extends Button {

    protected SavedGameInfo gameInfo = null;
    protected Texture backgroundTexture = null;

    public LoadEntityButton (Texture background, SavedGameInfo gameInfo, Skin skin) {
        super(skin);

        this.setWidth(400);
        this.setHeight(80);

        this.gameInfo = gameInfo;
        this.backgroundTexture = background;
    }

    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        //draw background
        batch.draw(this.backgroundTexture, getX(), getY());
    }

}
