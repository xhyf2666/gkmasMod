package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlockCustom extends AbstractCardCustomEffect {

    public static String growID = "BlockCustom";

    public BlockCustom(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card.baseBlock>0)
            return rawDescription;
        return rawDescription + " NL " + CardCrawlGame.languagePack.getUIString("customEffect:BlockCustom").TEXT[0];
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if(block>0)
            return block + this.amount;
        else
            return this.amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BlockCustom(this.amount);
    }

}
