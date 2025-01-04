package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.GkmasCardTag;

public class SecondDamageCustom extends AbstractCardCustomEffect {

    public static String growID = "SecondDamageCustom";

    public SecondDamageCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SecondDamageCustom(this.amount);
    }

}
