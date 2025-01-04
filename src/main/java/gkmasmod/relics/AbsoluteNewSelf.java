package gkmasmod.relics;
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
import gkmasmod.actions.TeaCaddyAction;

public class AbsoluteNewSelf extends CustomRelic {

    private static final String CLASSNAME = AbsoluteNewSelf.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP = 4;
    private static final int BASE_DAMAGE = 9;
    private static int playTimes = 1;

    public AbsoluteNewSelf() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
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
        return new AbsoluteNewSelf();
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (card.type == AbstractCard.CardType.ATTACK &&this.counter > 0) {
            AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            addToBot(new AbsoluteNewSelfAction(AbstractDungeon.player,target,BASE_DAMAGE,HP));
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
