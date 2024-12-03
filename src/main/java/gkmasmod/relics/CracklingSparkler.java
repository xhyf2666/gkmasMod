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

public class CracklingSparkler extends CustomRelic {

    private static final String CLASSNAME = CracklingSparkler.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;

    private static final int HP_LOST = 1;

    private static final int TURN = 3;

    private static final int GOOD_IMPRESSION = 5;

    private static final  int playTimes = 4;

    public CracklingSparkler() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],TURN,GOOD_IMPRESSION,HP_LOST,magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CracklingSparkler();
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
            int count = AbstractDungeon.player.getPower(GoodImpression.POWER_ID)==null?0:AbstractDungeon.player.getPower(GoodImpression.POWER_ID).amount;
            if(count>GOOD_IMPRESSION){
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
                addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP_LOST));
                addToBot(new GainBlockWithPowerAction(AbstractDungeon.player, AbstractDungeon.player, count));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, magicNumber), magicNumber));
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
