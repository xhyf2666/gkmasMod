package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.powers.GoodTune;

public class UltimateSourceOfHappiness extends CustomRelic {

    private static final String CLASSNAME = UltimateSourceOfHappiness.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("img/relics/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 3;
    private static final int magicNumber2 = 5;


    public UltimateSourceOfHappiness() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.MAGICAL);
    }




    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new UltimateSourceOfHappiness();
    }


    public void onEquip() {}

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        // TODO 换成专属卡
        if (card.cardID.equals("gkmasmod:KotoneVoice")) {
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodTune(AbstractDungeon.player, magicNumber), magicNumber));
                addToBot(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player,magicNumber2));
        }

    }

    public void atBattleStart() {

    }

    public  void  onPlayerEndTurn(){
    }

}
