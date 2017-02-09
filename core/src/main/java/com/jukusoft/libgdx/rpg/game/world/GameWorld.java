package com.jukusoft.libgdx.rpg.game.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.world.SectorCoord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 09.02.2017.
 */
public class GameWorld {

    protected Map<SectorCoord,GameWorldMap> gameWorldMap = new ConcurrentHashMap<>();

    /**
    * all game world maps which are shown on screen
    */
    protected List<GameWorldMap> visibleMaps = new ArrayList<>();

    public GameWorld () {
        //
    }

    protected void loadMap (SectorCoord coord) {
        //load map from cache or from file
    }

    public void drawMap (GameTime time, SpriteBatch batch) {
        //render all maps which are visible
    }

}
