package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;

public class FirstVoiceProofSaki extends CustomRelic {

    private static final String CLASSNAME = FirstVoiceProofSaki.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("img/relics/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;

    private static final  int playTimes = 2;

    public FirstVoiceProofSaki() {
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
        return (AbstractRelic)new FirstVoiceProofSaki();
    }


    public void onEquip() {}

    public void onLoseHp(int damageAmount) {
        if (this.counter > 0) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodImpression(AbstractDungeon.player, magicNumber), magicNumber));
            this.counter--;
            if(this.counter==0)
                this.grayscale = true;
        }

    }

    public void atBattleStart() {
        this.counter = playTimes;
    }

    public  void  onPlayerEndTurn(){
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }
}
