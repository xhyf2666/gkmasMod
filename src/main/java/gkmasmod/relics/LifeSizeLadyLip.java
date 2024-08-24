package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class LifeSizeLadyLip extends CustomRelic {

    private static final String CLASSNAME = LifeSizeLadyLip.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("img/relics/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int TURNS1 = 4;
    private static final int TURNS2 = 5;

    private static final int magicNumber = 5;

    public LifeSizeLadyLip() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }



    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],TURNS1,TURNS2,magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LifeSizeLadyLip();
    }


    public void onEquip() {}

    public void atBattleStart() {
        this.counter = 0;
    }

    public void atTurnStart() {
        this.counter++;
        if (this.counter == TURNS1 || this.counter == TURNS2){
            flash();
            beginLongPulse();
        }

    }

    public void onPlayerEndTurn() {

        int amount = AbstractDungeon.player.currentBlock;
        System.out.println("Amount: " + amount);
        int damage = magicNumber;

        if (this.counter == TURNS1 || this.counter == TURNS2) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            stopPulse();
            if (this.counter == TURNS2)
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

}
