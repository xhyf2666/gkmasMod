package gkmasmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
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
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.powers.TempSavePower;
import gkmasmod.powers.WhereDreamsArePower;

public class FullPowerStance extends GkmasModStance {
    private static final String CLASSNAME = "FullPowerStance";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(CLASSNAME);

    private static long sfxId = -1L;

    public static final String STANCE_ID = "FullPowerStance";
    public static final String STANCE_ID2 = "FullPowerStanceExit";


    private static int BLOCK = 5;
    private static int ENTHUSIASTIC1 = 5;
    private static int ENTHUSIASTIC2 = 8;

    public FullPowerStance() {
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        updateDescription();
    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.2F;
                AbstractDungeon.effectsQueue.add(new DivinityParticleEffect());
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Divinity"));
        }
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(STANCE_ID2));
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage * 3.0F;
    }


    @Override
    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx();
        CardCrawlGame.sound.play("STANCE_ENTER_DIVINITY");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_DIVINITY");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PINK, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Divinity"));
        AbstractDungeon.actionManager.addToBottom(new GainTrainRoundPowerAction(AbstractDungeon.player, 1));
        //TODO 暂存加入手牌
        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
            TempSavePower power = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
            power.getInHand();
        }
        if(AbstractDungeon.player.hasPower(WhereDreamsArePower.POWER_ID)){
            WhereDreamsArePower power = (WhereDreamsArePower) AbstractDungeon.player.getPower(WhereDreamsArePower.POWER_ID);
            power.onSpecificTrigger();
        }
    }

    public void onExitStance() {
        stopIdleSfx();
    }

    @Override
    public void updateDescription() {

        this.name = stanceString.NAME;
        this.description = stanceString.DESCRIPTION[0];

    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }
    }
}
