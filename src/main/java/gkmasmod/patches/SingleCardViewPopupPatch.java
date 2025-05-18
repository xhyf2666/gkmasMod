package gkmasmod.patches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import gkmasmod.cards.CustomEffect;
import gkmasmod.cards.GkmasCard;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.ImageHelper;

import java.util.ArrayList;


public class SingleCardViewPopupPatch
{

    public static ArrayList<Integer> customLimit = null;
    public static ArrayList<Integer> customPrice = null;
    public static ArrayList<AbstractCardModifier> customModifier = null;
    public static ArrayList<String> customDescription = null;
    public static int customEffectLength=0;
    public static int customEffectIndex=0;
    public static ArrayList<AbstractCard> customCardList = null;
    public static AbstractCard customCard = null;
    public static int count=0;
    public static String customTip= CardCrawlGame.languagePack.getUIString("gkmasMod:SpecialTeachScreen").TEXT[6];

    @SpirePatch(clz = SingleCardViewPopup.class, method = "open", paramtypez = {AbstractCard.class, CardGroup.class})
    public static class PostfixPatchSingleCardView_open {

        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup _inst, AbstractCard card, CardGroup group) {
            if (card instanceof GkmasCard) {
                GkmasCard gkmasCard = (GkmasCard)card;
                if(gkmasCard.customEffectList!=null)
                    customEffectLength=gkmasCard.customEffectList.size();
                else{
                    return;
                }
                customPrice = new ArrayList<>();
                customLimit = new ArrayList<>();
                customModifier = new ArrayList<>();
                customDescription = new ArrayList<>();
                customCardList = new ArrayList<>();
                count = 0;
                for (ArrayList<CustomEffect> list : gkmasCard.customEffectList) {
                    ArrayList<Integer> tmpPrice = new ArrayList<>();
                    ArrayList<AbstractCardModifier> tmpModifier = new ArrayList<>();
                    ArrayList<String> tmpDesc = new ArrayList<>();
                    for (CustomEffect effect : list) {
                        tmpPrice.add(effect.getPrice());
                        tmpModifier.add(CustomHelper.getCardModifierByID(effect.getType(),effect.getAmount()));
                        tmpDesc.add(effect.getDescription());
                    }
                    AbstractCard tmpCard = gkmasCard.makeStatEquivalentCopy();
                    tmpCard.upgrade();
                    CustomHelper.custom(tmpCard, tmpModifier.get(0));
                    customCardList.add(tmpCard);
                    customPrice.add(tmpPrice.get(0));
                    customLimit.add(list.size());
                    customModifier.add(tmpModifier.get(0));
                    customDescription.add(tmpDesc.get(0));
                }
                customCard = customCardList.get(0);
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "close")
    public static class PostPatchSingleCardView_close {

        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup _inst) {
                customEffectLength=0;
                customPrice = null;
                customLimit = null;
                customModifier = null;
                customDescription = null;
                if(customCardList!=null){
                    for(AbstractCard card:customCardList){
                        CardModifierManager.removeAllModifiers(card,true);
                    }
                }

                customCardList = null;
                customCard = null;
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "update")
    public static class PostPatchSingleCardView_update {

        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup _inst) {
            if(customEffectLength<=0)
                return;
            count++;
            if(count%100==0){
                customEffectIndex=(customEffectIndex+1)%customEffectLength;
                customCard = customCardList.get(customEffectIndex);
                count=0;
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderTitle", paramtypez = {SpriteBatch.class})
    public static class PostPatchSingleCardView_renderTitle {
        private static float drawScale = 2.0F;
        private static float yOffsetBase = 690.0F;

        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup _inst, SpriteBatch sb, AbstractCard ___card, float ___current_y) {
            if (___card instanceof GkmasCard) {
                GkmasCard card = (GkmasCard)___card;
                card.renderCardHeader(sb, Settings.WIDTH / 2.0F, ___current_y, yOffsetBase, drawScale);
                if(customEffectLength>0){
                    card.renderCardCustom(sb);
                }
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class,method = "renderFrame")
    public static class InsertPatchSingleCardView_renderFrame {
        @SpireInsertPatch(rloc = 680-589, localvars = {"tmpImg"})
        public static SpireReturn<Void> Insert(SingleCardViewPopup __instance,SpriteBatch sb, AbstractCard ___card,@ByRef TextureAtlas.AtlasRegion[] tmpImg) {
            if(!(___card instanceof GkmasCard)){
                return SpireReturn.Continue();
            }
            GkmasCard card = (GkmasCard)___card;
            String color = card.bannerColor;
            if(color.equals("")){
                if(card.rarity== AbstractCard.CardRarity.COMMON){
                    color = "blue";
                }else if(card.rarity== AbstractCard.CardRarity.UNCOMMON){
                    color = "yellow";
                }
                else if(card.rarity== AbstractCard.CardRarity.RARE){
                    color = "color";
                }
            }
            if(color.equals("")){
                return SpireReturn.Continue();
            }
            else if(color.equals("blue")){
                if(card.type== AbstractCard.CardType.ATTACK)
                    tmpImg[0] = ImageMaster.CARD_FRAME_ATTACK_UNCOMMON_L;
                else if(card.type== AbstractCard.CardType.POWER)
                    tmpImg[0] = ImageMaster.CARD_FRAME_POWER_UNCOMMON_L;
                else
                    tmpImg[0] = ImageMaster.CARD_FRAME_SKILL_UNCOMMON_L;
            }
            else if(color.equals("yellow")){
                if(card.type== AbstractCard.CardType.ATTACK)
                    tmpImg[0] = ImageMaster.CARD_FRAME_ATTACK_RARE_L;
                else if(card.type== AbstractCard.CardType.POWER)
                    tmpImg[0] = ImageMaster.CARD_FRAME_POWER_RARE_L;
                else
                    tmpImg[0] = ImageMaster.CARD_FRAME_SKILL_RARE_L;
            }
            else if(color.equals("color")){
                if(card.type== AbstractCard.CardType.ATTACK)
                    tmpImg[0] = ImageHelper.ATTACK_COLOR_REGION_L;
                else if(card.type== AbstractCard.CardType.POWER)
                    tmpImg[0] = ImageHelper.POWER_COLOR_REGION_L;
                else
                    tmpImg[0] = ImageHelper.SKILL_COLOR_REGION_L;
            }
            return SpireReturn.Continue();
        }
    }
}

