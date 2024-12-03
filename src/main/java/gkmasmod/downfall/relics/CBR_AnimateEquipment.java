package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.powers.GoodTune;
import gkmasmod.relics.AnimateEquipment;
import gkmasmod.utils.PlayerHelper;

public class CBR_AnimateEquipment extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_AnimateEquipment.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_AnimateEquipment.class.getSimpleName().substring(4);

    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 50;

    private static final int magicNumber2 = 1;

    private static final int BLOCK = 3;

    private static final int TURN = 2;

    private static final int GOOD_TONE = 0;

    private static final  int playTimes = 2;

    public CBR_AnimateEquipment() {
        super(new AnimateEquipment(),IMG);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],TURN,magicNumber,magicNumber2,BLOCK,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_AnimateEquipment();
    }

    public void onEquip() {
        this.counter = 0;
    }

    public void atTurnStart() {
        if (this.counter == -1) {
            this.counter += 2;
        } else {
            this.counter = (this.counter + 1)%TURN;
        }
        if (this.counter == 0) {
            int count = PlayerHelper.getPowerAmount(AbstractCharBoss.boss,GoodTune.POWER_ID);
            if(count>GOOD_TONE){
                int amount = (int) (1.0f*count*magicNumber/100);
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                if(amount>0){
                    addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new StrengthPower(AbstractCharBoss.boss, amount), amount));
                }
                addToBot(new GainBlockWithPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, BLOCK));
                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new StrengthPower(AbstractCharBoss.boss, magicNumber2), magicNumber2));
            }
            else{
                this.counter = TURN -1;
            }
        }
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }

}
