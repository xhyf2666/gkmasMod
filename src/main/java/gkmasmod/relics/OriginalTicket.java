package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Bloodletting;
import com.megacrit.cardcrawl.cards.red.Hemokinesis;
import com.megacrit.cardcrawl.cards.red.Offering;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.powers.increaseModifyPower.AbstractIncreaseModifyPower;
import gkmasmod.powers.increaseModifyPower.EnthusiasticAddRatePower;

public class OriginalTicket extends CustomRelic {

    private static final String CLASSNAME = OriginalTicket.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 30;


    private static final  int playTimes = 3;

    public OriginalTicket() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OriginalTicket();
    }


    public void onEquip() {
        this.counter = 0;
        this.grayscale = false;
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
            if (this.counter < playTimes) {
                if (card.type == AbstractCard.CardType.SKILL && isCostHPCard(card)) {
                    counter++;
                    addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    this.flash();
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new EnthusiasticAddRatePower(AbstractDungeon.player, AbstractIncreaseModifyPower.MAGIC,magicNumber, ID),magicNumber));
                    if (counter == playTimes) {
                        grayscale = true;
                    }
                }
            }
    }

    private boolean isCostHPCard(AbstractCard card) {
        if(card.hasTag(GkmasCardTag.COST_HP_TAG)){
            return true;
        }
        if(card instanceof Bloodletting || card instanceof Offering || card instanceof Hemokinesis){
            return true;
        }
        return false;
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    public  void  onPlayerEndTurn(){
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.counter = 0;
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
