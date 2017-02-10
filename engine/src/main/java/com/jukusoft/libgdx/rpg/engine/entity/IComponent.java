package com.jukusoft.libgdx.rpg.engine.entity;

import com.jukusoft.libgdx.rpg.engine.game.BaseGame;

/**
 * Created by Justin on 10.02.2017.
 */
public interface IComponent {

    public void init (BaseGame game, Entity entity);

    public void onAddedToEntity (Entity entity);

    public void onRemovedFromEntity (Entity entity);

    public void dispose ();

}
