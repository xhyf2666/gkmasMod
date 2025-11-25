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
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.cardAction.TheGeniusAction;
import gkmasmod.cards.logic.Sunbathing;
import gkmasmod.powers.BeyondSunSongPower;
import gkmasmod.powers.BeyondSunSongPower2;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.HalfDamageReceive;
import gkmasmod.utils.PlayerHelper;

public class DeliverFeeling extends CustomRelic {

    private static final String CLASSNAME = DeliverFeeling.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 1;
    private static final int magicNumber2 = 30;

    public DeliverFeeling() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber,magicNumber2);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DeliverFeeling();
    }


    public void onEquip() {
        this.counter = 0;
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void atTurnStartPostDraw() {
        this.counter++;
        if(isFibonacci(this.counter)){
            this.flash();
            addToBot(new LoseHPAction(AbstractDungeon.player,AbstractDungeon.player,magicNumber));
            addToBot(new TheGeniusAction(1));
            int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, GoodTune.POWER_ID);
            count = (int) (1.0F*count*magicNumber2/100);
            if(count>0){
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new StrengthPower(AbstractDungeon.player, count), count));
            }
        }
    }

    //判断是斐波那契数列
    public boolean isFibonacci(int n) {
        if (n <= 1) return false;
        int a = 1;
        int b = 2;
        while (b < n) {
            int temp = b;
            b = a + b;
            a = temp;
        }
        return b == n;
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
