package com.jukusoft.libgdx.rpg.engine.hud.actionbar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.hud.BaseHUDWidget;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.MouseUtils;

/**
 * Created by Justin on 05.03.2017.
 */
public class ActionBarItem extends BaseHUDWidget {

    protected String key = "N/A";
    protected Texture texture = null;
    protected BitmapFont font = null;

    protected float textPaddingX = 12;
    protected float textPaddingY = 16;

    protected boolean hovered = false;
    protected Color hoverColor = new Color(0.7f, 0.7f, 1.0f, 0.8f);
    protected ActionCommand command = null;
    protected boolean clicked = false;

    public ActionBarItem (Texture texture, BitmapFont font) {
        this.texture = texture;
        this.font = font;

        this.setDimension(texture.getWidth(), texture.getHeight());
    }

    @Override public void update(BaseGame game, GameTime time) {
        //check, if hovered
        hovered = false;

        Vector3 mousePos = MouseUtils.getMousePositionWithCamera(game.getUICamera());
        float mouseX = mousePos.x;
        float mouseY = mousePos.y;

        float x = getX();
        float y = getY();

        if (x <= mouseX && mouseX <= x + getWidth()) {
            if (y <= mouseY && mouseY <= y + getHeight()) {
                hovered = true;
            }
        }

        if (hovered) {
            if (game.getInputManager().isMousePressed()) {
                clicked = true;
            } else {
                if (clicked) {
                    //execute onClick action
                    this.onClick();
                }

                clicked = false;
            }
        } else {
            clicked = false;
        }
    }

    @Override public void drawLayer0(GameTime time, SpriteBatch batch) {
        if (hovered) {
            batch.setColor(hoverColor);
        }

        batch.draw(this.texture, getX(), getY(), getWidth(), getHeight());

        batch.setColor(1, 1, 1, 1);

        //draw key text
        font.draw(batch, key.toUpperCase(), getX() + getWidth() - (textPaddingX * key.length()), getY() + textPaddingY);
    }

    public void setTexture (Texture texture) {
        Texture oldTexture = this.texture;

        this.texture = texture;

        if (oldTexture != null) {
            oldTexture.dispose();
        }
    }

    public void setKeyText (String key) {
        this.key = key;
    }

    public void setActionCommand (ActionCommand command) {
        this.command = command;
    }

    public void removeActionCommand () {
        this.command = null;
    }

    protected void onClick () {
        //
    }

}
