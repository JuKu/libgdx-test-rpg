package com.jukusoft.libgdx.rpg.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.world.SectorCoord;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 09.02.2017.
 */
public class GameWorldMap extends BaseMap {

    //if map has an background tiledmap (for example water)
    protected String backgroundMapPath = "";

    protected SectorCoord coord = null;

    protected String mapDirPath = "";
    protected String mapPath = "";
    protected TiledMap tiledMap = null;

    public GameWorldMap (SectorCoord coord) {
        this.coord = coord;

        String dirName = "map_" + coord.getX() + "_" + coord.getY() + "_" + coord.getLayer() + "";
        this.mapDirPath = AssetPathUtils.getMapPath(dirName + "/");
        this.mapPath = AssetPathUtils.getMapPath(dirName + "/" + dirName + ".tmx");

        //load TMX map
        this.load();

        //load map information (water, collision, NPCs and so on)
    }

    public void load () {
        //
    }

    public void update (BaseGame game, Camera camera, GameTime time) {
        //
    }

    public void draw (GameTime time, Camera camera, SpriteBatch batch) {
        //
    }

    public void drawWater (GameTime time, Camera camera, SpriteBatch batch) {
        //
    }

    public SectorCoord getSectorCoord () {
        return this.coord;
    }

    public void dispose () {
        //
    }

}
