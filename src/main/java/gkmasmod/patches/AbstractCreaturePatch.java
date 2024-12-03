package gkmasmod.patches;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import gkmasmod.powers.SaySomethingToYouPower;
import gkmasmod.powers.SteelSoul;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ImageHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

public class AbstractCreaturePatch {

    @SpirePatch(clz = AbstractCreature.class,method = SpirePatch.CLASS)
    public static class blockBakField {
        public static SpireField<Integer> blockBak = new SpireField<>(() -> 0);
    }

    @SpirePatch(clz = AbstractCreature.class,method = SpirePatch.CLASS)
    public static class BlockField {
        public static SpireField<Integer> ThisCombatBlock = new SpireField<>(() -> 0);
    }

    @SpirePatch(clz = AbstractCreature.class,method = "addBlock")
    public static class PostPatchAbstractCreature_addBlock {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void Insert(AbstractCreature __instance, int blockAmount,float tmp) {
//            if(__instance.isPlayer){
                int increase = MathUtils.floor(tmp);
                if(increase>9999){
                    increase = 9999;
                }
                int block;
                if(__instance.hasPower(SteelSoul.POWER_ID)){
                    SteelSoul power = (SteelSoul)__instance.getPower(SteelSoul.POWER_ID);
                    power.onAddBlock(increase);
                    block = __instance.currentBlock;
                }
                else{
                    block = __instance.currentBlock + increase;
                }
                if(block>9999){
                    block = 9999;
                }
                blockBakField.blockBak.set(__instance, block);

                int count = AbstractCreaturePatch.BlockField.ThisCombatBlock.get(__instance);
            AbstractCreaturePatch.BlockField.ThisCombatBlock.set(__instance, count+increase);
//            }
        }
    }

    @SpirePatch(clz = AbstractCreature.class,method = "addBlock")
    public static class PostPatchAbstractCreature_addBlock2 {
        @SpireInsertPatch(rloc =37 ,localvars = {"tmp"})
        public static void Insert(AbstractCreature __instance, int blockAmount,float tmp) {
            if(AbstractDungeon.player.hasRelic(PocketBook.ID)){
                if(__instance.isPlayer){
                    __instance.currentBlock = blockBakField.blockBak.get(__instance);
                    return;
                }
            }
            __instance.currentBlock = blockBakField.blockBak.get(__instance);
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList(), (Matcher)methodCallMatcher);
        }
    }

    @SpirePatch(clz = AbstractCreature.class,method = "renderHealth")
    public static class PostPatchAbstractCreature_renderHealth {
        @SpirePostfixPatch
        public static void Postfix(AbstractCreature __instance,SpriteBatch sb) {
            if(__instance.isPlayer){
                if (AbstractPlayerPatch.IsRenderFinalCircleField.IsRenderFinalCircle.get(AbstractDungeon.player)) {
                    renderFinalCircle(__instance,sb);
                }
            }
        }
    }

    public static void renderFinalCircle(AbstractCreature __instance,SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(ImageHelper.finalCircleBg, Settings.WIDTH / 2.0F - 500.0F*Settings.xScale, Settings.HEIGHT / 2.0F + 000.0F*Settings.scale, 256.0F, 256.0F, 512, 512, Settings.scale*0.5F, Settings.scale*0.5F, 0.0F, 0, 0, 512, 512, false, false);

        ArrayList<Integer> finalCircleRound = AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.get(AbstractDungeon.player);
        double finalDamageRate = AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.get(AbstractDungeon.player);
        for(int i = 0; i<finalCircleRound.size();i++){
            sb.draw(ImageHelper.arcs[finalCircleRound.get(i)], Settings.WIDTH / 2.0F - 500.0F*Settings.xScale, Settings.HEIGHT / 2.0F + 000.0F*Settings.scale, 256.0F, 256.0F, 512, 512, Settings.scale*0.5F, Settings.scale*0.5F, 330.0F-30.F*i, 0, 0, 512, 512, false, false);
        }
        FontHelper.renderSmartText(sb, FontHelper.bannerNameFont, String.valueOf(finalCircleRound.size()), Settings.WIDTH / 2.0F - 275.0F*Settings.xScale, Settings.HEIGHT / 2.0F + 290.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR,1.5f);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, String.format("%.0f%%",finalDamageRate*100), Settings.WIDTH / 2.0F - 300.0F*Settings.xScale, Settings.HEIGHT / 2.0F + 325.0F*Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
    }

    @SpirePatch(clz = AbstractCreature.class,method = "decrementBlock")
    public static class PrefixPatchAbstractCreature_decrementBlock {
        public static SpireReturn<Integer> Prefix(AbstractCreature __instance, DamageInfo info, int damageAmount) {
            if(__instance.isPlayer){
                if(info.owner instanceof AbstractCharBoss){
                    if(AbstractCharBoss.boss.hasPower(SaySomethingToYouPower.POWER_ID)){
                        SaySomethingToYouPower power = (SaySomethingToYouPower)AbstractCharBoss.boss.getPower(SaySomethingToYouPower.POWER_ID);
                        if(!power.breakBlock&&__instance.currentBlock>0&&damageAmount>=__instance.currentBlock){
                            power.onBreakBlock(__instance);
                            return SpireReturn.Return(0);
                        }
                    }
                }
            }
            else{
            if(AbstractDungeon.player.hasPower(SaySomethingToYouPower.POWER_ID)){
                SaySomethingToYouPower power = (SaySomethingToYouPower)AbstractDungeon.player.getPower(SaySomethingToYouPower.POWER_ID);
                if(!power.breakBlock&&__instance.currentBlock>0&&damageAmount>=__instance.currentBlock){
                    power.onBreakBlock(__instance);
                    return SpireReturn.Return(0);
                }
            }
            }
            return SpireReturn.Continue();
        }
    }

}
