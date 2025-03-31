package gkmasmod.downfall.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import gkmasmod.powers.NotGoodTune;
import gkmasmod.relics.FirstVoiceProofKotone;
import gkmasmod.relics.FoodCarrot;

public class CBR_FoodCarrot extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_FoodCarrot.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_FoodCarrot.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    private static final int magicNumber = 3;

    public CBR_FoodCarrot() {
        super(new FoodCarrot(),IMG);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_FoodCarrot();
    }

    public void onEquip() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner,this.owner,new NotGoodTune(this.owner,magicNumber),magicNumber));
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
