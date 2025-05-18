package gkmasmod.patches;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Chrysalis;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import gkmasmod.cardCustomEffect.HPMagicCustom;
import gkmasmod.cardCustomEffect.SecondMagicCustom;
import gkmasmod.cardCustomEffect.ThirdMagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.powers.CanNotPlayCardPower;
import gkmasmod.relics.PledgePetal;
import gkmasmod.relics.PocketBook;
import gkmasmod.stances.SleepStance;
import gkmasmod.stances.WakeStance;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.ImageHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class AbstractCardPatch
{

    /**
     * 在战斗中显示卡牌指导后的数值
     */
    @SpirePatch(clz = AbstractCard.class, method = "applyPowers")
    public static class PrePatchAbstractCard_applyPowers {
        public static void Prefix(AbstractCard __instance) {
            if(__instance instanceof GkmasCard){
                GkmasCard card = (GkmasCard)__instance;
                for(AbstractCardModifier mod : CardModifierManager.modifiers(card)) {
                    if(mod instanceof AbstractCardModifier && mod instanceof SecondMagicCustom) {
                        card.secondMagicNumber = card.baseSecondMagicNumber+(((SecondMagicCustom)mod).amount);
                    }
                    if(mod instanceof AbstractCardModifier && mod instanceof ThirdMagicCustom) {
                        card.thirdMagicNumber = card.baseThirdMagicNumber+(((ThirdMagicCustom)mod).amount);
                    }
                    if(mod instanceof AbstractCardModifier && mod instanceof HPMagicCustom){
                        card.HPMagicNumber = card.baseHPMagicNumber+(((HPMagicCustom)mod).amount);
                    }
                }
            }
        }
    }

    /**
     * 睡眠、梦游、无法出牌的判断
     */
    @SpirePatch(clz = AbstractCard.class,method = "hasEnoughEnergy")
    public static class PrePatchAbstractCard_hasEnoughEnergy {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
            if(AbstractDungeon.player.stance.ID.equals(SleepStance.STANCE_ID)&&!__instance.hasTag(GkmasCardTag.SLEEP_TAG)){
                return SpireReturn.Return(false);
            }
            if(AbstractDungeon.player.hasPower(CanNotPlayCardPower.POWER_ID))
            {
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }

    /**
     * 清醒姿态下可打出诅咒牌
     */
    @SpirePatch(clz = AbstractCard.class,method = "canUse")
    public static class PrePatchAbstractCard_canUse {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.type == AbstractCard.CardType.CURSE&&AbstractDungeon.player.stance.ID.equals(WakeStance.STANCE_ID)){
                if (__instance.cardPlayable(m) && __instance.hasEnoughEnergy())
                    return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractCard.class,method = SpirePatch.CLASS)
    public static class ThreeSizeTagField {
        public static SpireField<Integer> threeSizeTag = new SpireField<>(() -> -1);
    }

    /**
     * 战斗外卡牌升级事件的监听
     */
    @SpirePatch(clz = AbstractCard.class,method = "upgradeName")
    public static class PostPatchAbstractCard_upgradeName {
        public static void Postfix(AbstractCard __instance) {
            if(AbstractDungeon.currMapNode!=null&&AbstractDungeon.getCurrRoom()!=null&&((AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT)){
                if(AbstractDungeon.player.masterDeck.contains(__instance)){
                    if(AbstractDungeon.player.hasRelic(PledgePetal.ID)){
                        ((PledgePetal)AbstractDungeon.player.getRelic(PledgePetal.ID)).onUpgradeCard();
                    }
                }
            }
        }
    }

    /**
     * 获得卡牌时，生成随机三维TAG
     */
    @SpirePatch(
            clz = AbstractCard.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    String.class,
                    String.class,
                    String.class,
                    int.class,
                    String.class,
                    AbstractCard.CardType.class,
                    AbstractCard.CardColor.class,
                    AbstractCard.CardRarity.class,
                    AbstractCard.CardTarget.class,
                    DamageInfo.DamageType.class
            }
    )
    public static class PostPatchAbstractCard_Constructor {
        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance, String id, String name, String imgUrl, int cost, String rawDescription, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, DamageInfo.DamageType dType) {
            if(CardCrawlGame.isInARun() && AbstractDungeon.isPlayerInDungeon() && type!= AbstractCard.CardType.CURSE && type!= AbstractCard.CardType.STATUS){
                if(AbstractDungeon.currMapNode!=null && AbstractDungeon.getCurrRoom().phase!=AbstractRoom.RoomPhase.COMBAT &&AbstractDungeon.player.hasRelic(PocketBook.ID)&&!SingleCardViewPopup.isViewingUpgrade){
                    int tag = GkmasMod.threeSizeTagRng.random(0, 2);
                    ThreeSizeTagField.threeSizeTag.set(__instance, tag);
                }
            }
        }
    }

    /**
     * 绘制卡牌的三维TAG
     */
    @SpirePatch(clz = AbstractCard.class,method = "render",paramtypez = {SpriteBatch.class, boolean.class})
    public static class PostPatchAbstractCard_render {
        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance, SpriteBatch sb, boolean selected) {
            float offsetY = 400 * Settings.scale * __instance.drawScale / 2.0F;
            float offsetX = 280 * Settings.scale * __instance.drawScale / 2.0F;
            int tag = ThreeSizeTagField.threeSizeTag.get(__instance);
            if(tag == 0) {
                sb.draw(ImageHelper.VoTagImg, __instance.current_x - offsetX, __instance.current_y - offsetY, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            }
            else if(tag == 1) {
                sb.draw(ImageHelper.DaTagImg, __instance.current_x - offsetX, __instance.current_y - offsetY, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            }
            else if(tag == 2) {
                sb.draw(ImageHelper.ViTagImg, __instance.current_x - offsetX, __instance.current_y - offsetY, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            }
        }
    }

    /**
     * 复制卡牌时，保留三维TAG
     */
    @SpirePatch(clz = AbstractCard.class,method = "makeStatEquivalentCopy")
    public static class InsertPatchAbstractCard_makeStatEquivalentCopy {
        @SpireInsertPatch(rloc = 11,localvars = {"card"})
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            int tag = ThreeSizeTagField.threeSizeTag.get(__instance);
            if(tag != -1){
                ThreeSizeTagField.threeSizeTag.set(card, tag);
            }
        }
    }

    /**
     * 绘制崩坠卡牌的banner
     */
    @SpirePatch(clz=AbstractCard.class,method="renderBannerImage")
    public static class PrePatchAbstractCard_renderBannerImage{
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractCard __instance, SpriteBatch sb, float drawX, float drawY, Color ___renderColor) {
            if (!(__instance instanceof GkmasBossCard)) {
                return SpireReturn.Continue();
            }
            GkmasBossCard card = (GkmasBossCard) __instance;
            TextureAtlas.AtlasRegion region = card.getBannerSmallRegion();
            if (region == null) {
                return SpireReturn.Continue();
            }
            renderHelper(card, sb, ___renderColor, region, drawX, drawY);
            return SpireReturn.Return(null);
        }
    }

    /**
     * 绘制崩坠卡牌的边框(攻击牌)
     */
    @SpirePatch(clz=AbstractCard.class,method="renderAttackPortrait")
    public static class prePatchAbstractCard_renderAttackPortrait {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractCard __instance, SpriteBatch sb, float x, float y, Color ___renderColor)
        {
            if (!(__instance instanceof GkmasCard)) {
                return SpireReturn.Continue();
            }
            GkmasCard card = (GkmasCard) __instance;
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
                else{
                    return SpireReturn.Continue();
                }
            }
            if(color.equals("blue")) {
                renderHelper(card, sb, ___renderColor, ImageMaster.CARD_FRAME_ATTACK_UNCOMMON, x, y);
            }
            else if(color.equals("yellow")){
                renderHelper(card, sb, ___renderColor, ImageMaster.CARD_FRAME_ATTACK_RARE, x, y);
            }
            else if(color.equals("color")){
                renderHelper(card, sb, ___renderColor, ImageHelper.ATTACK_COLOR_REGION, x, y);
            }
            return SpireReturn.Return(null);
        }
    }

    /**
     * 绘制崩坠卡牌的边框(技能牌)
     */
    @SpirePatch(clz=AbstractCard.class, method="renderSkillPortrait")
    public static class prePatchAbstractCard_renderSkillPortrait {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractCard __instance, SpriteBatch sb, float x, float y, Color ___renderColor)
        {
            if (!(__instance instanceof GkmasCard)) {
                return SpireReturn.Continue();
            }

            GkmasCard card = (GkmasCard) __instance;
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
                else{
                    return SpireReturn.Continue();
                }
            }
            if(color.equals("blue")) {
                renderHelper(card, sb, ___renderColor, ImageMaster.CARD_FRAME_SKILL_UNCOMMON, x, y);
            }
            else if(color.equals("yellow")){
                renderHelper(card, sb, ___renderColor, ImageMaster.CARD_FRAME_SKILL_RARE, x, y);
            }
            else if(color.equals("color")){
                renderHelper(card, sb, ___renderColor, ImageHelper.SKILL_COLOR_REGION, x, y);
            }
            return SpireReturn.Return(null);
        }
    }

    /**
     * 绘制崩坠卡牌的边框(能力牌)
     */
    @SpirePatch(clz=AbstractCard.class, method="renderPowerPortrait")
    public static class AbstractCard_prePatch_renderPowerPortrait {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractCard __instance, SpriteBatch sb, float x, float y, Color ___renderColor)
        {
            if (!(__instance instanceof GkmasCard)) {
                return SpireReturn.Continue();
            }

            GkmasCard card = (GkmasCard) __instance;
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
                else{
                    return SpireReturn.Continue();
                }
            }
            if(color.equals("blue")) {
                renderHelper(card, sb, ___renderColor, ImageMaster.CARD_FRAME_POWER_UNCOMMON, x, y);
            }
            else if(color.equals("yellow")){
                renderHelper(card, sb, ___renderColor, ImageMaster.CARD_FRAME_POWER_RARE, x, y);
            }
            else if(color.equals("color")){
                renderHelper(card, sb, ___renderColor, ImageHelper.POWER_COLOR_REGION, x, y);
            }
            return SpireReturn.Return(null);
        }
    }

    /**
     * 绘制崩坠卡牌的背景
     */
    @SpirePatch(clz=AbstractCard.class, method="renderCardBg")
    public static class PrePatchAbstractCard_renderCardBg {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractCard __instance, SpriteBatch sb, float xPos, float yPos, Color ___renderColor) {
            if (!(__instance instanceof GkmasBossCard)) {
                return SpireReturn.Continue();
            }
            CardColor color = __instance.color;
            GkmasBossCard card = (GkmasBossCard) __instance;
            Texture texture = null;
            TextureAtlas.AtlasRegion region = null;
            if (card.textureBackgroundSmallImg != null && !card.textureBackgroundSmallImg.isEmpty()) {
                texture = card.getBackgroundSmallTexture();
            }
            else {
                switch (card.type) {
                    case POWER:
                        if (BaseMod.getPowerBgTexture(color) == null) {
                            BaseMod.savePowerBgTexture(color, ImageMaster.loadImage(BaseMod.getPowerBg(color)));
                        }
                        texture = BaseMod.getPowerBgTexture(color);
                        break;
                    case ATTACK:
                        if (BaseMod.getAttackBgTexture(color) == null) {
                            BaseMod.saveAttackBgTexture(color, ImageMaster.loadImage(BaseMod.getAttackBg(color)));
                        }
                        texture = BaseMod.getAttackBgTexture(color);
                        break;
                    case SKILL:
                        if (BaseMod.getSkillBgTexture(color) == null) {
                            BaseMod.saveSkillBgTexture(color, ImageMaster.loadImage(BaseMod.getSkillBg(color)));
                        }
                        texture = BaseMod.getSkillBgTexture(color);
                        break;
                    default:
                        region = ImageMaster.CARD_SKILL_BG_BLACK;
                        break;
                }
            }
            if (texture != null) {
                region = new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
            }
            if (region == null) {
                return SpireReturn.Continue();
            }
            renderHelper(card, sb, ___renderColor, region, xPos, yPos);
            return SpireReturn.Return(null);
        }
    }

    private static Method renderHelperMethod;
    private static Method renderHelperMethodWithScale;
    static {
        try {
            renderHelperMethod = AbstractCard.class.getDeclaredMethod("renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
            renderHelperMethod.setAccessible(true);
            renderHelperMethodWithScale = AbstractCard.class.getDeclaredMethod("renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class, float.class);
            renderHelperMethodWithScale.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void renderHelper(AbstractCard card, SpriteBatch sb, Color color, TextureAtlas.AtlasRegion region, float xPos, float yPos) {
        try {
            renderHelperMethod.invoke(card, sb, color, region, xPos, yPos);
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    private static void renderHelper(AbstractCard card, SpriteBatch sb, Color color, TextureAtlas.AtlasRegion region, float xPos, float yPos, float scale) {
        try {
            renderHelperMethodWithScale.invoke(card, sb, color, region, xPos, yPos, scale);
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制透明的学mod卡牌，移除卡名
     */
    @SpirePatch(clz=AbstractCard.class, method="renderTitle")
    public static class PrePatchAbstractCard_renderTitle {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (!(__instance instanceof GkmasCard)) {
                return SpireReturn.Continue();
            }
            GkmasCard card = (GkmasCard) __instance;
            String color = card.backGroundColor;
            if(color.equals(IdolData.empty)) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    /**
     * 绘制透明的学mod卡牌，移除卡牌类型
     */
    @SpirePatch(clz=AbstractCard.class, method="renderType")
    public static class PrePatchAbstractCard_renderType {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (!(__instance instanceof GkmasCard)) {
                return SpireReturn.Continue();
            }
            GkmasCard card = (GkmasCard) __instance;
            String color = card.backGroundColor;
            if(color.equals(IdolData.empty)) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    /**
     * 绘制透明的学mod卡牌，移除能量图标
     */
    @SpirePatch(clz=AbstractCard.class, method="renderEnergy")
    public static class AbstractCard_prePatch_renderEnergy {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (!(__instance instanceof GkmasCard)) {
                return SpireReturn.Continue();
            }
            GkmasCard card = (GkmasCard) __instance;
            String color = card.backGroundColor;
            if(color.equals(IdolData.empty)) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    /**
     * 绘制透明的学mod卡牌，移除卡牌背景
     */
    @SpirePatch(clz=AbstractCard.class, method="renderCardBg")
    public static class PrePatchAbstractCard_renderCardBg2 {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(AbstractCard __instance, SpriteBatch sb, float x, float y) {
            if (!(__instance instanceof GkmasCard)) {
                return SpireReturn.Continue();
            }
            GkmasCard card = (GkmasCard) __instance;
            String color = card.backGroundColor;
            if(color.equals(IdolData.empty)) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

}

