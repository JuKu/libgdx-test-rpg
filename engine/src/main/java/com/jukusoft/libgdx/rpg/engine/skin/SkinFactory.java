package com.jukusoft.libgdx.rpg.engine.skin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Justin on 06.02.2017.
 */
public class SkinFactory {

    public static Skin createSkin (final String atlasFile, final String jsonFile) {
        Skin skin = new Skin();

        //load texture atlas
        TextureAtlas atlas = new TextureAtlas(Gdx.files.absolute(atlasFile));
        skin.addRegions(atlas);

        //load skin json file
        skin.load(Gdx.files.absolute(jsonFile));

        return skin;
    }

}
