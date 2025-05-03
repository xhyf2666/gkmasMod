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
import gkmasmod.actions.FirstStarChallengeAction;
import gkmasmod.actions.FrogFanAction;

public class FirstStarChallenge extends CustomRelic {

    private static final String CLASSNAME = FirstStarChallenge.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int MAGIC = 2;
    private static final int MAGIC2 = 1;

    private static final int REQUIRE = 5;
    private static int playTimes = 3;

    public FirstStarChallenge() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],REQUIRE,MAGIC,MAGIC2,playTimes);
    }

    @Override
    public void onEquip() {
        this.counter = playTimes;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FirstStarChallenge();
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (this.counter > 0) {
            addToBot(new FirstStarChallengeAction(AbstractDungeon.player,REQUIRE,MAGIC,MAGIC2,this));
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
