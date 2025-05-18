package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.PlayerHelper;

public class LadyDiary extends CustomRelic {

    private static final String CLASSNAME = LadyDiary.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 3;

    public LadyDiary() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0]);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LadyDiary();
    }

    @Override
    public  void  onPlayerEndTurn(){
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.flash();

        int count1 = PlayerHelper.getPowerAmount(AbstractDungeon.player,StrengthPower.POWER_ID);
        int count2 = PlayerHelper.getPowerAmount(AbstractDungeon.player, DexterityPower.POWER_ID);
        if(count1==0&&count2==0){
            return;
        }
        if(count1==0){
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,DexterityPower.POWER_ID));
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,count2),count2));
            return;
        }
        if(count2==0){
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,StrengthPower.POWER_ID));
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,count1),count1));
            return;
        }
        AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = count2;
        AbstractDungeon.player.getPower(StrengthPower.POWER_ID).updateDescription();
        AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount = count1;
        AbstractDungeon.player.getPower(DexterityPower.POWER_ID).updateDescription();
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
