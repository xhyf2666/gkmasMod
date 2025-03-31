package gkmasmod.vfx.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ShineLinesEffect;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.SoundHelper;

import java.util.Iterator;


public class RinhaAttackEffect extends AbstractGameEffect {
    private static final float GRAVITY;
    private static final float START_VY;
    private static final float START_VY_JITTER;
    private static final float START_VX;
    private static final float START_VX_JITTER;
    private static final float TARGET_JITTER;
    private float rotationSpeed;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float targetX;
    private float targetY;
    private int type;
    private Texture img;
    private static Texture voImg = ImageMaster.loadImage("gkmasModResource/img/vfx/ttmr_fan.png");
    private static Texture daImg = ImageMaster.loadImage("gkmasModResource/img/vfx/krha_fan.png");
    private static Texture viImg = ImageMaster.loadImage("gkmasModResource/img/vfx/hmsz_fan.png");
    private float alpha;
    private float suctionTimer;
    private float staggerTimer;

    public RinhaAttackEffect(int type, float x, float y, float targetX, float targetY) {
        this.alpha = 0.0F;
        this.suctionTimer = 0.0F;
        this.type = type;
        switch (type){
            case 0:
                this.img = voImg;
                break;
            case 1:
                this.img = daImg;
                break;
            default:
                this.img = viImg;
                break;
        }
        this.x = x - (float)this.img.getWidth() / 2.0F;
        this.y = y - (float)this.img.getHeight() / 2.0F;
        this.targetX = targetX;
        this.targetY = targetY;
        this.vX = MathUtils.random(START_VX - 50.0F * Settings.scale, START_VX_JITTER);
        this.rotationSpeed = MathUtils.random(1000.0F, 2000.0F);

        this.vY = MathUtils.random(START_VY, START_VY_JITTER);
        this.scale = Settings.scale;
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    }


    public void update() {
        if (this.alpha != 1.0F) {
            this.alpha += Gdx.graphics.getDeltaTime() * 2.0F;
            if (this.alpha > 1.0F) {
                this.alpha = 1.0F;
            }

            this.color.a = this.alpha;
        }

        this.rotation += Gdx.graphics.getDeltaTime() * this.rotationSpeed;
        this.x += Gdx.graphics.getDeltaTime() * this.vX;
        this.y += Gdx.graphics.getDeltaTime() * this.vY;
        this.vY -= Gdx.graphics.getDeltaTime() * GRAVITY;
        if (this.suctionTimer > 0.0F) {
            this.suctionTimer -= Gdx.graphics.getDeltaTime();
        } else {
            this.vY = MathUtils.lerp(this.vY, 0.0F, Gdx.graphics.getDeltaTime() * 5.0F);
            this.vX = MathUtils.lerp(this.vX, 0.0F, Gdx.graphics.getDeltaTime() * 5.0F);
            this.x = MathUtils.lerp(this.x, this.targetX, Gdx.graphics.getDeltaTime() * 4.0F);
            this.y = MathUtils.lerp(this.y, this.targetY, Gdx.graphics.getDeltaTime() * 4.0F);
            if (Math.abs(this.x - this.targetX) < 20.0F) {
                this.isDone = true;
            }
        }

    }

    public void render(SpriteBatch sb) {
        if (!(this.staggerTimer > 0.0F)) {
            sb.setColor(this.color);
            sb.draw(this.img, this.x, this.y, 64, 64, 128, 128, Settings.scale, Settings.scale, rotation, 0, 0, 128, 128, false, false);
        }
    }

    public void dispose() {
    }

    static {
        GRAVITY = 2000.0F * Settings.scale;
        START_VY = 800.0F * Settings.scale;
        START_VY_JITTER = 400.0F * Settings.scale;
        START_VX = 200.0F * Settings.scale;
        START_VX_JITTER = 300.0F * Settings.scale;
        TARGET_JITTER = 50.0F * Settings.scale;
    }
}
