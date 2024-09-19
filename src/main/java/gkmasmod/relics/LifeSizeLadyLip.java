package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.cards.free.BasePerform;

public class LifeSizeLadyLip extends CustomRelic {

    private static final String CLASSNAME = LifeSizeLadyLip.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private int playTimes = 3;

    private static final int magicNumber = 5;

    public LifeSizeLadyLip() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }



    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LifeSizeLadyLip();
    }


    public void onEquip() {}

    public void atBattleStart() {
        this.counter = 0;
    }


    public void onTrainRoundRemove() {

        int damage = magicNumber;

        if (this.counter < playTimes) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            this.counter++;
            if (this.counter == playTimes-1)
                this.grayscale = true;
        }
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public void onVictory() {
        this.counter = -1;
        stopPulse();
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
