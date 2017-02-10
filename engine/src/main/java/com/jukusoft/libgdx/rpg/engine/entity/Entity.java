package com.jukusoft.libgdx.rpg.engine.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

import java.util.*;

/**
 * Created by Justin on 10.02.2017.
 */
public class Entity {

    /**
    * map with all components
    */
    protected Map<Class,IComponent> componentMap = new HashMap<>();

    /**
    * list with all components which implements update() method
    */
    protected List<IUpdateComponent> updateComponentList = new ArrayList<>();

    /**
    * list with all components which implements draw() method
    */
    protected List<IDrawComponent> drawComponentList = new ArrayList<>();

    protected BaseGame game = null;

    public void init (BaseGame game) {
        this.game = game;
    }

    public void update(BaseGame game, GameTime time) {
        //update all components
        this.updateComponentList.stream().forEach(component -> {
            component.update(game, time);
        });
    }

    public void draw(GameTime time, Camera camera, SpriteBatch batch) {
        //draw all components
        this.drawComponentList.stream().forEach(component -> {
            component.draw(time, camera, batch);
        });
    }

    public <T extends IComponent> T getComponent (Class<T> cls) {
        //for HashMap implementation
        if (!this.componentMap.containsKey(cls)) {
            //component doesnt exists on this entity
            return null;
        }

        IComponent component = this.componentMap.get(cls);

        //for ConcurrentHashMap implementation
        if (component == null) {
            //component doesnt exists on this entity
            return null;
        }

        return cls.cast(component);
    }

    public <T extends IComponent> void addComponent (T component, Class<T> cls) {
        //initialize component
        component.init(this.game, this);

        component.onAddedToEntity(this);

        //add component to map
        this.componentMap.put(cls, component);

        //check, if component needs to update
        if (component instanceof IUpdateComponent) {
            this.updateComponentList.add((IUpdateComponent) component);

            //sort list
            Collections.sort(this.updateComponentList, new Comparator<IUpdateComponent>() {
                @Override public int compare(IUpdateComponent o1, IUpdateComponent o2) {
                    return ((Integer) o1.getUpdateOrder().getValue()).compareTo(o2.getUpdateOrder().getValue());
                }
            });
        }

        //check, if component needs to draw
        if (component instanceof IDrawComponent) {
            this.drawComponentList.add((IDrawComponent) component);

            //sort list
            Collections.sort(this.drawComponentList, new Comparator<IDrawComponent>() {
                @Override public int compare(IDrawComponent o1, IDrawComponent o2) {
                    return ((Integer) o1.getDrawOrder().getValue()).compareTo(o2.getDrawOrder().getValue());
                }
            });
        }
    }

    public <T extends IComponent> void removeComponent (Class<T> cls) {
        IComponent component = this.componentMap.get(cls);

        //remove component from map
        this.componentMap.remove(cls);

        if (component != null) {
            component.onRemovedFromEntity(this);

            this.updateComponentList.remove(component);
            this.drawComponentList.remove(component);

            //dispose component
            component.dispose();
        }
    }

}
