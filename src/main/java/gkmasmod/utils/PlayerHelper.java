package gkmasmod.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import static gkmasmod.characters.PlayerColorEnum.gkmasModColorLogic;
import static gkmasmod.characters.PlayerColorEnum.gkmasModColor;
import static gkmasmod.characters.PlayerColorEnum.gkmasModColorSense;

public class PlayerHelper {
    public static int getPowerAmount(AbstractPlayer p,String powerID) {
        return p.getPower(powerID)==null?0:p.getPower(powerID).amount;
    }

    public static float getCardRate() {
        int total = 0;
        int see = 0;
        for (AbstractCard card : CardLibrary.cards.values()) {
            if (card.color == gkmasModColorLogic || card.color == gkmasModColor || card.color == gkmasModColorSense) {
                total++;
                if(!UnlockTracker.isCardLocked(card.cardID))
                    see++;
            }
        }
        return 1.0f*see/total;
    }

}
