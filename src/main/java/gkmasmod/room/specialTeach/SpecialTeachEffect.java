package gkmasmod.room.specialTeach;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import gkmasmod.cards.GkmasCard;
import gkmasmod.room.shop.AnotherMerchant;
import gkmasmod.room.shop.AnotherShopScreen;

import java.util.Iterator;

public class SpecialTeachEffect extends AbstractGameEffect {


    static public boolean isFinished= false;

    private static final float DUR = 1.5F;

    private boolean openedScreen = false;

    private Color screenColor = AbstractDungeon.fadeColor.cpy();

    public SpecialTeachEffect() {
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        this.isFinished = false;
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            updateBlackScreenColor();
        }
//        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.forUpgrade) {
//            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
//                AbstractDungeon.player.bottledCardUpgradeCheck(c);
//                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
//            }
//            AbstractDungeon.gridSelectScreen.selectedCards.clear();
//            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
//        }
        if (this.duration < 1.0F && !this.openedScreen) {
            this.openedScreen = true;
            BaseMod.openCustomScreen(SpecialTeachScreen.Enum.SpecialTeach_Screen,AbstractDungeon.player.masterDeck
                    .getUpgradableCards(), 1,"选择一张卡进行效果修改");
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public CardGroup getUpgradableCards() {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            if (c instanceof GkmasCard) {
                GkmasCard gkmasCard = (GkmasCard) c;
                if(gkmasCard.customEffectList.size()>0){
                    retVal.group.add(c);
                }
            }
        }

        return retVal;
    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);
        }
    }



    @Override
    public void render(SpriteBatch sb) {
    }

    @Override
    public void dispose() {

    }
}
