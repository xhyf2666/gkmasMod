package gkmasmod.vfx.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


public class RinhaFallEffect extends AbstractGameEffect {
    private AbstractCreature p;

    float targetY = 0.0F;

    private float suctionTimer=0.6F;

    public RinhaFallEffect(AbstractCreature p,float targetY) {
        this.p = p;
        this.targetY = targetY;
    }


    public void update() {

        if (this.suctionTimer > 0.0F) {
            this.suctionTimer -= Gdx.graphics.getDeltaTime();
        } else {
            p.drawY = MathUtils.lerp(p.drawY, this.targetY, Gdx.graphics.getDeltaTime() * 3.0F);
            if (Math.abs(p.drawY- this.targetY) < 10.0F) {
                p.drawY = this.targetY;
                this.isDone = true;
            }
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }


    public void dispose() {
    }

}
