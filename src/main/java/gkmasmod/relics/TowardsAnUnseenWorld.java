package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.UpgradeAllHandCardAction;
import gkmasmod.powers.ReduceDamageReceive;

public class TowardsAnUnseenWorld extends CustomRelic {

    private static final String CLASSNAME = TowardsAnUnseenWorld.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP = 50;
    private static final int magicNumber = 5;
    private static final int magicNumber2 = 2;
    private static int playTimes = 1;

    public TowardsAnUnseenWorld() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP,magicNumber,magicNumber2,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TowardsAnUnseenWorld();
    }

    public void atTurnStartPostDraw() {
        if (this.counter > 0) {
            float amount = 1.0F*AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth;
            float HP_ = HP*1.0F/100;
            if(amount<=HP_){
                flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new UpgradeAllHandCardAction());
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, magicNumber), magicNumber));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ReduceDamageReceive(AbstractDungeon.player, magicNumber2), magicNumber2));
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
