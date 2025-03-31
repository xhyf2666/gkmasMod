package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.special.Kiss;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.powers.*;
import gkmasmod.relics.PocketBook;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;

public class AbstractPlayerPatch {

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class ThreeSizeField {
        public static SpireField<int[]> threeSize = new SpireField<>(() -> new int[]{0,0,0});
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class PreThreeSizeField {
        public static SpireField<int[]> preThreeSize = new SpireField<>(() -> new int[]{0,0,0});
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class ThreeSizeRateField {
        public static SpireField<float[]> threeSizeRate = new SpireField<>(() -> new float[]{0f,0f,0f});
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class FinalCircleRoundField {
        public static SpireField<ArrayList<Integer>> finalCircleRound = new SpireField<>(() -> new ArrayList<>());
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class IsRenderFinalCircleField {
        public static SpireField<Boolean> IsRenderFinalCircle = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = AbstractPlayer.class,method = SpirePatch.CLASS)
    public static class FinalDamageRateField {
        public static SpireField<Double> finalDamageRate = new SpireField<>(() -> 1.0);
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "applyStartOfCombatLogic")
    public static class AbstractPlayerPrefixPatch_applyStartOfCombatLogic {
        @SpirePrefixPatch
        public static void Prefix() {
            AbstractCreaturePatch.BlockField.ThisCombatBlock.set(AbstractDungeon.player, 0);
        }
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "gainGold")
    public static class AbstractPlayerPrefixPatch_gainGold {
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance, int amount) {
            if (__instance.hasRelic("Ectoplasm")) {
                if(__instance instanceof IdolCharacter){
                    IdolCharacter idolCharacter = (IdolCharacter) __instance;
                    if(idolCharacter.idolData.idolName.equals(IdolData.fktn)){
                        if (amount <= 0) {
                        } else {
                            CardCrawlGame.goldGained += amount;
                            __instance.gold += amount;
                            for (AbstractRelic r : __instance.relics)
                                r.onGainGold();
                        }
                    }
                }
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "gainGold")
    public static class InsertPatchAbstractPlayer_gainGold {
        @SpireInsertPatch(rloc =865-855)
        public static SpireReturn<Void> Insert(AbstractPlayer __instance, int amount) {
            if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
                return SpireReturn.Continue();
            }
            for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()&&m.hasPower(FriendKotonePower2.POWER_ID)){
                    m.getPower(FriendKotonePower2.POWER_ID).onSpecificTrigger();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "loseGold")
    public static class InsertPatchAbstractPlayer_loseGold {
        @SpireInsertPatch(rloc =844-830)
        public static SpireReturn<Void> Insert(AbstractPlayer __instance, int goldAmount) {
            if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
                return SpireReturn.Continue();
            }
            for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()&&m.hasPower(FriendKotonePower2.POWER_ID)){
                    m.getPower(FriendKotonePower2.POWER_ID).onSpecificTrigger();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "damage")
    public static class InsertPatchAbstractPlayer_damage2 {
        @SpireInsertPatch(rloc =1761-1725,localvars = {"damageAmount"})
        public static SpireReturn<Void> Insert(AbstractPlayer __instance, DamageInfo info, @ByRef int[] damageAmount) {
            if(damageAmount[0]>0){
                for(AbstractCard card:AbstractDungeon.player.hand.group){
                    if(card instanceof Kiss){
                        AbstractDungeon.player.hand.moveToExhaustPile(card);
                        damageAmount[0] = 0;
                        break;
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "damage")
    public static class InsertPatchAbstractPlayer_damage {
        @SpireInsertPatch(rloc =1851-1725)
        public static SpireReturn<Void> Insert(AbstractPlayer __instance, DamageInfo info) {
            for(AbstractPower p:__instance.powers){
                if(p instanceof RebirthPower){
                    __instance.currentHealth = 0;
                    p.onSpecificTrigger();
                    return SpireReturn.Return(null);
                }
                if(p instanceof OneTimeFlash){
                    __instance.currentHealth = 0;
                    p.onSpecificTrigger();
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "switchedStance")
    public static class PostPatchAbstractPlayer_switchedStance {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance) {
            if(GkmasMod.needCheckCard!=null){
                GkmasMod.needCheckCard.switchedStance();
                GkmasMod.needCheckCard = null;
            }
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
                if(c instanceof GkmasCard){
                    c.switchedStance();
                }
            }
            if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
                TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
                for(AbstractCard c:tempSavePower.getCards()){
                    if(c instanceof GkmasCard){
                        c.switchedStance();
                    }
                }
            }
            if(AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID)){
                if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
                    TempSavePower power = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
                    power.getInHand();
                }
            }
            for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
                if(mo.hasPower(MisuzuNightPower.POWER_ID)){
                    mo.getPower(MisuzuNightPower.POWER_ID).onSpecificTrigger();
                }
            }

        }
    }

//    @SpirePatch(clz = AbstractPlayer.class,method = "renderPowerTips")
//    public static class AbstractPlayerPrefixPatch_renderPowerTips {
//        @SpirePrefixPatch
//        public static void Prefix(AbstractPlayer __instance) {
//            System.out.println(__instance.stance);
//            System.out.println(__instance.stance.ID);
//        }
//    }

}
