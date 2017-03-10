package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IDrawComponent;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.DevMode;
import com.jukusoft.libgdx.rpg.engine.utils.RectanglePoolPrototypeFactory;
import com.jukusoft.libgdx.rpg.engine.utils.SpriteBatcherUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Justin on 10.03.2017.
 */
public class HitBoxesComponent extends BaseComponent implements IUpdateComponent, IDrawComponent {

    protected PositionComponent positionComponent = null;

    protected Pool<Rectangle> rectPool = null;

    //list with all hitboxes of entity
    protected List<Rectangle> hitboxes = new ArrayList<>();

    protected Rectangle mainHitbox = null;
    protected boolean autoUpdateHixbox = false;

    protected boolean drawHitBoxes = true;
    protected Color hitboxColor = Color.RED;
    protected float thickness = 1;

    public HitBoxesComponent(boolean autoUpdateHixbox) {
        //create new rectangle pool
        this.rectPool = RectanglePoolPrototypeFactory.createRectanglePool();

        this.autoUpdateHixbox = autoUpdateHixbox;

        if (this.autoUpdateHixbox) {
            this.mainHitbox = this.rectPool.obtain();

            //add main hitbox to list
            this.hitboxes.add(this.mainHitbox);
        }
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        //update hitbox
        if (autoUpdateHixbox) {
            mainHitbox.setPosition(positionComponent.getX(), positionComponent.getY());
            mainHitbox.setWidth(positionComponent.getWidth());
            mainHitbox.setHeight(positionComponent.getHeight());
        }
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.HIGH;
    }

    public List<Rectangle> listAllHitboxes () {
        return Collections.unmodifiableList(this.hitboxes);
    }

    public boolean overlaps (Rectangle otherRect) {
        for (Rectangle rect : this.hitboxes) {
            if (rect.overlaps(otherRect)) {
                return true;
            }
        }

        return false;
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        if (this.drawHitBoxes || DevMode.isDrawHitboxEnabled()) {
            for (Rectangle hitbox : this.hitboxes) {
                //draw rectangle
                SpriteBatcherUtils.drawRect(batch, hitbox, thickness, this.hitboxColor);
            }
        }
    }

    @Override public ECSPriority getDrawOrder() {
        return ECSPriority.DRAW_HUD;
    }

    public boolean isHitboxDrawing () {
        return this.drawHitBoxes;
    }

    public void setDrawHitBoxes (boolean drawHitBoxes) {
        this.drawHitBoxes = drawHitBoxes;
    }

    @Override
    public void dispose () {
        if (this.mainHitbox != null) {
            //recycle rectangle
            this.rectPool.free(this.mainHitbox);

            this.mainHitbox = null;
        }
    }

}
