package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.relicAction.WantToBecomeIdolAction;
import gkmasmod.cardGrowEffect.DamageGrow;
import gkmasmod.cardGrowEffect.LoseHPGrow;
import gkmasmod.cards.anomaly.DecorateMagic;
import gkmasmod.cards.logic.FlyAgain;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.GrowHelper;

public class TakoyakiTechDog extends CustomRelic {

    private static final String CLASSNAME = TakoyakiTechDog.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 3;
    private static final int magicNumber2 = 2;
    private static final int magicNumber3 = 2;

    private static final  int playTimes = 3;

    public TakoyakiTechDog() {
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
        return new TakoyakiTechDog();
    }

    @Override
    public void onTrigger() {
        if (this.counter > 0) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.flash();
            boolean flag = false;
            for (int i = AbstractDungeon.player.exhaustPile.group.size() - 1; i >= 0; i--) {
                AbstractCard card = AbstractDungeon.player.exhaustPile.group.get(i);
                if (card instanceof DecorateMagic) {
                    GrowHelper.grow(card, DamageGrow.growID, magicNumber);
                    ((DecorateMagic) card).growValue(magicNumber2);
                    GrowHelper.grow(card, LoseHPGrow.growID, magicNumber3);
                    card.unhover();
                    card.fadingOut = false;
                    flag = true;
                }
            }
            if(flag){
                this.counter--;
                if (this.counter == 0) {
                    this.grayscale = true;
                }
            }
        }

    }

    public void onEquip() {
        this.counter = playTimes;
        this.grayscale = false;
    }

    public void atBattleStart() {
        this.counter = playTimes;
        this.grayscale = false;
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
