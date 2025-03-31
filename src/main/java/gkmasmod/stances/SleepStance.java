package gkmasmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.cards.special.ResultWillNotChange;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.WakeUpMisuzuPower;
import gkmasmod.powers.WantToSleep;
import gkmasmod.powers.WantToSleepEnemy;
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
    public void onEnterStance() {
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "我要睡觉了…", 1.0F, 2.0F));
//        AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.hb.cX + AbstractDungeon.player.dialogX - 50, AbstractDungeon.player.hb.cY + AbstractDungeon.player.dialogY + 50, 2.0F,, true));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new ResultWillNotChange()));
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            ((MisuzuCharacter) AbstractDungeon.player).refreshSkin(1);
        }
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            if(!mo.isDeadOrEscaped()&&!AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new WantToSleepEnemy(mo, 2), 2));
            }
        }
        for(AbstractMonster m:AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!m.isDeadOrEscaped()&&m.hasPower(WakeUpMisuzuPower.POWER_ID)){
                WakeUpMisuzuPower power = (WakeUpMisuzuPower) m.getPower(WakeUpMisuzuPower.POWER_ID);
                if(power.canWake()){
                    power.wake();
                }
                break;
            }
        }
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage * 0.67F;
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
