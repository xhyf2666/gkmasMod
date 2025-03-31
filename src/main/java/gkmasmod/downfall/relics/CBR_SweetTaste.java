package gkmasmod.downfall.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.downfall.charbosses.actions.unique.EnemyChangeStanceAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.downfall.charbosses.stances.ENConcentrationStance;
import gkmasmod.downfall.charbosses.stances.ENPreservationStance;
import gkmasmod.relics.BeginnerGuideForEveryone;
import gkmasmod.relics.SweetTaste;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.PreservationStance;

public class CBR_SweetTaste extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_SweetTaste.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_SweetTaste.class.getSimpleName().substring(4);

    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 3;

    public CBR_SweetTaste() {
        super(new SweetTaste(),IMG);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SweetTaste();
    }

    @Override
    public  void  onPlayerEndTurn(){
        if(AbstractCharBoss.boss.stance.ID.equals(ENConcentrationStance.STANCE_ID)){
            addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
            addToBot(new EnemyChangeStanceAction(ENPreservationStance.STANCE_ID));
            this.flash();
            addToBot(new GainBlockWithPowerAction(AbstractCharBoss.boss, magicNumber));
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
