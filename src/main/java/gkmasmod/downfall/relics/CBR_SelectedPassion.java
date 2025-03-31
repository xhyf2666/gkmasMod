package gkmasmod.downfall.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.logic.Yeah;
import gkmasmod.downfall.cards.logic.ENYeah;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.relics.BeginnerGuideForEveryone;
import gkmasmod.relics.SelectedPassion;

public class CBR_SelectedPassion extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_SelectedPassion.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_SelectedPassion.class.getSimpleName().substring(4);

    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 15;
    private static final int magicNumber2 = 2;

    private static final  int playTimes = 5;


    public CBR_SelectedPassion() {
        super(new SelectedPassion(),IMG);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SelectedPassion();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (card.cardID.equals(ENYeah.ID)) {
            float amount = 1.0F * AbstractCharBoss.boss.currentHealth / AbstractCharBoss.boss.maxHealth;
            if (amount >= 0.5F) {
                if (this.counter > 0) {
                    addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                    this.flash();
                    addToBot(new LoseHPAction(AbstractCharBoss.boss, AbstractCharBoss.boss, (int) (AbstractCharBoss.boss.maxHealth * 1.0F * magicNumber / 100)));
                    addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new DexterityPower(AbstractCharBoss.boss, magicNumber2), magicNumber2));
                    addToBot(new GainTrainRoundPowerAction(AbstractCharBoss.boss, 1));
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
