package gkmasmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.cards.special.Rumor;
import gkmasmod.powers.NotGoodTune;
import gkmasmod.vfx.effect.GakuenLinkMasterEffect;

public class InformationMasterAction extends AbstractGameAction {
    AbstractCreature owner;
    int type = 0;

    public InformationMasterAction(AbstractCreature owner,int type) {
        this.owner = owner;
        this.type = type;
    }

    public void update() {
        switch (type){
            case 0:
                for (int i = 0; i < AbstractDungeon.player.drawPile.size(); i++) {
                    AbstractCard c = AbstractDungeon.player.drawPile.getNCardFromTop(i);
                    if (c instanceof Rumor) {
                        AbstractDungeon.player.drawPile.moveToExhaustPile(c);
                        i--;
                    }
                }
                break;
            case 1:
                for (int i = 0; i < AbstractDungeon.player.hand.size(); i++) {
                    AbstractCard c = AbstractDungeon.player.hand.getNCardFromTop(i);
                    if (c instanceof Rumor) {
                        AbstractDungeon.player.hand.moveToDeck(c,true);
                        i--;
                    }
                }
                break;
            case 2:
                addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player, NotGoodTune.POWER_ID));
                break;
        }
        this.isDone = true;
    }
}