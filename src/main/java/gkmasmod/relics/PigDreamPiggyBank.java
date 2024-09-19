package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.powers.GoodImpression;

public class PigDreamPiggyBank extends CustomRelic {

    private static final String CLASSNAME = PigDreamPiggyBank.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 3;
    private static final int magicNumber2 = 1;

    private static final int GOOD_IMPRESSION = 6;

    private static final  int playTimes = 1;


    public PigDreamPiggyBank() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],GOOD_IMPRESSION,magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PigDreamPiggyBank();
    }


    public void onEquip() {}

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (this.counter > 0) {
            int count = AbstractDungeon.player.getPower(GoodImpression.POWER_ID)==null?0:AbstractDungeon.player.getPower(GoodImpression.POWER_ID).amount;
            if(count>GOOD_IMPRESSION){
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodImpression(AbstractDungeon.player, magicNumber), magicNumber));
                addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,1));
                this.counter--;
                if (this.counter == 0) {
                    this.grayscale = true;
                }
            }
        }

    }

    public void atBattleStart() {
        this.counter = playTimes;
    }

    public  void  onPlayerEndTurn(){
    }

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
