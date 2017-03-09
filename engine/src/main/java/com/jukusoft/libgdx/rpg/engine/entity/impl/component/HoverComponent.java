package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.*;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.MouseUtils;

/**
 * Created by Justin on 12.02.2017.
 */
public class HoverComponent extends BaseComponent implements IUpdateComponent, IDrawUILayerComponent {

    //components
    protected PositionComponent positionComponent = null;
    protected DrawTextureComponent textureComponent = null;
    protected DrawTextureRegionComponent textureRegionComponent = null;

    //hover effect
    protected Texture hoverTexture = null;
    protected int padding = 2;
    protected Color hoverColor = Color.BLUE;
    protected boolean hovered = false;
    protected int width = 0;
    protected int height = 0;
    //protected Vector3 cachedVector = new Vector3(0, 0, 0);

    protected boolean invailde = true;

    public HoverComponent (Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.textureComponent = entity.getComponent(DrawTextureComponent.class);
        this.textureRegionComponent = entity.getComponent(DrawTextureRegionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        if (textureComponent == null && textureRegionComponent == null) {
            throw new IllegalStateException("You have to set an TextureComponent or an TextureRegionComponent to entity to use HoverComponent.");
        }

        if (this.textureComponent != null) {
            this.textureComponent.addTextureChangedListener((Texture oldTexture, Texture newTexture) -> {
                //update hover texture
                //updateHoverTexture();

                invailde = true;
            });
        }

        if (this.textureRegionComponent != null) {
            this.textureRegionComponent.addTextureRegionChangedListener((TextureRegion oldTextureRegion, TextureRegion textureRegion) -> {
                //update hover texture
                //updateHoverTexture();

                invailde = true;
            });
        }

        //create hover texture
        updateHoverTexture();
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        Vector3 mousePos = game.getCamera().getMousePosition();

        float mouseX = mousePos.x;
        float mouseY = mousePos.y;

        this.hovered = false;

        float entityX = positionComponent.getX();
        float entityY = positionComponent.getY();
        float entityWidth = (this.width - (2 * padding)) * 1 / game.getCamera().getZoom();
        float entityHeight = (this.height - (2 * padding)) * 1 / game.getCamera().getZoom();

        //check, if entity is hovered
        if (mouseX >= entityX && mouseX <= (entityX + entityWidth)) {
            if (mouseY >= entityY && mouseY <= (entityY + entityHeight)) {
                this.hovered = true;
            }
        }

        if (this.hovered && this.invailde) {
            //update hover texture
            updateHoverTexture();
        }
    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.NORMAL;
    }

    @Override
    public void drawUILayer (GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //System.out.println("draw UI layer.");

        if (hoverTexture != null && this.hovered) {
            //float width = positionComponent.getWidth() + (2 * padding);
            //float height = positionComponent.getHeight() + (2 * padding);

            //draw hover texture behind entity
            batch.draw(this.hoverTexture, positionComponent.getX() - padding, positionComponent.getY() - padding, width, height);
        }
    }

    @Override public ECSPriority getUILayerDrawOrder() {
        return ECSPriority.DRAW_HOVER_EFFECT;
    }

    protected void updateHoverTexture () {
        Pixmap originalPixmap = null;
        TextureData textureData = null;

        int texWidth = 0;
        int texHeight = 0;

        int startX = padding;
        int startY = padding;

        if (this.textureComponent != null) {
            //get texture of entity
            Texture texture = this.textureComponent.getTexture();

            //calculate width and height of shadow texture
            texWidth = texture.getWidth();
            texHeight = texture.getHeight();
            this.width = texWidth + (2 * padding);
            this.height = texHeight + (2 * padding);

            //get and prepare texture data
            textureData = texture.getTextureData();
            textureData.prepare();

            //originalPixmap = textureData.consumePixmap();

            //create new pixmap
            originalPixmap = new Pixmap(texWidth, texHeight, Pixmap.Format.RGBA8888);

            //draw entity texture region into Pixmap
            originalPixmap.drawPixmap(textureData.consumePixmap(), 0, 0, texWidth, texHeight, 0, 0, originalPixmap.getWidth(), originalPixmap.getHeight());
        } else if (this.textureRegionComponent != null) {
            //get texture region of entity
            TextureRegion textureRegion = this.textureRegionComponent.getTextureRegion();

            if (textureRegion == null) {
                return;
            }

            //calculate width and height of shadow texture
            texWidth = textureRegion.getRegionWidth();
            texHeight = textureRegion.getRegionHeight();
            this.width = texWidth + (2 * padding);
            this.height = texHeight + (2 * padding);

            //get and prepare texture data
            textureData = textureRegion.getTexture().getTextureData();
            textureData.prepare();

            //create new pixmap
            originalPixmap = new Pixmap(texWidth, texHeight, Pixmap.Format.RGBA8888);

            //draw entity texture region into Pixmap
            originalPixmap.drawPixmap(textureData.consumePixmap(), textureRegion.getRegionX(), textureRegion.getRegionY(), texWidth, texHeight, 0, 0, originalPixmap.getWidth(), originalPixmap.getHeight());
        } else {
            throw new IllegalStateException("No texture component or texture region component is set to entity.");
        }

        Color color = new Color();

        Pixmap tmpPixmap = new Pixmap(texWidth, texHeight, Pixmap.Format.RGBA8888);
        Pixmap hoverPixmap = new Pixmap(this.width, this.height, Pixmap.Format.RGBA8888);

        //colorize Pixmap
        for (int x = 0; x < originalPixmap.getWidth(); x++) {
            for (int y = 0; y < originalPixmap.getHeight(); y++) {
                int x2 = x + startX;
                int y2 = y + startY;

                color.set(originalPixmap.getPixel(x, y));

                //get color alpha value
                float alpha = color.a;

                if (alpha > 0) {
                    tmpPixmap.setColor(this.hoverColor);

                    //draw pixel in shadow color
                    tmpPixmap.fillRectangle(x2, y2, 1, 1);
                } else {
                    tmpPixmap.setColor(new Color(0, 0, 0, 0));
                    tmpPixmap.fillRectangle(x2, y2, 1, 1);
                }
            }
        }

        hoverPixmap.drawPixmap(tmpPixmap, startX, startY, tmpPixmap.getWidth(), tmpPixmap.getHeight(), 0, 0, hoverPixmap.getWidth(), hoverPixmap.getHeight());

        //disable blending, else fillRectangle() will calculate new color with old color
        hoverPixmap.setBlending(Pixmap.Blending.None);

        //stencil texture
        for (int x = 0; x < originalPixmap.getWidth(); x++) {
            for (int y = 0; y < originalPixmap.getHeight(); y++) {
                int x2 = x + startX;
                int y2 = y + startY;

                color.set(originalPixmap.getPixel(x, y));

                //get color alpha value
                float alpha = color.a;

                if (alpha > 0) {
                    color.set(0, 0, 0, 0);
                    hoverPixmap.setColor(new Color(0, 0, 0, 0));

                    //draw transparency pixel
                    hoverPixmap.fillRectangle(x2, y2, 1, 1);
                }
            }
        }

        //remove old texture
        if (this.hoverTexture != null) {
            this.hoverTexture.dispose();
            this.hoverTexture = null;
        }

        this.hoverTexture = new Texture(hoverPixmap);

        textureData.disposePixmap();
        originalPixmap.dispose();
        tmpPixmap.dispose();
        hoverPixmap.dispose();

        this.invailde = false;
    }

    public boolean isHovered () {
        return this.hovered;
    }

    public void setHoverColor (Color hoverColor) {
        this.hoverColor = hoverColor;

        //generate new hover texture
        this.updateHoverTexture();
    }

}
