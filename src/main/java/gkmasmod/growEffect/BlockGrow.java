package gkmasmod.growEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlockGrow extends AbstractGrowEffect {

    public static String growID = "BlockGrow";

    public BlockGrow(int damage) {
        this.amount = damage;
        growEffectID = growID;
        priority = 10;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("growEffect:BlockGrow").TEXT[0], this.amount);
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        return block + this.amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BlockGrow(this.amount);
    }

}
