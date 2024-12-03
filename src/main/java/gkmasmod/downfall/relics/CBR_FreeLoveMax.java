package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.relics.FreeLoveMax;

public class CBR_FreeLoveMax extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_FreeLoveMax.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_FreeLoveMax.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 5;

    private static final int YARUKI = 4;

    private static final  int playTimes = 2;

    public CBR_FreeLoveMax() {
        super(new FreeLoveMax(),IMG);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],YARUKI,magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_FreeLoveMax();
    }


    public void onEquip() {}

    public void atTurnStart() {
        if (this.counter > 0) {
            int count = AbstractCharBoss.boss.getPower(DexterityPower.POWER_ID)==null?0: AbstractCharBoss.boss.getPower(DexterityPower.POWER_ID).amount;
            if(count>YARUKI){
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                addToBot(new GainBlockWithPowerAction(AbstractCharBoss.boss,this.magicNumber));
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
