package gkmasmod.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.patches.AbstractMonsterPatch;

import static gkmasmod.characters.PlayerColorEnum.gkmasModColorLogic;
import static gkmasmod.characters.PlayerColorEnum.gkmasModColor;
import static gkmasmod.characters.PlayerColorEnum.gkmasModColorSense;

public class PlayerHelper {
    public static int getPowerAmount(AbstractCreature p, String powerID) {
        return p.getPower(powerID)==null?0:p.getPower(powerID).amount;
    }

    public static int getNegativePowerAmount(AbstractCreature p) {
        int count = 0;
        for(AbstractPower power:p.powers){
            if(power.type== AbstractPower.PowerType.DEBUFF){
                count++;
            }
            if(power.type== AbstractPower.PowerType.BUFF&&power.canGoNegative&&power.amount<0){
                count++;
            }
        }
        return count;
    }

    public static int getNegativePowerTotalAmount(AbstractCreature p) {
        int count = 0;
        for(AbstractPower power:p.powers){
            if(power.type== AbstractPower.PowerType.DEBUFF){
                count+=power.amount>0?power.amount:1;
            }
            if(power.type== AbstractPower.PowerType.BUFF&&power.canGoNegative&&power.amount<0){
                count+=-power.amount;
            }
        }
        return count;
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
