package gkmasmod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import gkmasmod.cards.free.Sleepy;
import gkmasmod.powers.SleepPower;

public class RewardItemPatch {
    @SpirePatch(clz = RewardItem.class,method = "applyGoldBonus")
    public static class InsertPatchRewardItem_applyGoldBonus {
        @SpireInsertPatch(rloc = 15,localvars = {"tmp"})
        public static void Insert(RewardItem __instance, boolean theft,int tmp) {
            if(MapRoomNodePatch.SPField.isSP.get(AbstractDungeon.getCurrMapNode())){
                __instance.bonusGold += MathUtils.round(tmp * 0.5F);
            }
        }
    }
}
