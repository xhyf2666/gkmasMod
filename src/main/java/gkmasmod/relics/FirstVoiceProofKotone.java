package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.cards.sense.FirstReward;
import gkmasmod.powers.GoodTune;

public class FirstVoiceProofKotone extends CustomRelic {

    private static final String CLASSNAME = FirstVoiceProofKotone.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("img/relics/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;


    public FirstVoiceProofKotone() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.MAGICAL);
    }




    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FirstVoiceProofKotone();
    }


    public void onEquip() {}

    public void onUseCard(AbstractCard card, UseCardAction useCardAction){
        if (card.cardID.equals(FirstReward.ID)) {
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodTune(AbstractDungeon.player, magicNumber), magicNumber));
        }

    }

    public void atBattleStart() {

    }

    public  void  onPlayerEndTurn(){
    }

}
