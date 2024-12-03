package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.powers.GoodTune;
import gkmasmod.relics.ChristmasLion;
import gkmasmod.utils.PlayerHelper;

public class CBR_ChristmasLion extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_ChristmasLion.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_ChristmasLion.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private int counter = 0;

    private static int playTimes = 3;

    private static final int BASE_MAGIC = 50;

    private float magicNumber;

    public CBR_ChristmasLion() {
        super(new ChristmasLion(),IMG);
        magicNumber = 1.0F*BASE_MAGIC /100;
    }



    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],BASE_MAGIC,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ChristmasLion();
    }


    public void onEquip() {}

    public void atBattleStart() {
        this.counter = 0;
    }

    public void onTrainRoundRemove() {

        if (this.counter < playTimes) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.flash();
            int damage = (int) (1.0F* PlayerHelper.getPowerAmount(AbstractDungeon.player, GoodTune.POWER_ID) *magicNumber);
            if(damage>0)
                addToBot(new ModifyDamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            this.counter++;
            if (this.counter == playTimes)
                this.grayscale = true;
        }
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public void onVictory() {
        this.counter = -1;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
