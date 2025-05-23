package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.CheerfulHandkerchiefAction;

public class CheerfulHandkerchief extends CustomRelic {

    private static final String CLASSNAME = CheerfulHandkerchief.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP_COST = 2;
    private static final int BASE_MAGIC = 60;
    private float magicNumber;
    private static int playTimes = 2;

    public CheerfulHandkerchief() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
        magicNumber = 1.0F*BASE_MAGIC /100;
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP_COST,BASE_MAGIC,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CheerfulHandkerchief();
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (this.counter > 0) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                AbstractCreature target = useCardAction.target;
                if(target==null) {
                    target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                }
                addToBot(new CheerfulHandkerchiefAction(AbstractDungeon.player,target,magicNumber,HP_COST));
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
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
