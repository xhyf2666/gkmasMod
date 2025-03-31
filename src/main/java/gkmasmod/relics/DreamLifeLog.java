package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.cards.free.BaseAppeal;
import gkmasmod.powers.GoodImpression;

public class DreamLifeLog extends CustomRelic {

    private static final String CLASSNAME = DreamLifeLog.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int BLOCK = 11;

    private static final int BASE_MAGIC = 50;
    private static final int BASE_MAGIC2 = 30;

    private float magicNumber;
    private float magicNumber2;

    private static final int playTimes = 1;

    public DreamLifeLog() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
        magicNumber = 1.0F*BASE_MAGIC /100;
        magicNumber2 = 1.0F*BASE_MAGIC2 /100;
    }

    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],BLOCK,BASE_MAGIC2,BASE_MAGIC,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DreamLifeLog();
    }


    public void onEquip() {}

    public void atBattleStart() {
        this.counter = playTimes;
    }

    public void onPlayerEndTurn() {

        if (this.counter > 0) {
            int amount = AbstractDungeon.player.currentBlock;
            if(amount> BLOCK){
                int damage = (int) (1.0F*amount*magicNumber2);
                int goodImpressionAmount = AbstractDungeon.player.getPower(GoodImpression.POWER_ID) ==null?0:AbstractDungeon.player.getPower(GoodImpression.POWER_ID).amount;
                int goodImpressionIncrease = (int) (1.0F*goodImpressionAmount*magicNumber);

                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.flash();
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodImpression(AbstractDungeon.player, goodImpressionIncrease), goodImpressionIncrease));
                addToBot(new ModifyDamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                this.counter--;
                if (this.counter == 0) {
                    this.grayscale = true;
                }
            }
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
