package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.downfall.cards.sense.ENFullAdrenaline;
import gkmasmod.powers.GoodTune;
import gkmasmod.relics.UltimateSourceOfHappiness;

public class CBR_UltimateSourceOfHappiness extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_UltimateSourceOfHappiness.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_UltimateSourceOfHappiness.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;
    private static final int magicNumber2 = 5;


    public CBR_UltimateSourceOfHappiness() {
        super(new UltimateSourceOfHappiness(),IMG);
    }




    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_UltimateSourceOfHappiness();
    }


    public void onEquip() {}

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (card.cardID.equals(ENFullAdrenaline.ID)) {
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new GoodTune(AbstractCharBoss.boss, magicNumber), magicNumber));
                addToBot(new GainBlockWithPowerAction(AbstractCharBoss.boss,AbstractCharBoss.boss,magicNumber2));
        }
    }

    public void atBattleStart() {

    }

    public  void  onPlayerEndTurn(){
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
