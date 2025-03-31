package gkmasmod.downfall.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.GreatGoodTune;
import gkmasmod.relics.FirstVoiceProofKotone;
import gkmasmod.relics.FoodTonkatsu;

public class CBR_FoodTonkatsu extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_FoodTonkatsu.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_FoodTonkatsu.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    private static final int magicNumber = 3;
    private static final int magicNumber2 = 1;

    public CBR_FoodTonkatsu() {
        super(new FoodTonkatsu(),IMG);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_FoodTonkatsu();
    }

    public void onEquip() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner,this.owner,new GoodTune(this.owner,magicNumber),magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner,this.owner,new GreatGoodTune(this.owner,magicNumber2),magicNumber2));

    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
