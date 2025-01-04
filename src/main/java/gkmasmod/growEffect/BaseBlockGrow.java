package gkmasmod.growEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BaseBlockGrow extends AbstractGrowEffect {

    public static String growID = "BaseBlockGrow";

    public AbstractCard.CardTarget originalTarget;

    private int originBaseBlock;

    public BaseBlockGrow(int damage) {
        this.amount = damage;
        growEffectID = growID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("growEffect:BaseBlockGrow").TEXT[0], this.amount);
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
        card.target = this.originalTarget;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if(this.originBaseBlock <=0){
            return this.amount;
        }
        return block + this.amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BaseBlockGrow(this.amount);
    }

}
