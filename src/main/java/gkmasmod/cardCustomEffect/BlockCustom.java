package gkmasmod.cardCustomEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlockCustom extends AbstractCardCustomEffect {

    public static String growID = "BlockCustom";

    private int originBaseBlock;

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
    public void onInitialApplication(AbstractCard card) {
        this.originBaseBlock = card.baseBlock;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if(this.originBaseBlock <=0){
            AbstractCreature source = action.source;
            if(source == null){
                source = AbstractDungeon.player;
            }
            addToBot(new GainBlockAction(source, card.block));
        }
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.baseBlock = this.originBaseBlock;
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
