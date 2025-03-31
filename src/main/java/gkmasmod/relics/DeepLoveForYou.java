package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.TheWingAction;
import gkmasmod.cards.logic.GiveYou;
import gkmasmod.cards.logic.Restart;
import gkmasmod.utils.PlayerHelper;

public class DeepLoveForYou extends CustomRelic {

    private static final String CLASSNAME = DeepLoveForYou.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 4;
    private static final int magicNumber2 = 220;
    private static final int block = 2;

    private static final  int playTimes = 2;


    public DeepLoveForYou() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,block,magicNumber2,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DeepLoveForYou();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (card.cardID.equals(GiveYou.ID)||card.type== AbstractCard.CardType.ATTACK) {
            if (this.counter > 0) {
                int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, DexterityPower.POWER_ID);
                if(count>magicNumber){
                    addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    this.flash();
                    addToBot(new GainBlockWithPowerAction(AbstractDungeon.player,AbstractDungeon.player,block,1.0F*magicNumber2/100));
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
