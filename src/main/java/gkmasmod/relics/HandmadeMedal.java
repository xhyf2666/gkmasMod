package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.PlayerHelper;

public class HandmadeMedal extends CustomRelic {

    private static final String CLASSNAME = HandmadeMedal.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;

    private static final int GOOD_IMPRESSION = 5;

//    private static final  int playTimes = 2;

    public HandmadeMedal() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


//    @Override
//    public void onVictory() {
//        this.counter = playTimes;
//    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],GOOD_IMPRESSION,magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HandmadeMedal();
    }


    public void onPlayerEndTurn() {
        int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, GoodImpression.POWER_ID);
        if(count>GOOD_IMPRESSION){
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodImpression(AbstractDungeon.player, magicNumber), magicNumber));
        }
    }

//    public void atBattleStart() {
//        this.counter = playTimes;
//    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
