package gkmasmod.screen;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class SingleRelicViewScreen{
    public static SingleRelicViewScreen Inst;

    public SingleRelicViewScreen() {
        this.usedImg = ImageMaster.loadImage("gkmasModResource/img/meme/RollingSourceOfEnergy.png");
    }
    public Texture usedImg;

    public void update() {
        float centerX = (float)Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.5F;
    }


    public void render(SpriteBatch sb) {
        Color RC = new Color(0.0F, 204.0F, 255.0F, 1.0F);
        float centerX = (float) Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.5F;
        if (this.usedImg != null) {
            sb.draw(this.usedImg,centerX-250, centerY-100, 500, 500);
        }

    }

    static {
        Inst = new SingleRelicViewScreen();
    }

}
