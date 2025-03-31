package gkmasmod.growEffect;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.cards.anomaly.FinalSpurt;
import gkmasmod.cards.anomaly.ShineBright;
import gkmasmod.cards.anomaly.StepByStep;
import gkmasmod.downfall.cards.anomaly.ENFinalSpurt;
import gkmasmod.downfall.cards.anomaly.ENShineBright;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import gkmasmod.downfall.charbosses.stances.ENFullPowerStance;
import gkmasmod.stances.FullPowerStance;
import org.lwjgl.Sys;

import java.util.Iterator;

public class AttackTimeGrow extends AbstractGrowEffect {

    public static String growID = "AttackTimeGrow";

    public AttackTimeGrow(int time) {
        this.amount = time;
        growEffectID = growID;
        priority = 15;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("growEffect:AttackTimeGrow").TEXT[0], this.amount);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if(card.baseDamage<0)
            return;
        if(card instanceof AbstractBossCard){
            if(card instanceof ENFinalSpurt ||card instanceof ENShineBright){
                if(!AbstractCharBoss.boss.stance.ID.equals(ENFullPowerStance.STANCE_ID))
                    return;
            }
            for (int i = 0; i < this.amount; i++) {
//                System.out.println("AttackTimeGrow: "+card.baseDamage);
                addToBot(new ModifyDamageAction(AbstractDungeon.player,new DamageInfo(AbstractCharBoss.boss,card.baseDamage,card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL,card,false));
            }
        }
        else{
            if(card instanceof FinalSpurt ||card instanceof StepByStep||card instanceof ShineBright){
                if(!AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID))
                    return;
            }
            for (int i = 0; i < this.amount; i++) {
                addToBot(new ModifyDamageAction(target,new DamageInfo(AbstractDungeon.player,card.baseDamage,card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL,card,false));
            }
        }
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new AttackTimeGrow(this.amount);
    }
}
