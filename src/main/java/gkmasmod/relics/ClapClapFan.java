package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import gkmasmod.actions.GainBlockWithPowerAction;

public class ClapClapFan extends CustomRelic {

    private static final String CLASSNAME = ClapClapFan.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 1;
    private static final int magicNumber2 = 3;


    private static final  int playTimes = 2;

    private int currentTimes =0;

    public ClapClapFan() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],3,magicNumber,magicNumber2,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ClapClapFan();
    }


    public void onEquip() {}

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if(currentTimes < playTimes){
            counter = (counter + 1)%3;
            if(counter == 0){
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new GainBlockWithPowerAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, magicNumber2), magicNumber2));
                currentTimes++;
                if(currentTimes == playTimes){
                    grayscale = true;
                }
            }
        }
    }

    public void atBattleStart() {

    }

    public  void  onPlayerEndTurn(){
    }

    public void justEnteredRoom(AbstractRoom room) {
        currentTimes = 0;
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
