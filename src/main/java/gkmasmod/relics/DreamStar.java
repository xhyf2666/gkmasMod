package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.powers.TrainingResultPower;
import gkmasmod.powers.increaseModifyPower.FullPowerAddRatePower;
import gkmasmod.stances.FullPowerStance;

public class DreamStar extends CustomRelic {

    private static final String CLASSNAME = DreamStar.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 6;
    private static final int magicNumber2 = 1;
    private static final int magicNumber3 = 50;

    private int currentTimes = 0;

    private static final String POWER_ID = "DreamStarPower_%d";

    public DreamStar() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2,magicNumber3);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DreamStar();
    }


    public void onEquip() {}

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if(AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID)){
            this.counter++;
            if(this.counter >= magicNumber){
                this.counter = 0;
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new FullPowerAddRatePower(AbstractDungeon.player, magicNumber2,magicNumber3, String.format(POWER_ID, currentTimes)), magicNumber2));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new TrainingResultPower(AbstractDungeon.player, 1), 1));
                currentTimes++;
            }
        }
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    public  void  onPlayerEndTurn(){
    }

    public void justEnteredRoom(AbstractRoom room) {
        currentTimes = 0;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
