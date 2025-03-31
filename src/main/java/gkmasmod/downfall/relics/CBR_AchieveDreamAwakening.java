package gkmasmod.downfall.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GrowAction;
import gkmasmod.cards.anomaly.JustAppeal;
import gkmasmod.downfall.cards.anomaly.ENJustAppeal;
import gkmasmod.downfall.charbosses.actions.unique.EnemyChangeStanceAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.downfall.charbosses.stances.ENPreservationStance;
import gkmasmod.growEffect.AttackTimeGrow;
import gkmasmod.growEffect.EnergyGrow;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.powers.TempSavePower;
import gkmasmod.relics.AchieveDreamAwakening;
import gkmasmod.relics.BeginnerGuideForEveryone;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.PlayerHelper;

public class CBR_AchieveDreamAwakening extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_AchieveDreamAwakening.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_AchieveDreamAwakening.class.getSimpleName().substring(4);

    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 1;
    private static final int magicNumber2 = 4;
    private static final int magicNumber3 = 1;
    private static final int magicNumber4 = 1;

    private static final  int playTimes = 1;


    public CBR_AchieveDreamAwakening() {
        super(new AchieveDreamAwakening(),IMG);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2,magicNumber3,magicNumber4,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_AchieveDreamAwakening();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (card.cardID.equals(ENJustAppeal.ID)) {
            if (this.counter > 0) {
                if (PlayerHelper.getPowerAmount(AbstractCharBoss.boss, TempSavePower.POWER_ID) > 0) {
                    addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                    this.flash();
                    addToBot(new EnemyChangeStanceAction(ENPreservationStance.STANCE_ID2));
                    addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new FullPowerValue(AbstractCharBoss.boss, magicNumber2), magicNumber2));
                    addToBot(new GrowAction(AttackTimeGrow.growID, GrowAction.GrowType.allTempSave,1,true));
                    addToBot(new GrowAction(EnergyGrow.growID, GrowAction.GrowType.allTempSave,1,true));
                    this.counter--;
                    if (this.counter == 0) {
                        this.grayscale = true;
                    }
                }
            }

        }

    }

    public void atBattleStart() {
        this.grayscale = false;
        this.counter = playTimes;
    }

    public  void  onPlayerEndTurn(){
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
