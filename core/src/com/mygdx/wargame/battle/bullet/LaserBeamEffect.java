package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.screen.IsoUtils;

public class LaserBeamEffect extends AbstractBullet {

    private Texture startTexture = new Texture(Gdx.files.internal("bullets/LaserStart.png"));
    private Texture startOverlayTexture = new Texture(Gdx.files.internal("bullets/LaserStartOverlay.png"));
    private Texture middleTexture = new Texture(Gdx.files.internal("bullets/LaserMiddle.png"));
    private Texture middleOverlayTexture = new Texture(Gdx.files.internal("bullets/LaserMiddleOverlay.png"));

    private Texture sprite;

    public LaserBeamEffect(int length) {

        int parts = length / 64;
        System.out.println(parts);

        Pixmap pixmap = new Pixmap(length + 32, 64, Pixmap.Format.RGBA8888);
        pixmap.setColor(0x00000000);
        pixmap.fill();

        pixmap.setBlending(Pixmap.Blending.SourceOver);

        startTexture.getTextureData().prepare();
        pixmap.drawPixmap(startTexture.getTextureData().consumePixmap(), 0, 0);

        startOverlayTexture.getTextureData().prepare();
        pixmap.drawPixmap(startOverlayTexture.getTextureData().consumePixmap(), 0, 0);

        int i;
        for (i = 1; i <= parts; i++) {
            middleTexture.getTextureData().prepare();
            pixmap.drawPixmap(middleTexture.getTextureData().consumePixmap(), i * 64, 0);

            middleOverlayTexture.getTextureData().prepare();
            pixmap.drawPixmap(middleOverlayTexture.getTextureData().consumePixmap(), i * 64, 0);
        }

        int remainder = length - i * 64;

        middleTexture.getTextureData().prepare();
        pixmap.drawPixmap(middleTexture.getTextureData().consumePixmap(), i * 64, 0, 0, 0, remainder, 64);

        middleOverlayTexture.getTextureData().prepare();
        pixmap.drawPixmap(middleOverlayTexture.getTextureData().consumePixmap(), i * 64, 0, 0, 0, remainder, 64);


        sprite = new Texture(pixmap);
    }

    @Override
    public void draw(Batch batch,
                     float parentAlpha) {
        Vector2 v2 = IsoUtils.ISO_UTILS.worldToScreen(getX(), getY());
        batch.setColor(getColor());
        batch.draw(sprite, v2.x + 32, v2.y + 16, 32, 32, sprite.getWidth(), sprite.getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, sprite.getWidth(), sprite.getHeight(), false, false);
    }
}