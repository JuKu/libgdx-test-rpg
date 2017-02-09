package com.jukusoft.libgdx.rpg.game.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
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

    public void update (BaseGame game, Camera camera, GameTime time) {
        //check, if some maps arent visible anymore and remove them from draw queue
        this.visibleMaps.stream().forEach(map -> {
            if (!map.isMapVisibleInViewPort(camera)) {
                //remove map from render queue
                this.visibleMaps.remove(map);
            }
        });

        //TODO: check, if user walk to near maps, if so, load near maps to draw queue
    }

    public void draw (GameTime time, Camera camera, SpriteBatch batch) {
        //render all maps which are visible
        this.visibleMaps.stream().forEach(map -> {
            //draw map
            map.draw(time, camera, batch);
        });
    }

}
