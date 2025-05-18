package gkmasmod.cardGrowEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.cards.anomaly.*;
import gkmasmod.stances.FullPowerStance;

public class BaseBlockGrow extends AbstractGrowEffect {

    public static String growID = "BaseBlockGrow";

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
        if(card instanceof AccelerateLand ||card instanceof CatchingDragonflies){
            if(!AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID))
                return;
        }
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
