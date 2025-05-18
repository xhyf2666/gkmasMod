package gkmasmod.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gkmasmod.patches.AbstractMonsterPatch;

import java.util.ArrayList;

import static gkmasmod.characters.PlayerColorEnum.*;

public class PlayerHelper {

    /**
     * 获取目标特定能力的层数
     * @param p 目标
     * @param powerID 能力ID
     * @return 能力层数
     */
    public static int getPowerAmount(AbstractCreature p, String powerID) {
        return p.getPower(powerID)==null?0:p.getPower(powerID).amount;
    }

    /**
     * 获取目标负面效果的种类数
     * @param p 目标
     * @return 负面效果的种类数
     */
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

    /**
     * 获取目标负面效果的总层数
     * @param p 目标
     * @return 负面效果的总层数
     */
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

    /**
     * 移除目标若干个负面效果
     * @param p 目标
     * @param amount 移除的数量
     */
    public static void removeNegativePower(AbstractCreature p,int amount){
        ArrayList<AbstractPower> powers = new ArrayList<>();
        for(AbstractPower power:p.powers){
            if(power.type== AbstractPower.PowerType.DEBUFF){
                powers.add(power);
            }
            if(power.type== AbstractPower.PowerType.BUFF&&power.canGoNegative&&power.amount<0){
                powers.add(power);
            }
        }
        for(int i=0;i<amount&&i<powers.size();i++){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p,powers.get(i)));
        }
    }

    /**
     * 移除目标所有负面效果
     * @param p 目标
     */
    public static void removeAllNegativePower(AbstractCreature p){
        ArrayList<AbstractPower> powers = new ArrayList<>();
        for(AbstractPower power:p.powers){
            if(power.type== AbstractPower.PowerType.DEBUFF){
                powers.add(power);
            }
            if(power.type== AbstractPower.PowerType.BUFF&&power.canGoNegative&&power.amount<0){
                powers.add(power);
            }
        }
        for(int i=0;i<powers.size();i++){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p,powers.get(i)));
        }
    }

    /**
     * 计算Mod的卡牌解锁率
     * @return 卡牌解锁率
     */
    public static float getCardRate() {
        int total = 0;
        int see = 0;
        for (AbstractCard card : CardLibrary.cards.values()) {
            if (card.color == gkmasModColorLogic || card.color == gkmasModColor || card.color == gkmasModColorSense|| card.color == gkmasModColorAnomaly) {
                total++;
                if(!UnlockTracker.isCardLocked(card.cardID))
                    see++;
            }
        }
        return 1.0f*see/total;
    }

}
