package gkmasmod.room.shop;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import gkmasmod.modcore.GkmasMod;
import gkmasmod.relics.PocketBook;

public class AnotherShopEffect extends AbstractGameEffect {

    AnotherMerchant merchant;

    static public boolean isFinished= false;

    public AnotherShopEffect() {
        this.isFinished = false;
        this.merchant = new AnotherMerchant();
    }

    @Override
    public void update() {
        if(isFinished)
        {
            this.isDone = true;
        }
//        GkmasMod.shopScreen.update();
        //this.merchant.update();
        updatePurge();
    }


    private void updatePurge() {
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() &&AbstractDungeon.gridSelectScreen.forPurge==true) {
            AnotherShopScreen.purgeCard();
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                CardCrawlGame.metricData.addPurgedItem(card.getMetricID());
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.player.masterDeck.removeCard(card);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            ((AnotherShopScreen)BaseMod.getCustomScreen(AnotherShopScreen.Enum.AnotherShop_Screen)).purgeAvailable = false;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        //GkmasMod.shopScreen.render(sb);
        //this.merchant.render(sb);
    }

    @Override
    public void dispose() {

    }
}
