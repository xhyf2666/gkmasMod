package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import gkmasmod.actions.EnjoyThreeColorGrowAction;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.cards.logic.GiveYou;
import gkmasmod.powers.EndOfTurnPreservationStancePlusPower;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.utils.PlayerHelper;

public class RoundSpring extends CustomRelic {

    private static final String CLASSNAME = RoundSpring.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 0;
    private static final int magicNumber2 = 5;

    private static final  int playTimes = 2;


    public RoundSpring() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RoundSpring();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    @Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        if(newStance.ID.equals(FullPowerStance.STANCE_ID)) {
            int count = 0;
            if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(ConcentrationStance.STANCE_ID)) {
                count += (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(ConcentrationStance.STANCE_ID)).intValue();
            }
            if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(ConcentrationStance.STANCE_ID2)) {
                count += (AbstractDungeon.actionManager.uniqueStancesThisCombat.get(ConcentrationStance.STANCE_ID2)).intValue();
            }
            if (this.counter > 0 && count>0) {
                this.counter--;
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new EnjoyThreeColorGrowAction(magicNumber2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EndOfTurnPreservationStancePlusPower(AbstractDungeon.player, 1), 1));
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
