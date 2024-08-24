package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class SidewalkResearchNotes extends CustomRelic {

    private static final String CLASSNAME = SidewalkResearchNotes.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("img/relics/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int TURNS1 = 4;
    private static final int TURNS2 = 5;

    private static final int BASE_MAGIC = 50;

    private float magicNumber;

    private static final int HP_LOST = 1;

    public SidewalkResearchNotes() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
        magicNumber = 1.0F*BASE_MAGIC /100;
    }



    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],TURNS1,TURNS2,HP_LOST,BASE_MAGIC);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SidewalkResearchNotes();
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
        int damage = (int) (1.0F*amount*magicNumber);

        if (this.counter == TURNS1 || this.counter == TURNS2) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP_LOST));
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
