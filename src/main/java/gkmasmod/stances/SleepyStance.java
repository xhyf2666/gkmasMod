package gkmasmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.powers.TempSavePower;
import gkmasmod.powers.WantToSleep;
import gkmasmod.powers.WhereDreamsArePower;
import gkmasmod.vfx.effect.StanceAuraEffect2;

public class SleepyStance extends GkmasModStance {
    private static final String CLASSNAME = "SleepyStance";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(CLASSNAME);

    private static long sfxId = -1L;

    public static final String STANCE_ID = "SleepyStance";


    private static int BLOCK = 5;
    private static int ENTHUSIASTIC1 = 5;
    private static int ENTHUSIASTIC2 = 8;

    public SleepyStance() {
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        updateDescription();
    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.2F;
//                AbstractDungeon.effectsQueue.add(new DivinityParticleEffect());
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect2(SleepyStance.STANCE_ID));
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage * 0.75F;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage * 0.75F;
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new WantToSleep(AbstractDungeon.player,1)));
    }

    public void onExitStance() {
        stopIdleSfx();
    }


    @Override
    public void onEnterStance() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Sleepy()));
    }

    @Override
    public void updateDescription() {

        this.name = stanceString.NAME;
        this.description = stanceString.DESCRIPTION[0];

    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
//            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }
    }
}
