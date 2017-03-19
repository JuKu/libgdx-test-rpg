package com.jukusoft.libgdx.rpg.engine.fightingsystem;

import com.badlogic.gdx.math.Rectangle;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.collision.CollisionBoxesComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 16.03.2017.
 */
public class HitboxesSystem {

    //list with all hitboxes in game world
    protected List<ListData> hitboxList = new ArrayList<>();

    public void addEntity (Entity entity, CollisionBoxesComponent component) {
        ListData data = new ListData(entity, component);

        System.out.println("add hitbox to HitboxesSystem.");

        hitboxList.add(data);
    }

    public void removeEntity (Entity entity, CollisionBoxesComponent component) {
        ListData data = new ListData(entity, component);

        System.out.println("remove hitbox from HitboxesSystem.");

        hitboxList.remove(data);
    }

    public boolean isHitboxColliding (Rectangle rectangle) {
        for (ListData data : this.hitboxList) {
            Entity entity = data.getEntity();
            CollisionBoxesComponent collisionBoxesComponent = data.getCollisionBoxesComponent();

            if (collisionBoxesComponent.overlaps(rectangle)) {
                return true;
            }
        }

        return false;
    }

    protected class ListData {

        protected Entity entity = null;
        protected CollisionBoxesComponent collisionBoxesComponent = null;

        public ListData (Entity entity, CollisionBoxesComponent collisionBoxesComponent) {
            this.entity = entity;
            this.collisionBoxesComponent = collisionBoxesComponent;
        }

        public Entity getEntity () {
            return this.entity;
        }

        public CollisionBoxesComponent getCollisionBoxesComponent() {
            return this.collisionBoxesComponent;
        }

        @Override
        public boolean equals (Object obj) {
            if (obj instanceof ListData) {
                return this.hashCode() == obj.hashCode();
            }

            return false;
        }

        @Override
        public int hashCode () {
            return this.entity.hashCode();
        }

    }

}
