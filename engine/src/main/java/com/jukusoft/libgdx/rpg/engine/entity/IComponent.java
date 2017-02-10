package com.jukusoft.libgdx.rpg.engine.entity;

/**
 * Created by Justin on 10.02.2017.
 */
public interface IComponent {

    public void onAddedToEntity (Entity entity);

    public void onRemovedFromEntity (Entity entity);

    public void dispose ();

}
