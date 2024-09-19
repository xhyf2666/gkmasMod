package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class UltimateSleepMask extends CustomRelic {

    private static final String CLASSNAME = UltimateSleepMask.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int BASE_MAGIC = 50;

    private float magicNumber;

    private int playTimes = 2;

    private static final int HP_LOST = 1;

    public UltimateSleepMask() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
        magicNumber = 1.0F*BASE_MAGIC /100;
    }



    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP_LOST,BASE_MAGIC);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new UltimateSleepMask();
    }


    public void onEquip() {}

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    public void onTrainRoundRemove() {

        int amount = AbstractDungeon.player.currentBlock;
        int damage = (int) (1.0F*amount*magicNumber);

        if (this.counter < playTimes) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP_LOST));
            addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            this.counter++;
            this.grayscale = true;
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
