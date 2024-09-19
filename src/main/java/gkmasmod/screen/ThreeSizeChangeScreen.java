package gkmasmod.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ThreeSizeChangeScreen{
    private float delayTime;
    private boolean started;
    private float startValue;
    private float targetValue;
    private float currentValue;
    private float duration;
    private float elapsedTime;
    private float x, y;
    private Texture img;
    private float holdTime = 1.0f;
    public static ThreeSizeChangeScreen VoInst;
    public static ThreeSizeChangeScreen DaInst;
    public static ThreeSizeChangeScreen ViInst;
    private static Texture voImg = ImageMaster.loadImage("gkmasModResource/img/vfx/vo_small.png");
    private static Texture daImg = ImageMaster.loadImage("gkmasModResource/img/vfx/da_small.png");
    private static Texture viImg = ImageMaster.loadImage("gkmasModResource/img/vfx/vi_small.png");

    // 构造函数
    public ThreeSizeChangeScreen(int type, int startValue, int targetValue, float x, float y, float duration) {
        this.startValue = startValue;
        this.targetValue = targetValue;
        this.currentValue = startValue;
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
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.elapsedTime = 0.0f;
    }
    public ThreeSizeChangeScreen(int type, int startValue, int targetValue, float x, float y, float duration, float delayTime){
        this(type,startValue,targetValue,x,y,duration);
        this.delayTime = delayTime;
        this.started = false;
    }

    public void update() {
        if (!started) {
            delayTime -= Gdx.graphics.getDeltaTime();
            if (delayTime <= 0.0f) {
                started = true;
            }
            return;
        }
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (elapsedTime < duration) {
            float progress = Math.min(elapsedTime / duration, 1.0f);

            currentValue = MathUtils.lerp(startValue, targetValue, progress);
        } else {
            holdTime -= Gdx.graphics.getDeltaTime();
        }
    }

    public void render(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontRed,Integer.toString(Math.round(currentValue)), x, y);
        sb.setColor(Color.WHITE);
        sb.draw(this.img, this.x-45, this.y+10, (float)this.img.getWidth() / 2.0F, (float)this.img.getHeight() / 2.0F, (float)this.img.getWidth(), (float)this.img.getHeight(), 1.0F, 1.0F, 0, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
    }

    public void dispose() {
    }
}
