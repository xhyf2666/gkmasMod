package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UpgradeAllHandCardAction extends AbstractGameAction {
    private AbstractPlayer p;

    public UpgradeAllHandCardAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.group.size() <= 0) {
                this.isDone = true;
                return;
            }
            CardGroup upgradeable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.hand.group) {
                if (c.canUpgrade() && c.type != AbstractCard.CardType.STATUS)
                    upgradeable.addToTop(c);
            }
            // 遍历upgradeable,升级所有牌
            for (AbstractCard c : upgradeable.group) {
                c.upgrade();
                c.superFlash();
                c.applyPowers();
            }
            this.isDone = true;
            return;


//            if (upgradeable.size() > 0) {
//                upgradeable.shuffle();
//                ((AbstractCard)upgradeable.group.get(0)).upgrade();
//                ((AbstractCard)upgradeable.group.get(0)).superFlash();
//                ((AbstractCard)upgradeable.group.get(0)).applyPowers();
//            }

        }
        tickDuration();
    }
}
