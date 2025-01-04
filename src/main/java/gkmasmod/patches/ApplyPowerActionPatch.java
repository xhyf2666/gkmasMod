package gkmasmod.patches;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.anomaly.TakeFlight;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.TempSavePower;
import gkmasmod.relics.*;

public class ApplyPowerActionPatch {
    private static AbstractPower lastPower = null;

    @SpirePatch(clz = ApplyPowerAction.class, method = "update")
    public static class PowerIncreasePatch {
        @SpirePostfixPatch
        public static void Postfix(ApplyPowerAction __instance, AbstractPower ___powerToApply) {
            if (___powerToApply.amount>0){
                if(__instance.target.isPlayer){
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
                    else if(___powerToApply instanceof FullPowerValue){
                        if(lastPower!=null&&lastPower==___powerToApply){
                            return;
                        }
                        else{
                            lastPower = ___powerToApply;
                        }
                        GameActionManagerPatch.FullPowerValueThisCombatField.fullPowerValueThisCombat.set(GameActionManagerPatch.FullPowerValueThisCombatField.fullPowerValueThisCombat.get()+___powerToApply.amount);
                        System.out.println("FullPowerValueThisCombat:"+GameActionManagerPatch.FullPowerValueThisCombatField.fullPowerValueThisCombat.get());
                        for(AbstractCard c:AbstractDungeon.player.hand.group){
                            if(c instanceof TakeFlight){
                                ((TakeFlight) c).onFullPowerValueIncrease(___powerToApply);
                            }
                        }
                        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
                            if(c instanceof TakeFlight){
                                ((TakeFlight) c).onFullPowerValueIncrease(___powerToApply);
                            }
                        }
                        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
                            if(c instanceof TakeFlight){
                                ((TakeFlight) c).onFullPowerValueIncrease(___powerToApply);
                            }
                        }
                        for(AbstractCard c:AbstractDungeon.player.exhaustPile.group) {
                            if (c instanceof TakeFlight) {
                                ((TakeFlight) c).onFullPowerValueIncrease(___powerToApply);
                            }
                        }
                        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
                            TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
                            for(AbstractCard c:tempSavePower.getCards()){
                                if(c instanceof TakeFlight){
                                    ((TakeFlight) c).onFullPowerValueIncrease(___powerToApply);
                                }
                            }
                        }
                    }
                }
                else if(__instance.target instanceof AbstractCharBoss){
                    if(___powerToApply instanceof GoodImpression){
                        if (AbstractCharBoss.boss!=null&&AbstractCharBoss.boss.hasRelic(CBR_GreenUniformBracelet.ID2)){
                            ((CBR_GreenUniformBracelet) AbstractCharBoss.boss.getRelic(CBR_GreenUniformBracelet.ID2)).onGoodImpressionIncrease();
                        }
                    }
                    else if(___powerToApply instanceof StrengthPower){
                        if (AbstractCharBoss.boss!=null&&AbstractCharBoss.boss.hasRelic(CBR_AfterSchoolDoodles.ID2)){
                            ((CBR_AfterSchoolDoodles) AbstractCharBoss.boss.getRelic(CBR_AfterSchoolDoodles.ID2)).onStrengthPowerIncrease();
                        }
                    }
                    else if(___powerToApply instanceof DexterityPower){
                        if (AbstractCharBoss.boss!=null&&AbstractCharBoss.boss.hasRelic(CBR_WishFulfillmentAmulet.ID2)){
                            ((CBR_WishFulfillmentAmulet) AbstractCharBoss.boss.getRelic(CBR_WishFulfillmentAmulet.ID2)).onDexterityPowerIncrease();
                        }
                        if(AbstractCharBoss.boss!=null&&AbstractCharBoss.boss.hasRelic(CBR_SecretTrainingCardigan.ID2)){
                            ((CBR_SecretTrainingCardigan) AbstractCharBoss.boss.getRelic(CBR_SecretTrainingCardigan.ID2)).onDexterityPowerIncrease();
                        }
                    }
                    else if(___powerToApply instanceof GoodTune){
                        if (AbstractCharBoss.boss!=null&&AbstractCharBoss.boss.hasRelic(CBR_DearLittlePrince.ID2)){
                            ((CBR_DearLittlePrince) AbstractCharBoss.boss.getRelic(CBR_DearLittlePrince.ID2)).onGoodTuneIncrease();
                        }
                    }
                }
            }
        }
    }
}
