package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.actions.orb.EnemyChannelAction;
import gkmasmod.downfall.charbosses.orbs.EnemyDark;
import gkmasmod.downfall.charbosses.orbs.EnemyFrost;
import gkmasmod.downfall.charbosses.orbs.EnemyLightning;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.relics.CrackedCoreNew;

public class CBR_CrackedCoreNew extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_CrackedCoreNew.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_CrackedCoreNew.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.SPECIAL;


    public CBR_CrackedCoreNew() {
        super(new CrackedCoreNew(),IMG);    }


    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0]);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_CrackedCoreNew();
    }


    public void onEquip() {}

    public void atTurnStart() {
    }

    public void onStrengthPowerIncrease(){

    }

    @Override
    public void atBattleStart() {
        this.addToTop(new EnemyChannelAction(new EnemyLightning()));
        this.addToTop(new EnemyChannelAction(new EnemyFrost()));
        this.addToTop(new EnemyChannelAction(new EnemyDark()));
//        AbstractCharBoss.boss.channelOrb(new Lightning());
//        AbstractCharBoss.boss.channelOrb(new Frost());
//        AbstractCharBoss.boss.channelOrb(new Dark());
    }

    public void atPreBattle() {

    }

    public  void  onPlayerEndTurn(){
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
