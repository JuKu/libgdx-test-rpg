package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.math.Vector3;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.03.2017.
 */
public class RelativeMousePositionComponent extends BaseComponent implements IUpdateComponent {

    PositionComponent positionComponent = null;

    //relative mouse position to entity
    protected float relX = 0;
    protected float relY = 0;

    //angle in degree
    protected float angle = 0;

    public RelativeMousePositionComponent() {
        //
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }
    }

    @Override public void update(BaseGame game, GameTime time) {
        //get camera
        CameraWrapper camera = game.getCamera();

        //get mouse position relative to camera
        Vector3 mousePos = camera.getMousePosition();

        //get entity position
        float entityX = this.positionComponent.getMiddleX();
        float entityY = this.positionComponent.getMiddleY();

        //calculate mouse position relative to entity
        this.relX = mousePos.x - entityX;
        this.relY = mousePos.y - entityY;

        //calculate mouse angle relative to entity
        double angleRadians = (float) Math.atan2(relY, relX);
        this.angle = (float) Math.toDegrees(angleRadians);

        while (this.angle > 360) {
            this.angle -= 360;
        }

        while (this.angle < 0) {
            this.angle += 360;
        }
    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.VERY_HIGHT;
    }

    public float getRelativeMouseX () {
        return this.relX;
    }

    public float getRelativeMouseY () {
        return this.relY;
    }

}
