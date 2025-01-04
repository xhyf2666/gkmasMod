package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.ModifyDamageAction;

public class BlockTimeCustom extends AbstractCardCustomEffect {

    public static String growID = "BlockTimeCustom";

    public BlockTimeCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("customEffect:BlockTimeCustom").TEXT[0],this.amount);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if(card.baseBlock<0)
            return;
        for (int i = 0; i < this.amount; i++) {
            addToBot(new GainBlockWithPowerAction(AbstractDungeon.player, AbstractDungeon.player, card.baseBlock));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BlockTimeCustom(this.amount);
    }

}
