package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.MayhemPower;
import gkmasmod.cards.GkmasCardTag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class AutoPlayAction extends AbstractGameAction {
    private AbstractCard card;
    private boolean flag;

    /**
     * AutoPlayAction：自动打出指定卡
     * @param card 待打出的卡牌
     */
    public AutoPlayAction(AbstractCard card,boolean flag) {
        this.card = card;
        this.flag = flag;
    }

    public void update() {
        this.target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        card.current_y = -200.0F * Settings.scale;
        card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        card.target_y = (float)Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        if(flag){
            card.exhaustOnUseOnce = true;
            if(!card.hasTag(GkmasCardTag.OUTSIDE_TAG)){
                card.tags.add(GkmasCardTag.OUTSIDE_TAG);
            }
        }
        AbstractDungeon.player.limbo.group.add(card);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();
        this.addToTop(new NewQueueCardAction(card, this.target, false, true));
        this.addToTop(new UnlimboAction(card));
        this.isDone = true;
    }

}
