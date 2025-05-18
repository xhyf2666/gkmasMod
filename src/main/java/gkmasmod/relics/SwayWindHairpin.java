package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.AbsoluteNewSelfAction;
import gkmasmod.actions.GrowAction;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.GrowHelper;

public class SwayWindHairpin extends CustomRelic {

    private static final String CLASSNAME = SwayWindHairpin.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP = 1;
    private static final int MAGIC = 1;
    private static int playTimes = 3;

    public SwayWindHairpin() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP,MAGIC,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SwayWindHairpin();
    }


    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (card.type == AbstractCard.CardType.ATTACK &&this.counter > 0) {
            this.flash();
            this.counter--;
            addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
            addToBot(new GrowAction(DamageGrow.growID, GrowAction.GrowType.all,MAGIC));
            GrowHelper.grow(card,DamageGrow.growID,MAGIC);
            if(this.counter==0){
                this.grayscale = true;
            }
        }
    }


    public void atBattleStart() {
        this.counter = playTimes;
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
