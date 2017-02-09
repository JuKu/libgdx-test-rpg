package com.jukusoft.libgdx.rpg.game.world;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.world.SectorCoord;

/**
 * Created by Justin on 09.02.2017.
 */
public class GameWorldMap extends BaseMap {

    //if map has an background tiledmap (for example water)
    protected String backgroundMapPath = "";

    protected SectorCoord coord = null;

    //special shader program for water shader
    protected ShaderProgram waterShader = null;
    protected ShaderProgram defaultShader = null;

    public GameWorldMap (SectorCoord coord) {
        this.coord = coord;

        final String vertexShader = new FileHandle("data/vertexShader.glsl").readString();
        final String fragmentShader = new FileHandle("data/defaultPixelShader.glsl").readString();

        //create default shader program
        this.defaultShader = SpriteBatch.createDefaultShader();

        //load TMX map

        //load map information (water, collision, NPCs and so on)
    }

    public void update (BaseGame game, Camera camera, GameTime time) {
        //
    }

    public void draw (GameTime time, Camera camera, SpriteBatch batch) {
        //
    }

}
