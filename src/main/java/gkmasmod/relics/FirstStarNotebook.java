package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.PlayerHelper;

public class FirstStarNotebook extends CustomRelic {

    private static final String CLASSNAME = FirstStarNotebook.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    private static final int magicNumber = 1;

    private static final int REQUIRE = 3;

    public FirstStarNotebook() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],REQUIRE,magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FirstStarNotebook();
    }


    public void onEquip() {}

    public void atTurnStart() {
        int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, StrengthPower.POWER_ID);
        if(count>REQUIRE){
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, magicNumber), magicNumber));
            addToBot(new GainTrainRoundPowerAction(AbstractDungeon.player,1));
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
