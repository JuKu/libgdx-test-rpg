package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 15.02.2017.
 */
public class CursorComponent extends BaseComponent implements IUpdateComponent {

    protected PositionComponent positionComponent = null;
    protected HoverComponent hoverComponent = null;

    protected Pixmap cursorImage = null;
    protected Cursor.SystemCursor systemCursor = null;

    public CursorComponent(Pixmap cursorImage) {
        if (cursorImage == null) {
            throw new NullPointerException("cursor image cannot be null.");
        }

        this.cursorImage = cursorImage;
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.hoverComponent = entity.getComponent(HoverComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }
    }

    public CursorComponent(Cursor.SystemCursor cursor) {
        if (cursorImage == null) {
            throw new NullPointerException("cursor image cannot be null.");
        }

        this.systemCursor = cursor;
    }

    @Override public void update(BaseGame game, GameTime time) {
        boolean hovered = false;

        if (this.hoverComponent != null) {
            hovered = this.hoverComponent.isHovered();
        } else {
            //check
            Vector3 mousePos = game.getCamera().getMousePosition();
            float mouseX = mousePos.x;
            float mouseY = mousePos.y;

            float x = positionComponent.getX();
            float y = positionComponent.getY();
            float width = positionComponent.getWidth();
            float height = positionComponent.getHeight();

            //check, if entity is hovered
            if (mouseX >= x && mouseX <= (x + width)) {
                if (mouseY >= y && mouseY <= (y + height)) {
                    hovered = true;
                }
            }
        }

        if (hovered) {
            if (this.cursorImage != null) {
                game.getCursorManager().setCursorTexture(this.cursorImage);
            } else if (this.systemCursor != null) {
                game.getCursorManager().setSystemCursor(this.systemCursor);
            } else {
                throw new IllegalStateException("no system cursor and no image cursor set.");
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
