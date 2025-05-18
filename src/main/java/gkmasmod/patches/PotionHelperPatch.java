package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.potion.*;
import gkmasmod.relics.PledgePetal;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.ImageHelper;
import org.apache.logging.log4j.Logger;
import javassist.CtBehavior;

import java.util.ArrayList;


public class PotionHelperPatch
{
    @SpirePatch(clz = PotionHelper.class,method = "getPotions",paramtypez = {AbstractPlayer.PlayerClass.class, boolean.class})
    public static class InsertPatchPotionHelper_getPotions{
        @SpireInsertPatch(rloc = 2,localvars = {"retVal"})
        public static void Insert(AbstractPlayer.PlayerClass c, boolean getAll,@ByRef ArrayList<String>[] retVal) {
            ArrayList<String> tmp =new ArrayList<>();
            if(AbstractDungeon.player instanceof IdolCharacter||AbstractDungeon.player instanceof MisuzuCharacter||AbstractDungeon.player instanceof OtherIdolCharacter){
                if(!getAll){
                    CommonEnum.IdolType type  = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
                    if(AbstractDungeon.player instanceof MisuzuCharacter){
                        type = CommonEnum.IdolType.SENSE;
                    }
                    if(AbstractDungeon.player instanceof OtherIdolCharacter){
                        type = IdolData.getOtherIdol(OtherSkinSelectScreen.Inst.idolIndex).getType(OtherSkinSelectScreen.Inst.skinIndex);
                    }
                    if(type==CommonEnum.IdolType.SENSE){
                        tmp.add(VitaminDrink.ID);
                        tmp.add(IcedCoffee.ID);
                        tmp.add(StaminaExplosionDrink.ID);
                        tmp.add(SelectFirstStarMacchiato.ID);
                        tmp.add(FirstStarBoostEnergy.ID);
                        tmp.add(FirstStarBlackVinegar.ID);
                    }
                    else if(type==CommonEnum.IdolType.LOGIC){
                        tmp.add(RooibosTea.ID);
                        tmp.add(HotCoffee.ID);
                        tmp.add(StylishHerbalTea.ID);
                        tmp.add(SelectFirstStarTea.ID);
                        tmp.add(SelectFirstStarBlend.ID);
                        tmp.add(SpecialFirstStarExtract.ID);
                    }
                    else if(type== CommonEnum.IdolType.ANOMALY){
                        tmp.add(GingerAle.ID);
                        tmp.add(Hojicha.ID);
                        tmp.add(HotGreenTea.ID);
                        tmp.add(SelectFirstStarChai.ID);
                        tmp.add(SelectFirstStarMilkTea.ID);
                        tmp.add(FirstStarSoup.ID);
                    }
                    else if(type== CommonEnum.IdolType.PRODUCE){
                        tmp.add(VitaminDrink.ID);
                        tmp.add(IcedCoffee.ID);
                        tmp.add(StaminaExplosionDrink.ID);
                        tmp.add(SelectFirstStarMacchiato.ID);
                        tmp.add(FirstStarBoostEnergy.ID);
                        tmp.add(FirstStarBlackVinegar.ID);
                        tmp.add(RooibosTea.ID);
                        tmp.add(HotCoffee.ID);
                        tmp.add(StylishHerbalTea.ID);
                        tmp.add(SelectFirstStarTea.ID);
                        tmp.add(SelectFirstStarBlend.ID);
                        tmp.add(SpecialFirstStarExtract.ID);
                        tmp.add(GingerAle.ID);
                        tmp.add(Hojicha.ID);
                        tmp.add(HotGreenTea.ID);
                        tmp.add(SelectFirstStarChai.ID);
                        tmp.add(SelectFirstStarMilkTea.ID);
                        tmp.add(FirstStarSoup.ID);
                    }
                    retVal[0] = tmp;
                    return;
                }
            }
            if(getAll){
                tmp.add(VitaminDrink.ID);
                tmp.add(IcedCoffee.ID);
                tmp.add(StaminaExplosionDrink.ID);
                tmp.add(SelectFirstStarMacchiato.ID);
                tmp.add(FirstStarBoostEnergy.ID);
                tmp.add(FirstStarBlackVinegar.ID);
                tmp.add(RooibosTea.ID);
                tmp.add(HotCoffee.ID);
                tmp.add(StylishHerbalTea.ID);
                tmp.add(SelectFirstStarTea.ID);
                tmp.add(SelectFirstStarBlend.ID);
                tmp.add(SpecialFirstStarExtract.ID);
                tmp.add(GingerAle.ID);
                tmp.add(Hojicha.ID);
                tmp.add(HotGreenTea.ID);
                tmp.add(SelectFirstStarChai.ID);
                tmp.add(SelectFirstStarMilkTea.ID);
                tmp.add(FirstStarSoup.ID);
                retVal[0] = tmp;
            }
        }
    }

    @SpirePatch2(clz = PotionHelper.class, method = "getPotion")
    public static class PotionHelperGetPotion {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<AbstractPotion> patch(String name) {
            if (name.equals(VitaminDrink.ID)) {
                return SpireReturn.Return(new VitaminDrink());
            }
            if (name.equals(IcedCoffee.ID)) {
                return SpireReturn.Return(new IcedCoffee());
            }
            if (name.equals(StaminaExplosionDrink.ID)) {
                return SpireReturn.Return(new StaminaExplosionDrink());
            }
            if (name.equals(SelectFirstStarMacchiato.ID)) {
                return SpireReturn.Return(new SelectFirstStarMacchiato());
            }
            if (name.equals(FirstStarBoostEnergy.ID)) {
                return SpireReturn.Return(new FirstStarBoostEnergy());
            }
            if (name.equals(FirstStarBlackVinegar.ID)){
                return SpireReturn.Return(new FirstStarBlackVinegar());
            }
            if (name.equals(RooibosTea.ID)) {
                return SpireReturn.Return(new RooibosTea());
            }
            if (name.equals(HotCoffee.ID)) {
                return SpireReturn.Return(new HotCoffee());
            }
            if (name.equals(StylishHerbalTea.ID)) {
                return SpireReturn.Return(new StylishHerbalTea());
            }
            if (name.equals(SelectFirstStarTea.ID)) {
                return SpireReturn.Return(new SelectFirstStarTea());
            }
            if (name.equals(SelectFirstStarBlend.ID)) {
                return SpireReturn.Return(new SelectFirstStarBlend());
            }
            if (name.equals(SpecialFirstStarExtract.ID)) {
                return SpireReturn.Return(new SpecialFirstStarExtract());
            }
            if (name.equals(GingerAle.ID)) {
                return SpireReturn.Return(new GingerAle());
            }
            if (name.equals(Hojicha.ID)) {
                return SpireReturn.Return(new Hojicha());
            }
            if (name.equals(HotGreenTea.ID)) {
                return SpireReturn.Return(new HotGreenTea());
            }
            if (name.equals(SelectFirstStarChai.ID)) {
                return SpireReturn.Return(new SelectFirstStarChai());
            }
            if (name.equals(SelectFirstStarMilkTea.ID)) {
                return SpireReturn.Return(new SelectFirstStarMilkTea());
            }
            if (name.equals(FirstStarSoup.ID)) {
                return SpireReturn.Return(new FirstStarSoup());
            }

            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(Logger.class, "info");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }




}

