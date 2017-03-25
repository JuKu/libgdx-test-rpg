package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jukusoft.libgdx.rpg.engine.data.SharedData;
import com.jukusoft.libgdx.rpg.engine.font.BitmapFontFactory;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.skin.SkinFactory;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.SpriteBatcherUtils;
import com.jukusoft.libgdx.rpg.game.client.GameClient;
import com.jukusoft.libgdx.rpg.game.client.listener.AuthListener;
import com.jukusoft.libgdx.rpg.game.shared.SharedDataConst;
import com.jukusoft.libgdx.rpg.game.ui.ImageButton;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;
import com.jukusoft.libgdx.rpg.network.utils.SocketUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.io.IOException;

/**
 * Created by Justin on 24.03.2017.
 */
public class MultiplayerLoginScreen extends BaseScreen {

    protected final String BG_IMAGE_PATH = AssetPathUtils.getWallpaperPath("ocean_sunset/ocean.png");
    protected final String MUSIC_PATH = AssetPathUtils.getMusicPath("EssentialGameAudiopack/FullScores/Orchestral_Scores/Ove_Melaa-Theme_Crystalized.mp3");
    protected final String BUTTON_BACK_BG_PATH = AssetPathUtils.getUIWidgetPath("backbutton", "backbutton.png");
    protected final String BUTTON_BACK_HOVER_PATH = AssetPathUtils.getUIWidgetPath("backbutton", "backbutton_hovered.png");
    protected final String ICON_BACK_PATH = AssetPathUtils.getImagePath("icons/back/back_64.png");

    public static final int GAME_CLIENT_THREADS = 2;
    public static final long PING_CHECK_INTERVAL = 2000l;

    //assets
    protected Texture backgroundTexture = null;
    protected Music backgroundMusic = null;
    protected Texture backButtonTexture = null;
    protected Texture backButtonHoveredTexture = null;

    protected Stage stage = null;
    protected Skin uiSkin = null;
    protected BitmapFont arialFont = null;

    protected TextButton loginButton = null;
    protected ImageButton backButton = null;
    protected Texture iconBackTexture = null;

    protected Label errorLabel = null;
    protected Label serverIPLabel = null;
    protected TextField serverIPField = null;
    protected Label serverPortLabel = null;
    protected TextField serverPortField = null;
    protected Label usernameLabel = null;
    protected TextField usernameField = null;
    protected Label passwordLabel = null;
    protected TextField passwordField = null;

    protected GameClient client = null;
    protected boolean fixedConnection = false;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //
    }

    @Override
    public void onResume () {
        //create and load ui skin from json file
        this.uiSkin = SkinFactory.createSkin(AssetPathUtils.getUISkinPath("create_character", "uiskin.json"));

        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);

        //load assets
        this.assetManager.load(BG_IMAGE_PATH, Texture.class);
        this.assetManager.load(MUSIC_PATH, Music.class);
        this.assetManager.load(BUTTON_BACK_BG_PATH, Texture.class);
        this.assetManager.load(BUTTON_BACK_HOVER_PATH, Texture.class);
        this.assetManager.load(ICON_BACK_PATH, Texture.class);

        this.client = game.getSharedData().get(SharedDataConst.NETWORK_CLIENT, GameClient.class);

        this.arialFont = BitmapFontFactory
            .createFont(AssetPathUtils.getFontPath("arial/arial.ttf"), 26, Color.WHITE);

        //wait while all assets was loaded
        game.getAssetManager().finishLoading();
        this.assetManager.finishLoadingAsset(BG_IMAGE_PATH);

        //get assets
        this.backgroundTexture = this.assetManager.get(BG_IMAGE_PATH, Texture.class);
        this.backgroundMusic = this.assetManager.get(MUSIC_PATH, Music.class);
        this.backButtonTexture = this.assetManager.get(BUTTON_BACK_BG_PATH, Texture.class);
        this.backButtonHoveredTexture = this.assetManager.get(BUTTON_BACK_HOVER_PATH, Texture.class);
        this.iconBackTexture = this.assetManager.get(ICON_BACK_PATH, Texture.class);

        this.assetManager.finishLoading();

        //play background music
        this.backgroundMusic.setVolume(game.getVolume(0.3f));
        this.backgroundMusic.setPosition(38);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.play();
        this.backgroundMusic.setPosition(38);

        this.backButton = new ImageButton(this.backButtonTexture, this.backButtonHoveredTexture, this.arialFont, "BACK");
        this.backButton.setPosition(50, 50);
        this.backButton.setIcon(this.iconBackTexture);
        this.backButton.setClickListener(() -> {
            //leave and enter new game state
            game.getScreenManager().leaveAllAndEnter("menu");
        });

        //create error label
        this.errorLabel = new Label("Error! Character name already exists!", this.uiSkin);
        this.errorLabel.setPosition(600, 600);
        this.errorLabel.setWidth(800);
        this.errorLabel.setHeight(50);
        this.errorLabel.setColor(Color.RED);
        this.errorLabel.setVisible(false);
        this.stage.addActor(this.errorLabel);

        //create login button
        this.loginButton = new TextButton("LOGIN", this.uiSkin);
        this.loginButton.setPosition(1000, 80);
        this.loginButton.setWidth(200);
        this.loginButton.setHeight(50);
        this.loginButton.setDisabled(true);
        this.loginButton.setVisible(false);
        this.loginButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                tryLogin();
            }
        });
        this.stage.addActor(this.loginButton);

        this.serverIPLabel = new Label("Server IP: ", this.uiSkin);
        this.serverIPLabel.setPosition(500, 504);
        this.stage.addActor(this.serverIPLabel);

        this.serverIPField = new TextField("", this.uiSkin);
        this.serverIPField.setPosition(600, 500);
        this.serverIPField.setText("jukusoft.com");
        this.serverIPField.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                validateForm();
            }
        });
        this.stage.addActor(this.serverIPField);

        this.serverPortLabel = new Label("Server Port: ", this.uiSkin);
        this.serverPortLabel.setPosition(500, 464);
        this.stage.addActor(this.serverPortLabel);

        this.serverPortField = new TextField("", this.uiSkin);
        this.serverPortField.setPosition(600, 460);
        this.serverPortField.setText("55011");
        this.serverPortField.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                validateForm();
            }
        });
        this.stage.addActor(this.serverPortField);

        this.usernameLabel = new Label("Username: ", this.uiSkin);
        this.usernameLabel.setPosition(500, 424);
        this.stage.addActor(this.usernameLabel);

        this.usernameField = new TextField("", this.uiSkin);
        this.usernameField.setPosition(600, 420);
        this.usernameField.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                validateForm();
            }
        });
        this.stage.addActor(this.usernameField);

        this.passwordLabel = new Label("Password: ", this.uiSkin);
        this.passwordLabel.setPosition(500, 384);
        this.stage.addActor(this.passwordLabel);

        this.passwordField = new TextField("", this.uiSkin);
        this.passwordField.setPosition(600, 380);
        this.passwordField.setPasswordMode(true);
        this.passwordField.setPasswordCharacter('*');
        this.passwordField.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                validateForm();
            }
        });
        this.stage.addActor(this.passwordField);
    }

    @Override
    public void onPause () {
        //stop background music
        this.backgroundMusic.stop();

        this.backgroundMusic.dispose();
        this.backgroundMusic = null;
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        this.backButton.update(game, time);
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        if (backgroundTexture == null) {
            throw new NullPointerException("background texture is null.");
        }

        if (!backgroundTexture.isManaged()) {
            throw new IllegalStateException("background texture isnt managed.");
        }

        batch.setProjectionMatrix(game.getUICamera().combined);

        //draw background image
        batch.draw(this.backgroundTexture, 0, 0);

        SpriteBatcherUtils.fillRectangle(batch, 580, 610, 240, 30, Color.WHITE);

        //draw back button
        this.backButton.draw(time, batch);

        //because stage used its own sprite batcher, we have to flush batch, so batch will be drawn
        batch.flush();

        this.stage.draw();
    }

    @Override public void destroy() {
        //
    }

    protected boolean validateForm () {
        boolean valide = true;
        String errorText = "";

        if (this.serverIPField.getText().isEmpty()) {
            valide = false;
            errorText = "Server IP is empty!";
        }

        if (this.serverPortField.getText().isEmpty()) {
            valide = false;
            errorText = "Server Port is empty!";
        } else {
            try {
                int port = Integer.parseInt(this.serverPortField.getText());
            } catch (NumberFormatException e) {
                valide = false;
                errorText = "Server Port isnt an number!";
            }
        }

        if (this.usernameField.getText().isEmpty()) {
            valide = false;
            errorText = "Username is empty!";
        }

        if (this.passwordField.getText().isEmpty()) {
            valide = false;
            errorText = "Password is empty!";
        }

        this.loginButton.setVisible(valide);
        this.loginButton.setDisabled(!valide);
        errorLabel.setVisible(!valide);
        errorLabel.setColor(Color.RED);
        errorLabel.setText(errorText);

        return valide;
    }

    protected void tryLogin () {
        this.loginButton.setVisible(false);

        final String ip = this.serverIPField.getText();
        final int port = Integer.parseInt(this.serverPortField.getText());
        final String username = this.usernameField.getText();
        final String password = this.passwordField.getText();

        new Thread(() -> {
            boolean valide = true;
            String errorText = "";

            if (!validateForm()) {
                return;
            }

            try {
                if (SocketUtils.checkIfRemotePortAvailable(ip, port, 500)) {
                    //server is available
                } else {
                    valide = false;
                    errorText = "Server isnt available!";
                }
            } catch (IOException e) {
                e.printStackTrace();
                valide = false;
                errorText = "Server isnt available!";
            }

            if (valide) {
                //create new network game client, if neccessary
                if (this.client == null) {
                    this.client = new GameClient(GAME_CLIENT_THREADS);

                    //save and share network client with other screens
                    game.getSharedData().put(SharedDataConst.NETWORK_CLIENT, this.client);
                }

                //check, if client is already connected
                if (client.isConnected() && !this.fixedConnection) {
                    //close connection
                    client.close();
                }

                try {
                    game.runOnUIThread(() -> {
                        errorLabel.setVisible(true);
                        errorLabel.setColor(Color.GREEN);
                        errorLabel.setText("Connecting...");

                        //disable login button
                        this.loginButton.setVisible(false);
                    });

                    if (!fixedConnection) {
                        //connect to server
                        ChannelFuture channelFuture = this.client.connectAsync(ip, port);

                        channelFuture.addListener(new ChannelFutureListener() {
                            @Override public void operationComplete(ChannelFuture future) throws Exception {
                                if (future.isSuccess()) {
                                    game.runOnUIThread(() -> {
                                        errorLabel.setVisible(true);
                                        errorLabel.setColor(Color.GREEN);
                                        errorLabel.setText("Connected!");
                                    });

                                    fixedConnection = true;

                                    //add ping check every 2 seconds
                                    client.addTask(PING_CHECK_INTERVAL, () -> {
                                        client.requestPingCheck();
                                    });

                                    tryAuth();
                                } else {
                                    game.runOnUIThread(() -> {
                                        errorLabel.setVisible(true);
                                        errorLabel.setColor(Color.RED);
                                        errorLabel.setText("Connection failed!");

                                        //disable login button
                                        loginButton.setVisible(false);
                                    });
                                }
                            }
                        });
                    } else {
                        tryAuth();
                    }

                    return;
                } catch (Exception e) {
                    e.printStackTrace();

                    valide = false;
                    errorText = "Cannot connect to game server!";
                }
            }

            final boolean valide2 = valide;
            final String errorText2 = errorText;

            game.runOnUIThread(() -> {
                this.loginButton.setVisible(valide2);
                this.loginButton.setDisabled(!valide2);
                errorLabel.setVisible(!valide2);
                errorLabel.setColor(Color.RED);
                errorLabel.setText(errorText2);
            });
        }).start();
    }

    protected void tryAuth () {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();

        client.setAuthListener(new AuthListener() {
            @Override public void onAuth(boolean success, int errorCode, long userID,
                String message) {
                if (success) {
                    game.runOnUIThread(() -> {
                        errorLabel.setVisible(true);
                        errorLabel.setColor(Color.GREEN);
                        errorLabel.setText("Login successful!");

                        //save userID
                        game.getSharedData().put(SharedDataConst.USERID, userID);

                        //enter multiplayer game state
                        game.getScreenManager().leaveAllAndEnter("multiplayer_game");
                    });
                } else {
                    errorLabel.setVisible(true);
                    errorLabel.setColor(Color.RED);
                    errorLabel.setText("Login failed! " + message);
                }
            }
        });

        game.getSharedData().put("username", username);

        //try to authorize
        client.authUser(username, password, 2000);
    }

}
