package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.skin.SkinFactory;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

import java.io.File;

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
    protected Label nameLabel = null;
    protected TextField nameTextField = null;
    protected Label genderLabel = null;
    protected SelectBox<String> genderSelectBox = null;
    protected Label errorLabel = null;

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
        this.uiSkin = SkinFactory.createSkin(/*AssetPathUtils.getUISkinPath("libgdx", "uiskin.atlas"), */AssetPathUtils.getUISkinPath("create_character", "uiskin.json"));

        //create new stage
        this.stage = new Stage();

        //set input processor
        Gdx.input.setInputProcessor(this.stage);

        //create error label
        this.errorLabel = new Label("Error! Character name already exists!", this.uiSkin);
        this.errorLabel.setPosition(100, 400);
        this.errorLabel.setWidth(800);
        this.errorLabel.setHeight(50);
        this.errorLabel.setColor(Color.RED);
        this.errorLabel.setVisible(false);
        this.stage.addActor(this.errorLabel);

        //create start button
        this.startButton = new TextButton("START GAME", this.uiSkin);
        this.startButton.setPosition(1000, 80);
        this.startButton.setWidth(200);
        this.startButton.setHeight(50);
        this.startButton.setDisabled(true);
        this.startButton.setVisible(false);
        this.startButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                //game.getScreenManager().leaveAllAndEnter("settings");
            }
        });
        this.stage.addActor(this.startButton);

        this.nameLabel = new Label("Your Character Name: ", this.uiSkin);
        this.nameLabel.setPosition(30, 504);
        this.stage.addActor(this.nameLabel);

        this.nameTextField = new TextField("", this.uiSkin);
        this.nameTextField.setPosition(200, 500);
        this.nameTextField.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                validateForm();
            }
        });
        this.stage.addActor(this.nameTextField);

        this.genderLabel = new Label("Gender: ", this.uiSkin);
        this.genderLabel.setPosition(30, 464);
        this.stage.addActor(this.genderLabel);

        this.genderSelectBox = new SelectBox<>(this.uiSkin);
        this.genderSelectBox.setPosition(200, 460);
        this.genderSelectBox.setWidth(100);
        this.genderSelectBox.setItems("male", "female");
        this.genderSelectBox.setSelected("male");
        this.genderSelectBox.setSelectedIndex(0);
        this.genderSelectBox.validate();
        this.stage.addActor(this.genderSelectBox);
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

    protected void validateForm () {
        if (!nameTextField.getText().isEmpty()) {
            errorLabel.setVisible(false);

            if (!(new File(AssetPathUtils.getSavesPath(nameTextField.getText())).exists())) {
                startButton.setDisabled(false);
                startButton.setVisible(true);
            } else {
                startButton.setDisabled(true);
                startButton.setVisible(false);

                errorLabel.setVisible(true);
                errorLabel.setText("Error! Character name already exists: " + nameTextField.getText() + "! Choose another one!");
            }
        } else {
            startButton.setDisabled(true);
            startButton.setVisible(false);

            errorLabel.setVisible(true);
            errorLabel.setText("Please choose an character name!");
        }
    }

}
