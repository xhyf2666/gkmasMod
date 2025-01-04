package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainBlockWithPowerAction;

public class BeyondTheSea extends CustomRelic {

    private static final String CLASSNAME = BeyondTheSea.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 30;

    private static final int YARUKI = 2;

    private static final  int playTimes = 3;

    public BeyondTheSea() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],YARUKI,magicNumber,1,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BeyondTheSea();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void atTurnStart() {
        if (this.counter > 0) {
            int count = AbstractDungeon.player.getPower(DexterityPower.POWER_ID)==null?0:AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
            if(count>YARUKI){
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
                int add = (int) (1.0F*count*magicNumber/100);
                if(add>0){
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, add), add));
                }
                addToBot(new DrawCardAction(1));
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
