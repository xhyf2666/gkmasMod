package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class SecretTrainingCardigan extends CustomRelic {

    private static final String CLASSNAME = SecretTrainingCardigan.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("img/relics/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 3;

    private static final  int playTimes = 2;

    private static boolean isRefresh = true;

    public SecretTrainingCardigan() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SecretTrainingCardigan();
    }


    public void onEquip() {}

    public void atTurnStart() {
    }

    public void onDexterityPowerIncrease(){
        if (this.counter > 0 && isRefresh) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, magicNumber), magicNumber));
            this.counter--;
            isRefresh = false;
            if (this.counter == 0) {
                this.grayscale = true;
            }
        }
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        isRefresh = true;
    }

    public void atBattleStart() {
        this.counter = playTimes;
        isRefresh = true;
    }

    public  void  onPlayerEndTurn(){
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }
}
