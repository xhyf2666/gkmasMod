package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.DestinyEncounterAction;
import gkmasmod.actions.GainBlockWithPowerAction;
import gkmasmod.actions.GrowAction;
import gkmasmod.growEffect.DamageGrow;
import gkmasmod.powers.EndOfTurnPreservationStancePower;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import sun.security.krb5.internal.crypto.Des;

public class DestinyEncounter extends CustomRelic {

    private static final String CLASSNAME = DestinyEncounter.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 7;
    private static final int magicNumber2 = 3;
    private static final int magicNumber3 = 4;
    private static int playTimes = 1;


    public DestinyEncounter() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }

    @Override
    public void onVictory() {
        this.counter = playTimes;
    }



    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2,magicNumber3,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DestinyEncounter();
    }


    public void onEquip() {
        this.counter = playTimes;
    }

    public void atTurnStartPostDraw() {
        if (this.counter > 0) {
            addToBot(new DestinyEncounterAction(AbstractDungeon.player,magicNumber,magicNumber2,magicNumber3,this));
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
