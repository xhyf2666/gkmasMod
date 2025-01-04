package gkmasmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.powers.EnthusiasticPower;

public class ConcentrationStance extends GkmasModStance {
    private static final String CLASSNAME = "ConcentrationStance1";
    private static final String CLASSNAME2 = "ConcentrationStance2";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(CLASSNAME);

    private static final StanceStrings stanceString2 = CardCrawlGame.languagePack.getStanceString(CLASSNAME2);

    public static final String STANCE_ID = "ConcentrationStance";
    public static final String STANCE_ID2 = "ConcentrationStance2";

    public int stage = 0;

    private static long sfxId = -1L;

    public ConcentrationStance() {
        this(0);
    }

    public ConcentrationStance(int stage) {
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
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new WrathParticleEffect());
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(this.stage==1)
            return damage * 2.0F;
        else
            return damage * 1.5F;
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        if(this.stage==1){
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 1));
        }
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if(this.stage==1)
            return damage * 2.0F;
        else
            return damage * 1.5F;
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
        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));
    }

    public void onExitStance() {
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
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }
    }
}
