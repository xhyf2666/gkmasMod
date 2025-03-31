package gkmasmod.downfall.charbosses.stances;


import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.PowerTip;

import java.util.ArrayList;

import com.megacrit.cardcrawl.stances.*;
import gkmasmod.stances.*;

public abstract class AbstractEnemyStance extends AbstractStance {
//    public String name;
//    public String description;
    protected ArrayList<PowerTip> tips = new ArrayList();
    protected Color c;
    protected static final int W = 512;
    protected Texture img;
    protected float angle;
    protected float particleTimer;
    protected float particleTimer2;

    public AbstractEnemyStance() {
        this.c = Color.WHITE.cpy();
        this.img = null;
        this.particleTimer = 0.0F;
        this.particleTimer2 = 0.0F;
    }

    public abstract void updateDescription();

    public void atStartOfTurn() {
    }

    public void onEndOfTurn() {
    }

    public void onEnterStance() {
    }

    public void onEnterSameStance() {

    }

    public void onExitStance() {
    }

    public float atDamageGive(float damage, DamageType type) {
        return damage;
    }

    public float atDamageGive(float damage, DamageType type, AbstractCard card) {
        return this.atDamageGive(damage, type);
    }

    public float atDamageReceive(float damage, DamageType damageType) {
        return damage;
    }

    public void onPlayCard(AbstractCard card) {
    }

    public void update() {
        this.updateAnimation();
    }

    public void updateAnimation() {
    }

    public void render(SpriteBatch sb) {
        if (this.img != null) {
            sb.setColor(this.c);
            sb.setBlendFunction(770, 1);
            sb.draw(this.img, AbstractCharBoss.boss.drawX - 256.0F + AbstractCharBoss.boss.animX, AbstractCharBoss.boss.drawY - 256.0F + AbstractCharBoss.boss.animY + AbstractCharBoss.boss.hb_h / 2.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, -this.angle, 0, 0, 512, 512, false, false);
            sb.setBlendFunction(770, 771);
        }
    }

    public void stopIdleSfx() {
    }

    public static AbstractEnemyStance getStanceFromName(String name) {
        if(name.equals(ENPreservationStance.STANCE_ID))
            return new ENPreservationStance();
        else if(name.equals(ENConcentrationStance.STANCE_ID))
            return new ENConcentrationStance();
        else if(name.equals(ENFullPowerStance.STANCE_ID))
            return new ENFullPowerStance();
        else if(name.equals(ENPreservationStance.STANCE_ID2))
            return new ENPreservationStance(1);
        else if(name.equals(ENConcentrationStance.STANCE_ID2))
            return new ENConcentrationStance(1);
        else if (name.equals("Calm")) {
            return new EnCalmStance();
        } else if (name.equals("Wrath")) {
            return new EnWrathStance();
        } else if (name.equals("Real Wrath")) {
            return new EnRealWrathStance();
        } else if (name.equals("Divinity")) {
            return new EnDivinityStance();
        } else if (name.equals("Neutral")) {
            return new EnNeutralStance();
        } else {
            return null;
        }
    }
}
