package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.growEffect.BlockGrow;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.powers.TempSavePower;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.utils.GrowHelper;

public class SmallGalaxy extends CustomRelic {

    private static final String CLASSNAME = SmallGalaxy.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP = 1;
    private static final int magicNumber = 2;

    private static final  int playTimes = 3;


    public SmallGalaxy() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP,magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SmallGalaxy();
    }


    public void onEquip() {}

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (this.counter > 0 && card.type == AbstractCard.CardType.SKILL) {
            boolean flag= false;
            if(AbstractDungeon.player.stance.ID.equals(ConcentrationStance.STANCE_ID)){
                ConcentrationStance concentrationStance = (ConcentrationStance) AbstractDungeon.player.stance;
                if(concentrationStance.stage==1){
                    flag=true;
                }
            }
            if (flag) {
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
                addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP));
                GrowHelper.grow(card, BlockGrow.growID,magicNumber);
                for(AbstractCard c:AbstractDungeon.player.hand.group){
                    if(c.type== AbstractCard.CardType.SKILL){
                        GrowHelper.grow(c, BlockGrow.growID,magicNumber);
                    }
                }
                for(AbstractCard c:AbstractDungeon.player.drawPile.group){
                    if(c.type== AbstractCard.CardType.SKILL){
                        GrowHelper.grow(c, BlockGrow.growID,magicNumber);
                    }
                }
                for(AbstractCard c:AbstractDungeon.player.discardPile.group){
                    if(c.type== AbstractCard.CardType.SKILL){
                        GrowHelper.grow(c, BlockGrow.growID,magicNumber);
                    }
                }
                for(AbstractCard c:AbstractDungeon.player.exhaustPile.group) {
                    if(c.type== AbstractCard.CardType.SKILL){
                        GrowHelper.grow(c, BlockGrow.growID,magicNumber);
                    }
                }
                if(AbstractDungeon.player.hasPower(TempSavePower.POWER_ID)){
                    TempSavePower tempSavePower = (TempSavePower) AbstractDungeon.player.getPower(TempSavePower.POWER_ID);
                    for(AbstractCard c:tempSavePower.getCards()){
                        if(c.type== AbstractCard.CardType.SKILL){
                            GrowHelper.grow(c, BlockGrow.growID,magicNumber);
                        }
                    }
                }
                this.counter--;
                if (this.counter == 0) {
                    this.grayscale = true;
                }
            }
        }

    }

    public void atBattleStart() {
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
