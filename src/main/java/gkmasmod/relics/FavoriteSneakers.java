package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.powers.GoodImpression;

public class FavoriteSneakers extends CustomRelic {

    private static final String CLASSNAME = FavoriteSneakers.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 4;

    private static final int BLOCK = 6;

    public FavoriteSneakers() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],BLOCK,magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FavoriteSneakers();
    }


    public void onEquip() {}

    public void onPlayerEndTurn() {
        int amount = AbstractDungeon.player.currentBlock;
        if(amount>BLOCK){
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodImpression(AbstractDungeon.player, magicNumber), magicNumber));
        }

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
