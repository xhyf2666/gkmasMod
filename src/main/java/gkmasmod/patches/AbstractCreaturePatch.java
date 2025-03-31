package gkmasmod.patches;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
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
import gkmasmod.modcore.GkmasMod;
import gkmasmod.powers.*;
import gkmasmod.relics.PocketBook;
import gkmasmod.utils.ImageHelper;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

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
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                int increase = MathUtils.floor(tmp);
                if(increase>99999){
                    increase = 99999;
                }
                if(__instance.isPlayer){
                    for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
                        if(!m.isDeadOrEscaped()&&m.hasPower(FateCommunityPower2.POWER_ID)){
                            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m,m, (int) (increase*0.5F)));
                        }
                    }
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
                if(block>99999){
                    block = 99999;
                }
                blockBakField.blockBak.set(__instance, block);

                int count = AbstractCreaturePatch.BlockField.ThisCombatBlock.get(__instance);
                AbstractCreaturePatch.BlockField.ThisCombatBlock.set(__instance, count+increase);
            }

        }
    }

    @SpirePatch(clz = AbstractCreature.class,method = "addBlock")
    public static class PostPatchAbstractCreature_addBlock2 {
        @SpireInsertPatch(rloc =37 ,localvars = {"tmp"})
        public static void Insert(AbstractCreature __instance, int blockAmount,float tmp) {
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(PocketBook.ID)){
                __instance.currentBlock = blockBakField.blockBak.get(__instance);
            }
        }
    }

    @SpirePatch(clz = AbstractCreature.class,method = "addBlock")
    public static class PostPatchAbstractCreature_addBlock3 {
        @SpireInsertPatch(rloc =484-479 ,localvars = {"tmp"})
        public static void Insert(AbstractCreature __instance, int blockAmount,float tmp) {
            if(tmp<=0)
                return;
            for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
                if(m.hasPower(FriendHiroPower2.POWER_ID)){
                    m.getPower(FriendHiroPower2.POWER_ID).onSpecificTrigger();
                }
            }
        }
    }

    @SpirePatch(clz = AbstractCreature.class,method = "loseBlock",paramtypez = {int.class, boolean.class})
    public static class PostPatchAbstractCreature_loseBlock {
        @SpireInsertPatch(rloc =0)
        public static SpireReturn Insert(AbstractCreature __instance, int amount, boolean noAnimation) {
            System.out.println("loseBlock");
            if(__instance.isPlayer&&AbstractDungeon.player.hasPower(TrainRoundLogicPower.POWER_ID)&& GkmasMod.loseBlock){
                GkmasMod.loseBlock = false;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
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
        sb.draw(ImageHelper.finalCircleBg, Settings.WIDTH / 2.0F - 500.0F*Settings.xScale, Settings.HEIGHT / 2.0F + 200.0F*Settings.yScale-200.0F, 256.0F, 256.0F, 512, 512, Settings.scale*0.5F, Settings.scale*0.5F, 0.0F, 0, 0, 512, 512, false, false);

        ArrayList<Integer> finalCircleRound = AbstractPlayerPatch.FinalCircleRoundField.finalCircleRound.get(AbstractDungeon.player);
        double finalDamageRate = AbstractPlayerPatch.FinalDamageRateField.finalDamageRate.get(AbstractDungeon.player);
        for(int i = 0; i<finalCircleRound.size();i++){
            sb.draw(ImageHelper.arcs[finalCircleRound.get(i)], Settings.WIDTH / 2.0F - 500.0F*Settings.xScale, Settings.HEIGHT / 2.0F + 200.0F*Settings.yScale-200.0F, 256.0F, 256.0F, 512, 512, Settings.scale*0.5F, Settings.scale*0.5F, 330.0F-30.F*i, 0, 0, 512, 512, false, false);
        }
        if(finalCircleRound.size()>9)
            FontHelper.renderSmartText(sb, FontHelper.bannerNameFont, String.valueOf(finalCircleRound.size()), Settings.WIDTH / 2.0F - 380.0F*Settings.xScale+90.0F, Settings.HEIGHT / 2.0F + 250.0F*Settings.yScale+40.0F, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR,1.5f);
        else{
            FontHelper.renderSmartText(sb, FontHelper.bannerNameFont, String.valueOf(finalCircleRound.size()), Settings.WIDTH / 2.0F - 360.0F*Settings.xScale+105.0F, Settings.HEIGHT / 2.0F + 250.0F*Settings.yScale+40.0F, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR,1.5f);
        }
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, String.format("%.0f%%",finalDamageRate*100), Settings.WIDTH / 2.0F - 400.0F*Settings.xScale+100.0F, Settings.HEIGHT / 2.0F + 325.0F*Settings.yScale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
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
