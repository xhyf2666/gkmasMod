package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class EffectChangeCustom extends AbstractCardCustomEffect {

    public static String growID = "EffectChangeCustom";

    public EffectChangeCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EffectChangeCustom(this.amount);
    }

}
