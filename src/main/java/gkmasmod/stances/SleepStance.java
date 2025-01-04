package gkmasmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import gkmasmod.powers.WantToSleep;
import gkmasmod.vfx.effect.StanceAuraEffect2;

public class SleepStance extends GkmasModStance {
    private static final String CLASSNAME = "SleepStance";

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(CLASSNAME);

    private static long sfxId = -1L;

    public static final String STANCE_ID = "SleepStance";


    private static int BLOCK = 5;
    private static int ENTHUSIASTIC1 = 5;
    private static int ENTHUSIASTIC2 = 8;

    public SleepStance() {
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
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect2(SleepStance.STANCE_ID));
        }
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(WakeStance.STANCE_ID));
    }

    public void onExitStance() {
        stopIdleSfx();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,2),2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,1),1));
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
