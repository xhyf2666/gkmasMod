package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import gkmasmod.cards.logic.TeaChat;
import gkmasmod.cards.logic.Yeah;
import gkmasmod.powers.AnotherTurnPower;

public class SelectedPassion extends CustomRelic {

    private static final String CLASSNAME = SelectedPassion.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 15;
    private static final int magicNumber2 = 2;

    private static final  int playTimes = 5;


    public SelectedPassion() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SelectedPassion();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (card.cardID.equals(Yeah.ID)) {
            float amount = 1.0F * AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth;
            if (amount >= 0.5F) {
                if (this.counter > 0) {
                    addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    this.flash();
                    addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, (int) (AbstractDungeon.player.maxHealth * 1.0F * magicNumber / 100)));
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, magicNumber2), magicNumber2));
                    addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player, 1));
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
