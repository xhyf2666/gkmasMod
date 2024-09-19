package gkmasmod.vfx.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class GainThreeSizeTextEffect extends AbstractGameEffect {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static int totalGold;
    private int gold = 0;
    private boolean reachedCenter = false;
    private float x;
    private float y;
    private float destinationY;
    private int type;
    private Texture img;
    private static Texture voImg = ImageMaster.loadImage("gkmasModResource/img/vfx/vo_small.png");
    private static Texture daImg = ImageMaster.loadImage("gkmasModResource/img/vfx/da_small.png");
    private static Texture viImg = ImageMaster.loadImage("gkmasModResource/img/vfx/vi_small.png");
    private static final float WAIT_TIME = 1.0F;
    private float waitTimer = 1.0F;
    private float fadeTimer = 1.0F;
    private static final float FADE_Y_SPEED;
    private static final float TEXT_DURATION = 3.0F;
    private String text;
    private int effectID;

    public GainThreeSizeTextEffect(int effectID,int type,int startingAmount) {
        this.x = AbstractDungeon.player.hb.cX + MathUtils.random(-60.0F, 60.0F) * Settings.scale;
        this.y = AbstractDungeon.player.hb.cY;
        this.effectID = effectID;
        this.type = type;
        switch (type){
            case 0:
                this.img = voImg;
                this.text = "Vo";
                break;
            case 1:
                this.img = daImg;
                this.text = "Da";
                break;
            default:
                this.img = viImg;
                this.text = "Vi";
                break;
        }
        this.destinationY = this.y + 150.0F * Settings.scale;
        this.duration = 3.0F;
        this.startingDuration = 3.0F;
        this.reachedCenter = false;
        this.gold = startingAmount;
        totalGold = startingAmount;
        this.color = Color.GOLD.cpy();
    }

    public void update() {
        if (this.waitTimer > 0.0F) {
            if (!this.reachedCenter && this.y != this.destinationY) {
                this.y = MathUtils.lerp(this.y, this.destinationY, Gdx.graphics.getDeltaTime() * 9.0F);
                if (Math.abs(this.y - this.destinationY) < Settings.UI_SNAP_THRESHOLD) {
                    this.y = this.destinationY;
                    this.reachedCenter = true;
                }
            } else {
                this.waitTimer -= Gdx.graphics.getDeltaTime();
            }
        } else {
            this.y += Gdx.graphics.getDeltaTime() * FADE_Y_SPEED;
            this.fadeTimer -= Gdx.graphics.getDeltaTime();
            this.color.a = this.fadeTimer;
            if (this.fadeTimer < 0.0F) {
                this.isDone = true;
            }
        }

    }

    public boolean ping(int amount,int effectID) {
        if (this.waitTimer > 0.0F) {
            this.waitTimer = 1.0F;
            return effectID == this.effectID;
        } else {
            return false;
        }
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            sb.setColor(Color.WHITE);
            sb.draw(this.img, this.x - (float)this.img.getWidth() / 2.0F-20F, this.y - (float)this.img.getHeight() / 2.0F, (float)this.img.getWidth() / 2.0F, (float)this.img.getHeight() / 2.0F, (float)this.img.getWidth(), (float)this.img.getHeight(), Settings.scale, Settings.scale, 0.0F, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, "+ " + (this.gold) + this.text, this.x, this.y + 20, this.color);
        }

    }

    public void dispose() {
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("GainGoldTextEffect");
        TEXT = uiStrings.TEXT;
        totalGold = 0;
        FADE_Y_SPEED = 100.0F * Settings.scale;
    }
}
