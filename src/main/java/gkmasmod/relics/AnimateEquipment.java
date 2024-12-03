package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;

public class AnimateEquipment extends CustomRelic {

    private static final String CLASSNAME = AnimateEquipment.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 50;

    private static final int magicNumber2 = 1;

    private static final int BLOCK = 3;

    private static final int TURN = 2;

    private static final int GOOD_TONE = 0;

    private static final  int playTimes = 2;

    public AnimateEquipment() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],TURN,magicNumber,magicNumber2,BLOCK,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AnimateEquipment();
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
            int count = AbstractDungeon.player.getPower(GoodTune.POWER_ID)==null?0:AbstractDungeon.player.getPower(GoodTune.POWER_ID).amount;
            if(count>GOOD_TONE){
                int amount = (int) (1.0f*count*magicNumber/100);
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
                if(amount>0){
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, amount), amount));
                }
                addToBot(new GainBlockWithPowerAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, magicNumber2), magicNumber2));
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
