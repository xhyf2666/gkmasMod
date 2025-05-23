package gkmasmod.cardGrowEffect;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.cards.anomaly.AccelerateLand;
import gkmasmod.cards.anomaly.CatchingDragonflies;
import gkmasmod.stances.FullPowerStance;

public class BlockTimeGrow extends AbstractGrowEffect {

    public static String growID = "BlockTimeGrow";

    public BlockTimeGrow(int time) {
        this.amount = time;
        growEffectID = growID;
        priority = 15;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + String.format(CardCrawlGame.languagePack.getUIString("growEffect:BlockTimeGrow").TEXT[0], this.amount);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if(card instanceof AccelerateLand ||card instanceof CatchingDragonflies){
            if(!AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID))
                return;
        }
        if(card.baseBlock<0)
            return;
        for (int i = 0; i < this.amount; i++) {
            addToBot(new GainBlockAction(AbstractDungeon.player, card.block));
        }
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new BlockTimeGrow(this.amount);
    }

}
