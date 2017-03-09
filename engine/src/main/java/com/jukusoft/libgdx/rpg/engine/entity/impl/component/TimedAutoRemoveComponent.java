package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.DevMode;

/**
 * Created by Justin on 09.03.2017.
 */
public class TimedAutoRemoveComponent extends BaseComponent implements IUpdateComponent {

    protected long startTime = 0;
    protected float ttl = 0;

    public TimedAutoRemoveComponent (long TTL) {
        this.ttl = TTL;
    }

    @Override public void update(BaseGame game, GameTime time) {
        if (startTime == 0) {
            startTime = time.getTime();
        }

        //calculate elapsed time
        long elapsed = time.getTime() - startTime;

        if (elapsed > this.ttl) {
            //auto remove entity on next gameloop cycle
            game.runOnUIThread(() -> {
                //check, if developer mode is enabled
                if (DevMode.isEnabled()) {
                    //log message
                    System.out.println("remove projectile.");
                }

                //remove entity from ecs
                this.entity.getEntityComponentSystem().removeEntity(this.entity);
            });
        }
    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.NORMAL;
    }

}
