package gkmasmod.downfall.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.CBR_MyPartAction;
import gkmasmod.actions.MyPartGrowAction;
import gkmasmod.cards.anomaly.Starlight;
import gkmasmod.downfall.cards.anomaly.ENStarlight;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.downfall.charbosses.stances.ENPreservationStance;
import gkmasmod.growEffect.AttackTimeGrow;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.powers.HalfDamageReceive;
import gkmasmod.relics.BeginnerGuideForEveryone;
import gkmasmod.relics.MyPart;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.GrowHelper;

public class CBR_MyPart extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_MyPart.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_MyPart.class.getSimpleName().substring(4);

    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;
    private static final int magicNumber2 = 1;
    private static final int magicNumber3 = 4;
    private static final int HP_LOSS = 2;

    private static final  int playTimes = 3;


    public CBR_MyPart() {
        super(new MyPart(),IMG);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP_LOSS,magicNumber,magicNumber2,magicNumber3,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MyPart();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (card.cardID.equals(ENStarlight.ID)&& AbstractCharBoss.boss.stance.ID.equals(ENPreservationStance.STANCE_ID)) {
            if (this.counter > 0) {
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                addToBot(new LoseHPAction(AbstractCharBoss.boss, AbstractCharBoss.boss, HP_LOSS));
//                addToBot(new DrawCardAction(magicNumber));
                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new HalfDamageReceive(AbstractCharBoss.boss, magicNumber2), magicNumber2));
                addToBot(new CBR_MyPartAction(true));
                this.counter--;
                if (this.counter == 0) {
                    this.grayscale = true;
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
