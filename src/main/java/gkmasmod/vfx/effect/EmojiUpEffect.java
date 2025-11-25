package gkmasmod.vfx.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class EmojiUpEffect extends AbstractGameEffect {
    private static Texture img;
    private float x;
    private float y;
    private float vY;

    public EmojiUpEffect() {
        if (img == null) {
            img = ImageMaster.loadImage("gkmasModResource/img/UI/TemariToyEmoji.png");
        }
        this.x = AbstractDungeon.player.hb.cX+ MathUtils.random(-100.0F, 100.0F) * Settings.scale;
        this.y = AbstractDungeon.player.hb.cY+ MathUtils.random(0.0F, 60.0F) * Settings.scale;
        this.startingDuration = 3.0f;
        this.duration = this.startingDuration;
        this.vY = 250F * Settings.scale;
        this.color = Color.WHITE.cpy();
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        duration -= delta;

        y += vY * delta;

        // 透明度随时间减少
        color.a = duration / startingDuration;

        if (duration <= 0.0f) {
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.draw(img, x, y, img.getWidth(), img.getHeight());
    }

    public void dispose() {
    }
}
