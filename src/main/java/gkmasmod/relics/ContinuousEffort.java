package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.PlasticUmbrellaThatDayAction;
import gkmasmod.patches.GameActionManagerPatch;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.stances.ConcentrationStance;

public class ContinuousEffort extends CustomRelic {

    private static final String CLASSNAME = ContinuousEffort.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 4;

    private static final int BLOCK = 7;


    public ContinuousEffort() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,BLOCK);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ContinuousEffort();
    }


    public void onEquip() {}

    public void atTurnStartPostDraw() {
        int count = GameActionManagerPatch.FullPowerValueThisCombatField.fullPowerValueThisCombat.get();
        if(count>magicNumber){
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.flash();
            addToBot(new GainBlockWithPowerAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
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
