package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.ArrayList;

import gkmasmod.monster.beyond.*;
import gkmasmod.monster.friend.*;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(clz = AbstractCreature.class, method = "renderPowerIcons")
public class AnotherMultiLinePowersPatches {
    private static float offsetY = 0.0F;

    private static final int INITIAL_LIMIT = 8;

    private static int count = 0;

    private static boolean doingAmounts = false;

    public static boolean isNotInvis(AbstractPower p) {
        return !(p instanceof com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower);
    }

    public static float getOffsetY() {
        return offsetY;
    }

    public static void incrementOffsetY(float[] offsetX, AbstractPower p) {
        if (Loader.isModLoaded("mintyspire"))
            return;
        if (isNotInvis(p)) {
            count++;
            if(p.owner!=null&&(p.owner instanceof MonsterTemari1||p.owner instanceof MonsterSaki1 ||p.owner instanceof MonsterKotone1||p.owner instanceof MonsterMisuzu1 ||p.owner instanceof MonsterUme1 ||p.owner instanceof MonsterSena1||p.owner instanceof MonsterLilja1||p.owner instanceof MonsterSumika1 ||p.owner instanceof MonsterHiro1||p.owner instanceof MonsterChina1 ||p.owner instanceof MonsterRinami1 ||p.owner instanceof MonsterMao1)){
                if (count == 5) {
                    count = 0;
                    offsetY -= 38.0F * Settings.scale;
                    offsetX[0] = ((doingAmounts ? 0 : 10) - 48) * Settings.scale;
                }
            }
            else if(p.owner!=null&&(p.owner instanceof FriendTemari ||p.owner instanceof FriendRinha ||p.owner instanceof FriendHiro)||p.owner instanceof FriendChina ||p.owner instanceof FriendUme ||p.owner instanceof FriendKotone||p.owner instanceof FriendSena){
                if (count == 3) {
                    count = 0;
                    offsetY -= 38.0F * Settings.scale;
                    offsetX[0] = ((doingAmounts ? 0 : 10) - 48) * Settings.scale;
                }
            }
            else{
                if (count == INITIAL_LIMIT) {
                    count = 0;
                    offsetY -= 38.0F * Settings.scale;
                    offsetX[0] = ((doingAmounts ? 0 : 10) - 48) * Settings.scale;
                }
            }
        }
    }

    @SpirePrefixPatch
    public static void Prefix(AbstractCreature __instance, SpriteBatch sb, float x, float y) {
        offsetY = 0.0F;
        count = 0;
        doingAmounts = false;
    }

    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(AbstractCreature __instance, SpriteBatch sb, float x, float y) {
        offsetY = 0.0F;
        count = 0;
        doingAmounts = true;
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "powers");
            return new int[] { LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList(), (Matcher)fieldAccessMatcher)[1] };
        }
    }

    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                if (Loader.isModLoaded("mintyspire"))
                    return;
                if (m.getMethodName().equals("renderIcons")) {
                    m.replace("{if(!" + AnotherMultiLinePowersPatches.class
                            .getName() + ".isExcludedClass($0.owner)){$3 += " + AnotherMultiLinePowersPatches.class

                            .getName() + ".getOffsetY();$proceed($$);float[] offsetArr = new float[]{offset};" + AnotherMultiLinePowersPatches.class

                            .getName() + ".incrementOffsetY(offsetArr, p);offset = offsetArr[0];}else{$proceed($$);}}");
                } else if (m.getMethodName().equals("renderAmount")) {
                    m.replace("{if(!" + AnotherMultiLinePowersPatches.class
                            .getName() + ".isExcludedClass($0.owner)){$3 += " + AnotherMultiLinePowersPatches.class

                            .getName() + ".getOffsetY();$proceed($$);float[] offsetArr = new float[]{offset};" + AnotherMultiLinePowersPatches.class

                            .getName() + ".incrementOffsetY(offsetArr, p);offset = offsetArr[0];}else{$proceed($$);}}");
                }
            }
        };
    }

    public static boolean isExcludedClass(AbstractCreature c) {
        if (c instanceof AbstractPlayer) {
            String tmp = ((AbstractPlayer)c).getTitle(((AbstractPlayer)c).chosenClass);
            return (tmp.equals("the Bladedancer") || tmp.equals("The Anomaly"));
        }
        return false;
    }
}
