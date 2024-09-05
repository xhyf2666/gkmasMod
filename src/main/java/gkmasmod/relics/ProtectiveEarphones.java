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
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.powers.GoodImpression;

public class ProtectiveEarphones extends CustomRelic {

    private static final String CLASSNAME = ProtectiveEarphones.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 1;
    private static final int magicNumber2 = 1;

    private static final int STRENGTH = 4;

    private static final  int playTimes = 1;


    public ProtectiveEarphones() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],STRENGTH,magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ProtectiveEarphones();
    }


    public void onEquip() {}

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (this.counter > 0) {
            int count = AbstractDungeon.player.getPower(StrengthPower.POWER_ID)==null?0:AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            if(count>STRENGTH){
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, magicNumber), magicNumber));
                addToBot(new GainEnergyAction(magicNumber2));
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
