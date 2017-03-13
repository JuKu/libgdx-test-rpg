package com.jukusoft.libgdx.rpg.engine.entity.impl.component.input;

import com.badlogic.gdx.math.Vector3;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.PositionComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Component to set entity position to mouse position
 *
 * Created by Justin on 10.03.2017.
 */
public class SetMousePositionComponent extends BaseComponent implements IUpdateComponent {

    protected PositionComponent positionComponent = null;

    public SetMousePositionComponent () {
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

        //get mouse position
        Vector3 mousePos = camera.getMousePosition();

        //set mouse position
        this.positionComponent.setMiddlePosition(mousePos.x, mousePos.y);
    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.HIGH;
    }

}
