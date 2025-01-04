package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.PlasticUmbrellaThatDayAction;
import gkmasmod.cards.anomaly.BeyondTheCrossing;
import gkmasmod.patches.GameActionManagerPatch;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.powers.TempSavePower;

public class PlasticUmbrellaThatDay extends CustomRelic {

    private static final String CLASSNAME = PlasticUmbrellaThatDay.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 8;

    private static final int FULL_POWER_VALUE = 4;

    private static final  int playTimes = 1;

    public PlasticUmbrellaThatDay() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }

    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],FULL_POWER_VALUE,magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PlasticUmbrellaThatDay();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void atTurnStartPostDraw() {
    }

    @Override
    public void onTrigger() {
        if (this.counter > 0) {
            int count = GameActionManagerPatch.FullPowerValueThisCombatField.fullPowerValueThisCombat.get();
            if(count>FULL_POWER_VALUE){
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FullPowerValue(AbstractDungeon.player, magicNumber), magicNumber));
                addToBot(new PlasticUmbrellaThatDayAction());
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
