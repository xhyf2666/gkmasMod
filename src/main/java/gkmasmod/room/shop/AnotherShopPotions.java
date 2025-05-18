package gkmasmod.room.shop;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.MisuzuCharacter;
import gkmasmod.characters.OtherIdolCharacter;
import gkmasmod.potion.*;
import gkmasmod.screen.OtherSkinSelectScreen;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.CommonEnum;
import gkmasmod.utils.IdolData;

import java.util.ArrayList;
import java.util.Arrays;

public class AnotherShopPotions {
    public static AbstractPotion returnRandomPotion() {
        return returnRandomPotion(false);
    }

    public static AbstractPotion returnRandomPotion(boolean limited) {
        int roll = AbstractDungeon.potionRng.random(0, 99);
        if (roll < PotionHelper.POTION_COMMON_CHANCE) {
            return returnRandomPotion(AbstractPotion.PotionRarity.COMMON, limited);
        } else {
            return roll < PotionHelper.POTION_UNCOMMON_CHANCE + PotionHelper.POTION_COMMON_CHANCE ? returnRandomPotion(AbstractPotion.PotionRarity.UNCOMMON, limited) : returnRandomPotion(AbstractPotion.PotionRarity.RARE, limited);
        }
    }

    public static AbstractPotion returnRandomPotion(AbstractPotion.PotionRarity rarity, boolean limited) {
        AbstractPotion temp = PotionHelper.getRandomPotion();
        if(!(AbstractDungeon.player instanceof IdolCharacter)&&!(AbstractDungeon.player instanceof MisuzuCharacter)&&!(AbstractDungeon.player instanceof OtherIdolCharacter))
            return temp;
        CommonEnum.IdolType type = IdolData.getIdol(SkinSelectScreen.Inst.idolIndex).getType(SkinSelectScreen.Inst.skinIndex);
        if(AbstractDungeon.player instanceof MisuzuCharacter){
            type = CommonEnum.IdolType.SENSE;
        }
        if(AbstractDungeon.player instanceof OtherIdolCharacter){
            type = IdolData.getIdol(OtherSkinSelectScreen.Inst.idolIndex).getType(OtherSkinSelectScreen.Inst.skinIndex);
        }
        ArrayList<String> potions = new ArrayList<>();
        if (type == CommonEnum.IdolType.LOGIC) {
            potions = new ArrayList<>(Arrays.asList(BoostExtract.ID,
                    FirstStarSpecialAojiru.ID,
                    FirstStarWater.ID,
                    FirstStarWheyProtein.ID,
                    FreshVinegar.ID,
                    MixedSmoothie.ID,
                    OolongTea.ID,
                    RecoveryDrink.ID,
                    PowerfulHerbalDrink.ID,
                    HotCoffee.ID,
                    RooibosTea.ID,
                    SelectFirstStarBlend.ID,
                    SelectFirstStarTea.ID,
                    SpecialFirstStarExtract.ID,
                    StylishHerbalTea.ID));
        } else if (type == CommonEnum.IdolType.SENSE) {
            potions = new ArrayList<>(Arrays.asList(BoostExtract.ID,
                    FirstStarSpecialAojiru.ID,
                    FirstStarWater.ID,
                    FirstStarWheyProtein.ID,
                    FreshVinegar.ID,
                    MixedSmoothie.ID,
                    OolongTea.ID,
                    RecoveryDrink.ID,
                    PowerfulHerbalDrink.ID,
                    IcedCoffee.ID,
                    SelectFirstStarMacchiato.ID,
                    StaminaExplosionDrink.ID,
                    VitaminDrink.ID,
                    FirstStarBoostEnergy.ID));
        }
        else if (type == CommonEnum.IdolType.ANOMALY) {
            potions = new ArrayList<>(Arrays.asList(BoostExtract.ID,
                    FirstStarSpecialAojiru.ID,
                    FirstStarWater.ID,
                    FirstStarWheyProtein.ID,
                    FreshVinegar.ID,
                    MixedSmoothie.ID,
                    OolongTea.ID,
                    RecoveryDrink.ID,
                    PowerfulHerbalDrink.ID,
                    GingerAle.ID,
                    Hojicha.ID,
                    HotGreenTea.ID,
                    SelectFirstStarChai.ID,
                    SelectFirstStarMilkTea.ID,
                    FirstStarSoup.ID));
        }
        else if(type== CommonEnum.IdolType.PRODUCE){
            potions = new ArrayList<>(Arrays.asList(BoostExtract.ID,
                    FirstStarSpecialAojiru.ID,
                    FirstStarWater.ID,
                    FirstStarWheyProtein.ID,
                    FreshVinegar.ID,
                    MixedSmoothie.ID,
                    OolongTea.ID,
                    RecoveryDrink.ID,
                    PowerfulHerbalDrink.ID,
                    HotCoffee.ID,
                    RooibosTea.ID,
                    SelectFirstStarBlend.ID,
                    SelectFirstStarTea.ID,
                    SpecialFirstStarExtract.ID,
                    StylishHerbalTea.ID,
                    IcedCoffee.ID,
                    SelectFirstStarMacchiato.ID,
                    StaminaExplosionDrink.ID,
                    VitaminDrink.ID,
                    FirstStarBoostEnergy.ID,
                    GingerAle.ID,
                    Hojicha.ID,
                    HotGreenTea.ID,
                    SelectFirstStarChai.ID,
                    SelectFirstStarMilkTea.ID,
                    FirstStarSoup.ID
            ));
        }

        while((temp.rarity != rarity ||!potions.contains(temp.ID))) {
            temp = PotionHelper.getRandomPotion();
        }

        return temp;
    }
}
