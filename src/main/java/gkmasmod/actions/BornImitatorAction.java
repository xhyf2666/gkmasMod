package gkmasmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.CardHelper;

import java.util.ArrayList;

public class BornImitatorAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean isUpgraded;
    private String rarityColor="";
    private boolean playInstantly = false;

    public BornImitatorAction(AbstractPlayer p, boolean isUpgraded) {
        this(p,isUpgraded, "",false);
    }

    public BornImitatorAction(AbstractPlayer p, boolean isUpgraded, String rarityColor, boolean playInstantly) {
        this.p = p;
        this.isUpgraded = isUpgraded;
        this.rarityColor = rarityColor;
        this.playInstantly = playInstantly;
    }

    public void update() {
        ArrayList<AbstractCard.CardColor> colorList = new ArrayList<>();
        colorList.add(PlayerColorEnum.gkmasModColorLogic);
        colorList.add(PlayerColorEnum.gkmasModColorSense);
        colorList.add(PlayerColorEnum.gkmasModColorAnomaly);
        ArrayList<AbstractCard> tmpPool = CardHelper.getAllIdolCards(colorList,rarityColor,"");
        AbstractCard card = tmpPool.get(AbstractDungeon.cardRandomRng.random(0,tmpPool.size()-1));
        if(isUpgraded)
            card.upgrade();
        if(playInstantly){
            addToBot(new NewQueueCardAction(card, true, false, true));
        }
        else
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, false, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
        this.isDone = true;
    }

}
