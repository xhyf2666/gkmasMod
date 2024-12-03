package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Deca;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ThreeSizeHelper;


public class DecaPatch
{

    @SpirePatch(clz = Deca.class,method = "takeTurn")
    public static class DecaPrePatch_RenderCard{
        @SpirePrefixPatch
        public static void Prefix(Deca __instance) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if(__instance.nextMove ==2){
                    int change = ThreeSizeHelper.getHealthRate(2) -1;
                    for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, __instance, 16*change));
                        if (AbstractDungeon.ascensionLevel >= 19)
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, __instance, new PlatedArmorPower(m, 3*change), 3*change));
                    }
                }
            }

        }
    }
}

