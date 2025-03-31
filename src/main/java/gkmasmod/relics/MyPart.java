package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.actions.MyPartGrowAction;
import gkmasmod.cards.anomaly.Starlight;
import gkmasmod.cards.logic.Smile;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.HalfDamageReceive;
import gkmasmod.stances.PreservationStance;

public class MyPart extends CustomRelic {

    private static final String CLASSNAME = MyPart.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;
    private static final int magicNumber2 = 1;
    private static final int magicNumber3 = 4;
    private static final int HP_LOSS = 2;

    private static final  int playTimes = 3;


    public MyPart() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP_LOSS,magicNumber,magicNumber2,magicNumber3,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MyPart();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (card.cardID.equals(Starlight.ID)&&AbstractDungeon.player.stance.ID.equals(PreservationStance.STANCE_ID)) {
            if (this.counter > 0) {
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
                addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP_LOSS));
                addToBot(new DrawCardAction(magicNumber));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new HalfDamageReceive(AbstractDungeon.player, magicNumber2), magicNumber2));
                addToBot(new MyPartGrowAction(magicNumber3));
                this.counter--;
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
