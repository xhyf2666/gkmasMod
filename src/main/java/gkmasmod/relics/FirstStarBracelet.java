package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;
import gkmasmod.patches.MapRoomNodePatch;
import gkmasmod.vfx.effect.ModifyCampfireSmithEffect;

import java.util.ArrayList;

public class FirstStarBracelet extends CustomRelic implements CustomSavable<Integer>{

    private static final String CLASSNAME = FirstStarBracelet.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    private static final int HP_LOST = 3;

    private static int playTimes =4;

    public FirstStarBracelet() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
        this.counter = playTimes;
    }

    public void afterVictory() {
        if(this.counter>0){
            if(MapRoomNodePatch.SPField.isSP.get(AbstractDungeon.getCurrMapNode())){
                if(AbstractDungeon.player.masterDeck.getUpgradableCards().size() > 0){
                    addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    this.flash();
                    AbstractDungeon.player.currentHealth -= HP_LOST;
                    AbstractDungeon.isScreenUp = false;
                    AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
                    AbstractDungeon.effectList.add(new ModifyCampfireSmithEffect());
                }
            }
        }
    }

    public void upgradeFinish(){
        this.counter--;
        if(this.counter==0){
            this.grayscale = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP_LOST,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FirstStarBracelet();
    }


    public void onEquip() {}

    public void atTurnStart() {
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }

    @Override
    public Integer onSave() {
        return this.counter;
    }

    @Override
    public void onLoad(Integer integer) {
        this.counter = integer;
        if(this.counter==0){
            this.grayscale = true;
        }
    }
}
