package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.utils.ThreeSizeHelper;

public class EveryoneTextbook extends CustomRelic {

    private static final String CLASSNAME = EveryoneTextbook.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.SPECIAL;

    private static final int magicNumber = 15;

    public EveryoneTextbook() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }


    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber,magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EveryoneTextbook();
    }


    public void onEquip() {}

    public void onEnterRoom(AbstractRoom room){
        if (room instanceof com.megacrit.cardcrawl.rooms.EventRoom) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.flash();
            ThreeSizeHelper.addFixedThreeSize(true,new int[]{magicNumber,magicNumber,magicNumber});
        }

    }

    public  void  onPlayerEndTurn(){
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
