package com.jukusoft.libgdx.rpg.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInfo;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 08.02.2017.
 */
public class LoadButton<T extends SavedGameInfo> {

    protected float x = 0;
    protected float y = 0;
    protected float width = 400;
    protected float height = 100;

    protected boolean hovered = false;
    protected boolean isClicked = false;

    protected ClickListener clickListener = null;

    //assets
    protected Texture bgTexture = null;
    protected Texture hoverTexture = null;
    protected BitmapFont font = null;

    protected T gameInfo = null;

    public LoadButton (Texture bgTexture, Texture hoverTexture, BitmapFont font, T gameInfo) {
        this.bgTexture = bgTexture;
        this.hoverTexture = hoverTexture;
        this.font = font;
        this.gameInfo = gameInfo;
    }

    public void setPosition (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update (BaseGame game, GameTime time) {
        //get mouse coordinates
        float mouseX = Gdx.input.getX();
        float mouseY = game.getViewportHeight() - Gdx.input.getY();

        //check, if mouse is inner button
        if (isInner(mouseX, mouseY)) {
            this.hovered = true;
        } else {
            hovered = false;
        }

        boolean oldClicked = this.isClicked;

        if (isInner(mouseX, mouseY) && Gdx.input.isTouched()) {
            this.isClicked = true;
        } else {
            this.isClicked = false;

            //check, if user has released button
            if (oldClicked == true) {
                //user has clicked button
                if (clickListener != null) {
                    clickListener.onClick();
                }
            }
        }
    }

    public void draw (GameTime time, SpriteBatch batch) {
        //draw background texture
        if (hovered) {
            batch.draw(this.hoverTexture, x, y);
        } else {
            batch.draw(this.bgTexture, x, y);
        }

        //draw button text
        this.font.draw(batch, this.gameInfo.getTitle(), x + 30, y + 60);
    }

    protected boolean isInner (float mouseX, float mouseY) {
        if (mouseX >= x && mouseX <= (x + width)) {
            if (mouseY >= y && mouseY <= (y + height)) {
                return true;
            }
        }

        return false;
    }

    public void setClickListener (ClickListener listener) {
        this.clickListener = listener;
    }

}
