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
        int damage  = calculateDamage(card,card.baseDamage,target);
        for (int i = 0; i < this.amount; i++) {
            addToBot(new ModifyDamageAction(target,new DamageInfo(AbstractDungeon.player,card.baseDamage,card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL,card,false));
        }
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new AttackTimeGrow(this.amount);
    }


    public int calculateDamage(AbstractCard card,int baseDamage,AbstractCreature m){
        AbstractPlayer player = AbstractDungeon.player;
        if (m != null) {
            float tmp = (float)baseDamage;
            Iterator var9 = player.relics.iterator();

            if(card!=null){
                while(var9.hasNext()) {
                    AbstractRelic r = (AbstractRelic)var9.next();
                    tmp = r.atDamageModify(tmp, card);
                }
            }

            AbstractPower p;
            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            tmp = player.stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL)) {
                p = (AbstractPower)var9.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            return MathUtils.floor(tmp);
        }
        return baseDamage;
    }
}
