package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.skin.SkinFactory;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

/**
 * Created by Justin on 06.02.2017.
 */
public class CreateCharacterScreen extends BaseScreen {

    protected final String MUSIC_PATH = AssetPathUtils.getMusicPath("EssentialGameAudiopack/Loops/Drum_Only_Loops/Ove_Melaa-DrumLoop_1_64BPM.mp3");
    protected final String BG_IMAGE_PATH = AssetPathUtils.getWallpaperPath("calm_waters_tree/calm_waters_tree.png");

    protected Texture bgTexture = null;

    protected Music backgroundMusic = null;

    protected Stage stage = null;
    protected Skin uiSkin = null;

    protected TextButton startButton = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //
    }

    @Override
    public void onResume () {
        //load all assets
        game.getAssetManager().load(MUSIC_PATH, Music.class);
        game.getAssetManager().load(BG_IMAGE_PATH, Texture.class);

        game.getAssetManager().finishLoading();

        //get and play background music as loop
        this.backgroundMusic = game.getAssetManager().get(MUSIC_PATH, Music.class);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.setVolume(game.getVolume());
        this.backgroundMusic.play();

        this.bgTexture = game.getAssetManager().get(BG_IMAGE_PATH, Texture.class);

        //create and load ui skin from json file
        this.uiSkin = SkinFactory.createSkin(/*AssetPathUtils.getUISkinPath("libgdx", "uiskin.atlas"), */AssetPathUtils.getUISkinPath("libgdx", "uiskin.json"));

        //create new stage
        this.stage = new Stage();

        //create start button
        this.startButton = new TextButton("START", this.uiSkin);
        this.startButton.setPosition(800, 160);
        this.startButton.setWidth(100);
        this.startButton.setHeight(50);
        this.startButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                //game.getScreenManager().leaveAllAndEnter("settings");
            }
        });
        this.stage.addActor(this.startButton);
    }

    @Override
    public void onPause () {
        this.backgroundMusic.stop();
        this.backgroundMusic.dispose();
        this.backgroundMusic = null;

        this.stage.dispose();
        this.stage = null;

        this.startButton = null;

        this.uiSkin.dispose();
        this.uiSkin = null;
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {

    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //draw background image
        batch.draw(bgTexture, 0, 0);

        //because stage used its own sprite batcher, we have to flush batch, so batch will be drawn
        batch.flush();

        this.stage.draw();
    }

    @Override public void destroy() {

    }

}
