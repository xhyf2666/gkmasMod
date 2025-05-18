package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GrowAction;
import gkmasmod.cards.anomaly.JustAppeal;
import gkmasmod.cardGrowEffect.AttackTimeGrow;
import gkmasmod.cardGrowEffect.EnergyGrow;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.powers.TempSavePower;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.PlayerHelper;

public class AchieveDreamAwakening extends CustomRelic {

    private static final String CLASSNAME = AchieveDreamAwakening.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 1;
    private static final int magicNumber2 = 4;
    private static final int magicNumber3 = 1;
    private static final int magicNumber4 = 1;

    private static final  int playTimes = 1;


    public AchieveDreamAwakening() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2,magicNumber3,magicNumber4,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AchieveDreamAwakening();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (card.cardID.equals(JustAppeal.ID)) {
            if (this.counter > 0) {
                if (PlayerHelper.getPowerAmount(AbstractDungeon.player, TempSavePower.POWER_ID) > 0) {
                    addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    this.flash();
                    addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID2));
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FullPowerValue(AbstractDungeon.player, magicNumber2), magicNumber2));
                    addToBot(new GrowAction(AttackTimeGrow.growID, GrowAction.GrowType.allTempSave,1));
                    addToBot(new GrowAction(EnergyGrow.growID, GrowAction.GrowType.allTempSave,1));
                    this.counter--;
                    if (this.counter == 0) {
                        this.grayscale = true;
                    }
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
