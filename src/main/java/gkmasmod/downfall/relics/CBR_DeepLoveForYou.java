package gkmasmod.downfall.relics;
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
import gkmasmod.cards.logic.GiveYou;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.relics.BeginnerGuideForEveryone;
import gkmasmod.relics.DeepLoveForYou;
import gkmasmod.utils.PlayerHelper;

public class CBR_DeepLoveForYou extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_DeepLoveForYou.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_DeepLoveForYou.class.getSimpleName().substring(4);

    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 4;
    private static final int magicNumber2 = 220;
    private static final int block = 2;

    private static final  int playTimes = 2;


    public CBR_DeepLoveForYou() {
        super(new DeepLoveForYou(),IMG);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,block,magicNumber2,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DeepLoveForYou();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (card.cardID.equals(GiveYou.ID)||card.type== AbstractCard.CardType.ATTACK) {
            //TODO
            if (this.counter > 0) {
                int count = PlayerHelper.getPowerAmount(AbstractCharBoss.boss, DexterityPower.POWER_ID);
                if(count>magicNumber){
                    addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                    this.flash();
                    addToBot(new GainBlockWithPowerAction(AbstractCharBoss.boss,AbstractCharBoss.boss,block,1.0F*magicNumber2/100));
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
