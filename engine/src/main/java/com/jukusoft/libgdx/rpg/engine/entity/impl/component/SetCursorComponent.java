package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 15.02.2017.
 */
public class SetCursorComponent extends BaseComponent implements IUpdateComponent {

    protected HoverComponent hoverComponent = null;

    protected Pixmap cursorImage = null;
    protected Cursor.SystemCursor systemCursor = null;

    public SetCursorComponent (Pixmap cursorImage) {
        if (cursorImage == null) {
            throw new NullPointerException("cursor image cannot be null.");
        }

        this.cursorImage = cursorImage;
    }

    public SetCursorComponent (Cursor.SystemCursor cursor) {
        if (cursorImage == null) {
            throw new NullPointerException("cursor image cannot be null.");
        }

        this.systemCursor = cursor;
    }

    @Override public void update(BaseGame game, GameTime time) {
        if (this.hoverComponent != null) {
            if (this.hoverComponent.isHovered()) {
                if (this.cursorImage != null) {
                    game.getCursorManager().setCursorTexture(this.cursorImage);
                } else if (this.systemCursor != null) {
                    game.getCursorManager().setSystemCursor(this.systemCursor);
                } else {
                    throw new IllegalStateException("no system cursor and no image cursor set.");
                }
            }
        }
    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.LOW;
    }

    public void setCursorImage (Pixmap cursorImage) {
        this.cursorImage = cursorImage;
        this.systemCursor = null;
    }

    public void setSystemCursor (Cursor.SystemCursor cursor) {
        this.systemCursor = cursor;
        this.cursorImage = null;
    }

}
