package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.logic.Sunbathing;
import gkmasmod.cards.sense.LightGait;
import gkmasmod.powers.BeyondSunSongPower;
import gkmasmod.powers.BeyondSunSongPower2;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.HalfDamageReceive;

public class BeyondSunSong extends CustomRelic {

    private static final String CLASSNAME = BeyondSunSong.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 1;
    private static final int magicNumber2 = 3;
    private static final int magicNumber3 = 2;
    private static final int magicNumber4 = 2;
    private static final int magicNumber5 = 2;
    private static final int magicNumber6 = 50;

    private static final  int playTimes = 1;

    private int turnCount=-1;

    public BeyondSunSong() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2,magicNumber3,magicNumber6,magicNumber4,magicNumber5,magicNumber6,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BeyondSunSong();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (card.cardID.equals(Sunbathing.ID)) {
            if (this.counter > 0) {
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, magicNumber), magicNumber));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new HalfDamageReceive(AbstractDungeon.player, magicNumber2), magicNumber2));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BeyondSunSongPower(AbstractDungeon.player, magicNumber3), magicNumber3));
                this.counter--;
                turnCount = magicNumber4;
                if (this.counter == 0) {
                    this.grayscale = true;
                }
            }
        }
    }

    public void atBattleStart() {
        this.grayscale = false;
        this.counter = playTimes;
    }

    @Override
    public void atTurnStartPostDraw() {
        if(turnCount>0){
            turnCount--;
        }
        if(turnCount==0){
            this.flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BeyondSunSongPower2(AbstractDungeon.player, magicNumber5), magicNumber5));
            turnCount--;
        }
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
