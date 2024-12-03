package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.patches.AbstractPlayerPatch;


public class PushingTooHardAgainAction extends AbstractGameAction {
    private DamageInfo info;

    public PushingTooHardAgainAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
        } else {
            this.tickDuration();
            if (this.isDone) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY, false));
                this.target.damage(this.info);
                if (this.target.lastDamageTaken > 0) {
                    int count = this.target.lastDamageTaken;
                    if(this.source instanceof IdolCharacter){
                        IdolCharacter idol = (IdolCharacter) AbstractDungeon.player;
                        if(AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.get(AbstractDungeon.player).size()>0){
                            count = (int) (1.0f*count / (AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.get(AbstractDungeon.player)*1.0f));
                        }
                    }
                    this.addToTop(new GainBlockAction(this.source, count));
                    if (this.target.hb != null) {
                        this.addToTop(new VFXAction(new WallopEffect(this.target.lastDamageTaken, this.target.hb.cX, this.target.hb.cY)));
                    }
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                } else {
                    this.addToTop(new WaitAction(0.1F));
                }
            }

        }
    }
}
