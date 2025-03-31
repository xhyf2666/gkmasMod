package gkmasmod.patches;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import gkmasmod.cards.anomaly.AfterSchoolChat;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.downfall.bosses.AbstractIdolBoss;
import gkmasmod.downfall.cards.anomaly.ENTakeFlight;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.anomaly.TakeFlight;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.*;
import gkmasmod.relics.*;
import gkmasmod.utils.PlayerHelper;

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
                        if(lastPower!=null&&lastPower==___powerToApply){
                            return;
                        }
                        else{
                            lastPower = ___powerToApply;
                        }
                        if (AbstractDungeon.player.hasRelic(AfterSchoolDoodles.ID)){
                            ((AfterSchoolDoodles) AbstractDungeon.player.getRelic(AfterSchoolDoodles.ID)).onStrengthPowerIncrease();
                        }
                        if (AbstractDungeon.player.hasRelic(GiftForYou.ID)){
                            ((GiftForYou) AbstractDungeon.player.getRelic(GiftForYou.ID)).onStrengthPowerIncrease();
                        }
                        if(AbstractDungeon.player.hasPower(FateCommunityPower.POWER_ID)){
                            for(AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
                                if(!mo.isDeadOrEscaped()&&AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new StrengthPower(mo, ___powerToApply.amount), ___powerToApply.amount));
                                }
                            }
                        }
                        for(AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
                            if(!mo.isDeadOrEscaped()&&mo.hasPower(FateCommunityPower2.POWER_ID)){
                                int add = (int) (___powerToApply.amount *0.5F);
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new StrengthPower(mo, add), add));
                            }
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
                        int tmp = PlayerHelper.getPowerAmount(AbstractDungeon.player, ThunderWillStopSPPower.POWER_ID);
                        if(tmp>0){
                            AbstractDungeon.player.getPower(ThunderWillStopSPPower.POWER_ID).onSpecificTrigger();
                        }
                        tmp = PlayerHelper.getPowerAmount(AbstractDungeon.player, TrainRoundAnomalyPower.POWER_ID);
                        if(tmp>0){
                            AbstractDungeon.player.getPower(TrainRoundAnomalyPower.POWER_ID).onSpecificTrigger();
                        }
                        GameActionManagerPatch.FullPowerValueThisCombatField.fullPowerValueThisCombat.set(GameActionManagerPatch.FullPowerValueThisCombatField.fullPowerValueThisCombat.get()+___powerToApply.amount);
                        for(AbstractCard c:AbstractDungeon.player.hand.group){
                            if(c instanceof TakeFlight){
                                ((TakeFlight) c).onFullPowerValueIncrease(___powerToApply);
                            }
                            if(c instanceof AfterSchoolChat){
                                ((AfterSchoolChat) c).onFullPowerValueIncrease(___powerToApply);
                            }
                        }
                        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
                            if(c instanceof TakeFlight){
                                ((TakeFlight) c).onFullPowerValueIncrease(___powerToApply);
                            }
                            if(c instanceof AfterSchoolChat){
                                ((AfterSchoolChat) c).onFullPowerValueIncrease(___powerToApply);
                            }
                        }
                        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
                            if(c instanceof TakeFlight){
                                ((TakeFlight) c).onFullPowerValueIncrease(___powerToApply);
                            }
                            if(c instanceof AfterSchoolChat){
                                ((AfterSchoolChat) c).onFullPowerValueIncrease(___powerToApply);
                            }
                        }
                        for(AbstractCard c:AbstractDungeon.player.exhaustPile.group) {
                            if (c instanceof TakeFlight) {
                                ((TakeFlight) c).onFullPowerValueIncrease(___powerToApply);
                            }
                            if(c instanceof AfterSchoolChat){
                                ((AfterSchoolChat) c).onFullPowerValueIncrease(___powerToApply);
                            }
                        }
                        if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
                            TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
                            for(AbstractCard c:tempSavePower.getCards()){
                                if(c instanceof TakeFlight){
                                    ((TakeFlight) c).onFullPowerValueIncrease(___powerToApply);
                                }
                                if(c instanceof AfterSchoolChat){
                                    ((AfterSchoolChat) c).onFullPowerValueIncrease(___powerToApply);
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
                    else if(___powerToApply instanceof FullPowerValue){
                        if(lastPower!=null&&lastPower==___powerToApply){
                            return;
                        }
                        else{
                            lastPower = ___powerToApply;
                        }
                        for(AbstractCard c:AbstractCharBoss.boss.hand.group){
                            if(c instanceof ENTakeFlight){
                                ((ENTakeFlight) c).onFullPowerValueIncrease(___powerToApply);
                            }
                        }
                    }
                }
            }
        }
    }

    @SpirePatch(clz = ApplyPowerAction.class,method = "update")
    public static class InsertPatch_ApplyPowerAction_update {
        @SpireInsertPatch(rloc =209-141)
        public static SpireReturn<Void> Insert(ApplyPowerAction __instance, AbstractPower ___powerToApply,@ByRef float[] ___duration) {
            if (__instance.target.hasPower(NegativeNotPower.POWER_ID) &&
                    ___powerToApply.type == AbstractPower.PowerType.DEBUFF) {
                if(___powerToApply instanceof StrengthPower||___powerToApply instanceof DexterityPower||___powerToApply instanceof GoodImpression||___powerToApply instanceof GoodTune||___powerToApply instanceof FullPowerValue){
                    return SpireReturn.Continue();
                }
                AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(__instance.target, CardCrawlGame.languagePack.getUIString("ApplyPowerAction").TEXT[0]));
                ___duration[0] -= Gdx.graphics.getDeltaTime();
                CardCrawlGame.sound.play("NULLIFY_SFX");
                __instance.target.getPower(NegativeNotPower.POWER_ID).flashWithoutSound();
                __instance.target.getPower(NegativeNotPower.POWER_ID).onSpecificTrigger();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = ApplyPowerAction.class,method = "update")
    public static class InsertPatch_ApplyPowerAction_update2 {
        @SpireInsertPatch(rloc =152-141)
        public static SpireReturn<Void> Insert(ApplyPowerAction __instance, AbstractPower ___powerToApply) {
            if(__instance.target instanceof IdolCharacter ||__instance.target instanceof MisuzuCharacter){
                if(___powerToApply instanceof ArtifactPower){
                    __instance.isDone = true;
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(__instance.target, __instance.target, new NegativeNotPower(__instance.target, ___powerToApply.amount), ___powerToApply.amount));
                    return SpireReturn.Return(null);
                }
            }
            if(__instance.target instanceof AbstractIdolBoss){
                if(___powerToApply instanceof ArtifactPower){
                    __instance.isDone = true;
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(__instance.target, __instance.target, new NegativeNotPower(__instance.target, ___powerToApply.amount), ___powerToApply.amount));
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }
}
