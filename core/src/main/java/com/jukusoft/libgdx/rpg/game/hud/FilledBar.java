package com.jukusoft.libgdx.rpg.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.hud.BaseHUDWidget;
import com.jukusoft.libgdx.rpg.game.hud.HUDWidget;

/**
 * Created by Justin on 08.02.2017.
 */
public class FilledBar extends BaseHUDWidget {

    protected String text = "";

    protected Color backgroundColor = Color.GREEN;
    protected Color foregroundColor = Color.RED;
    protected Color textColor = Color.WHITE;

    protected float paddingLeft = 4;
    protected float paddingRight = 4;
    protected float paddingTop = 4;
    protected float paddingBottom = 4;

    protected BitmapFont font = null;
    protected float percent = 0;
    protected float value = 0;
    protected float maxValue = 100;

    public FilledBar (BitmapFont font) {
        this.font = font;
    }

    public void update (BaseGame game, GameTime time) {
        this.percent = this.value / this.maxValue;
    }

    @Override public void drawLayer0(GameTime time, SpriteBatch batch) {

    }

    @Override public void drawLayer1(GameTime time, ShapeRenderer shapeRenderer) {
        //draw background
        shapeRenderer.setColor(this.backgroundColor);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());

        //draw foreground
        shapeRenderer.setColor(this.foregroundColor);
        shapeRenderer.rect(getX() + paddingBottom, getY() + paddingLeft, getWidth() - (paddingLeft + paddingRight), getHeight() - (paddingTop + paddingBottom));
    }

    @Override public void drawLayer2(GameTime time, SpriteBatch batch) {
        float startX = getX() + (getWidth() / 2) - (this.text.length() * this.font.getSpaceWidth() - 10);
        float startY = getY() + (getHeight() / 2) - this.font.getLineHeight();

        //draw text
        this.font.setColor(this.textColor);
        this.font.draw(batch, this.text, startX, startY);
    }

    public void setFont (BitmapFont font) {
        this.font = font;
    }

    public String getText () {
        return this.text;
    }

    public void setText (String text) {
        this.text = text;
    }

    public float getValue () {
        return this.value;
    }

    public void setValue (float value) {
        this.value = value;
    }

}
