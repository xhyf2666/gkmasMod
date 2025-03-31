package gkmasmod.downfall.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.AbsoluteNewSelfAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.relics.AbsoluteNewSelf;
import gkmasmod.relics.AfterSchoolDoodles;

public class CBR_AbsoluteNewSelf extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_AbsoluteNewSelf.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_AbsoluteNewSelf.class.getSimpleName().substring(4);

    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP = 4;
    private static final int BASE_DAMAGE = 9;
    private static int playTimes = 1;

    public CBR_AbsoluteNewSelf() {
        super(new AbsoluteNewSelf(),IMG);
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],BASE_DAMAGE,HP,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_AbsoluteNewSelf();
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (card.type == AbstractCard.CardType.ATTACK &&this.counter > 0) {
            addToBot(new AbsoluteNewSelfAction(AbstractCharBoss.boss,AbstractDungeon.player,BASE_DAMAGE,HP));
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
