package gkmasmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.powers.EnthusiasticPower;

public class PreservationStance extends GkmasModStance {
    private static final String CLASSNAME = "PreservationStance1";
    private static final String CLASSNAME2 = "PreservationStance2";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(CLASSNAME);

    private static final StanceStrings stanceString2 = CardCrawlGame.languagePack.getStanceString(CLASSNAME2);

    public static final String STANCE_ID = "PreservationStance";
    public static final String STANCE_ID2 = "PreservationStance2";

    public int stage = 0;

    private static long sfxId = -1L;

    private static int BLOCK = 5;
    private static int ENTHUSIASTIC1 = 3;
    private static int ENTHUSIASTIC2 = 5;

    public PreservationStance() {
        this(0);
    }

    public PreservationStance(int stage) {
        this.stage = stage;
        if(this.stage>1)
            this.stage = 1;
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        updateDescription();
    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.04F;
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(this.stage==1)
            return damage * 0.5F;
        else
            return damage * 0.7F;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if(this.stage==1)
            return damage * 0.5F;
        else
            return damage * 0.7F;
    }

    @Override
    public void onEnterSameStance() {
        if(this.stage==0){
            this.stage = 1;
        }
        updateDescription();
    }

    @Override
    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_CALM");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SKY, true));
    }

    public void onExitStance() {
        if(stage==1){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnthusiasticPower(AbstractDungeon.player, ENTHUSIASTIC2), ENTHUSIASTIC2));
            AbstractDungeon.actionManager.addToTop(new GainBlockWithPowerAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
        }
        else{
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnthusiasticPower(AbstractDungeon.player, ENTHUSIASTIC1), ENTHUSIASTIC1));
        }
        AbstractDungeon.actionManager.addToTop(new GainTrainRoundPowerAction(AbstractDungeon.player, 1));
        stopIdleSfx();
    }

    @Override
    public void updateDescription() {
        this.name = stanceString.NAME;
        this.description = stanceString.DESCRIPTION[0];
        if(this.stage==1){
            this.name = stanceString2.NAME;
            this.description = stanceString2.DESCRIPTION[0];
        }
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", sfxId);
            sfxId = -1L;
        }
    }
}
