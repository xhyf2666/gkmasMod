package gkmasmod.room.supply;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import gkmasmod.room.specialTeach.SpecialTeachEffect;

public class SupplyOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("gkmasMod:SupplyOption");

    public static final String[] TEXT = uiStrings.TEXT;

    public SupplyOption(boolean active) {
        this.label = TEXT[0];
        this.usable = active;
        updateUsability(active);
    }

    public void updateUsability(boolean canUse) {
        this.description = canUse ? TEXT[1] : TEXT[2];
        this.img = ImageMaster.loadImage("gkmasModResource/img/room/Supply.png");
    }

    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new SupplyEffect());
            this.usable = false;
        }
    }
}
