package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.powers.GoodImpression;

public class ThisIsMe extends CustomRelic {

    private static final String CLASSNAME = ThisIsMe.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("img/relics/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 3;

    private static final int GOOD_IMPRESSION = 9;

    private static final  int playTimes = 2;

    public ThisIsMe() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],GOOD_IMPRESSION,magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new ThisIsMe();
    }


    public void onEquip() {}

    public void onPlayerEndTurn() {
        if (this.counter > 0) {
            int count = AbstractDungeon.player.getPower(GoodImpression.POWER_ID)==null?0:AbstractDungeon.player.getPower(GoodImpression.POWER_ID).amount;
            if(count>GOOD_IMPRESSION){
                int damage = count;
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new GainBlockAction(AbstractDungeon.player, magicNumber));
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
}
