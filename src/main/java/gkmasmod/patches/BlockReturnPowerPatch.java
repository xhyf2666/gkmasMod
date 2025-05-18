package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;
import gkmasmod.powers.TopWisdomPlusPower;
import gkmasmod.powers.TopWisdomPower;


public class BlockReturnPowerPatch
{
    /**
     * 来自 无上智慧 的伤害，无法触发受击获得格挡的效果
     */
    @SpirePatch(clz = BlockReturnPower.class,method = "onAttacked")
    public static class BlockReturnPower_Prefix_onAttacked {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(BlockReturnPower _inst, DamageInfo info, int damageAmount) {
            if(info.name!=null&&(info.name.equals(TopWisdomPlusPower.POWER_ID)||info.name.equals(TopWisdomPower.POWER_ID))){
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}

