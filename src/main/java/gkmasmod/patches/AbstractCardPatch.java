package gkmasmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import gkmasmod.cards.GkmasCard;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.PledgePetal;
import gkmasmod.utils.ImageHelper;


public class AbstractCardPatch
{

    @SpirePatch(clz = AbstractCard.class,method = SpirePatch.CLASS)
    public static class ThreeSizeTagField {
        public static SpireField<Integer> threeSizeTag = new SpireField<>(() -> -1);
    }

    @SpirePatch(clz = AbstractCard.class,method = "upgradeName")
    public static class CardUpgradePostPatch {
        public static void Postfix(AbstractCard __instance) {
            if(AbstractDungeon.currMapNode!=null&&AbstractDungeon.getCurrRoom()!=null&&((AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT)){
                if(AbstractDungeon.player.masterDeck.contains(__instance)){
                    System.out.println("upgradeName "+AbstractDungeon.screen.name()+" "+__instance.name);
                    if(AbstractDungeon.player.hasRelic(PledgePetal.ID)){
                        ((PledgePetal)AbstractDungeon.player.getRelic(PledgePetal.ID)).onUpgradeCard();
                    }
                }
            }
        }
    }

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
    public static class AbstractCardPostPatch_Constructor {
        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance, String id, String name, String imgUrl, int cost, String rawDescription, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, DamageInfo.DamageType dType) {
            if(CardCrawlGame.isInARun() && AbstractDungeon.isPlayerInDungeon() && type!= AbstractCard.CardType.CURSE && type!= AbstractCard.CardType.STATUS){
                if(AbstractDungeon.currMapNode!=null && AbstractDungeon.getCurrRoom().phase!=AbstractRoom.RoomPhase.COMBAT){
                    int tag = GkmasMod.threeSizeTagRng.random(0, 2);
                    ThreeSizeTagField.threeSizeTag.set(__instance, tag);
                }
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class,method = "render",paramtypez = {SpriteBatch.class, boolean.class})
    public static class AbstractCardPostPatch_RenderCard{
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

    @SpirePatch(clz = AbstractCard.class,method = "makeStatEquivalentCopy")
    public static class AbstractCardPostPatch_MakeStatEquivalentCopy{
        @SpireInsertPatch(rloc = 11,localvars = {"card"})
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            int tag = ThreeSizeTagField.threeSizeTag.get(__instance);
            if(tag != -1){
                ThreeSizeTagField.threeSizeTag.set(card, tag);
            }
        }
    }
}

