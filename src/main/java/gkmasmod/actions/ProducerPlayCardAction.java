package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.downfall.bosses.ProducerBoss;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;

import java.util.ArrayList;

public class ProducerPlayCardAction extends AbstractGameAction {

    public ProducerPlayCardAction() {
    }

    public void update() {
        this.isDone = true;
        if(AbstractCharBoss.boss!=null){
            if(AbstractCharBoss.boss instanceof ProducerBoss){
                ProducerBoss producerBoss = (ProducerBoss) AbstractCharBoss.boss;
                if(producerBoss.rebirth){
                    return;
                }
            }
            AbstractCharBoss.boss.useCard(AbstractCharBoss.boss.hand.group.get(0),AbstractCharBoss.boss,0);
            AbstractCharBoss.boss.hand.addToTop(ProducerBoss.getProducerCard());
            AbstractCharBoss.boss.hand.refreshHandLayout();
        }

    }


}
