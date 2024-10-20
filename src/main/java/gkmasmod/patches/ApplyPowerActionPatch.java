package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;
import gkmasmod.relics.*;

public class ApplyPowerActionPatch {

    @SpirePatch(clz = ApplyPowerAction.class, method = "update")
    public static class PowerIncreasePatch {
        @SpirePostfixPatch
        public static void Postfix(ApplyPowerAction __instance, AbstractPower ___powerToApply) {
            System.out.println(___powerToApply.ID);

            if (___powerToApply.amount>0){
                if(___powerToApply instanceof GoodImpression){
                    if (AbstractDungeon.player.hasRelic(GreenUniformBracelet.ID)){
                        ((GreenUniformBracelet) AbstractDungeon.player.getRelic(GreenUniformBracelet.ID)).onGoodImpressionIncrease();
                    }
                }
                else if(___powerToApply instanceof StrengthPower){
                    if (AbstractDungeon.player.hasRelic(AfterSchoolDoodles.ID)){
                        ((AfterSchoolDoodles) AbstractDungeon.player.getRelic(AfterSchoolDoodles.ID)).onStrengthPowerIncrease();
                    }
                }
                else if(___powerToApply instanceof DexterityPower){
                    if (AbstractDungeon.player.hasRelic(WishFulfillmentAmulet.ID)){
                        ((WishFulfillmentAmulet) AbstractDungeon.player.getRelic(WishFulfillmentAmulet.ID)).onDexterityPowerIncrease();
                    }
                    if(AbstractDungeon.player.hasRelic(SecretTrainingCardigan.ID)){
                        ((SecretTrainingCardigan) AbstractDungeon.player.getRelic(SecretTrainingCardigan.ID)).onDexterityPowerIncrease();
                    }
                }
                else if(___powerToApply instanceof GoodTune){
                    if (AbstractDungeon.player.hasRelic(DearLittlePrince.ID)){
                        ((DearLittlePrince) AbstractDungeon.player.getRelic(DearLittlePrince.ID)).onGoodTuneIncrease();
                    }
                }
            }
        }
    }
}
