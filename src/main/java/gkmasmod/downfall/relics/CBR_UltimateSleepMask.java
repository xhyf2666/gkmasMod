package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.relics.UltimateSleepMask;

public class CBR_UltimateSleepMask extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_UltimateSleepMask.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_UltimateSleepMask.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int BASE_MAGIC = 50;

    private float magicNumber;

    private static int playTimes = 2;

    private static final int HP_LOST = 1;

    public CBR_UltimateSleepMask() {
        super(new UltimateSleepMask(),IMG);
        magicNumber = 1.0F*BASE_MAGIC /100;
    }



    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP_LOST,BASE_MAGIC,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_UltimateSleepMask();
    }


    public void onEquip() {}

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    public void onTrainRoundRemove() {

        int amount = AbstractCharBoss.boss.currentBlock;
        int damage = (int) (1.0F*amount*magicNumber);

        if (this.counter < playTimes) {
            addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
            this.flash();
            addToBot(new LoseHPAction(AbstractCharBoss.boss, AbstractCharBoss.boss, HP_LOST));
            addToBot(new ModifyDamageAction(AbstractDungeon.player,new DamageInfo(AbstractCharBoss.boss, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            this.counter++;
            if(this.counter==playTimes){
                this.grayscale = true;
            }

        }
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
