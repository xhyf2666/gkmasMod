package gkmasmod.downfall.relics;

import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.patches.AbstractPowerPatch;
import gkmasmod.powers.GoodImpression;
import gkmasmod.relics.DreamLifeLog;

public class CBR_DreamLifeLog extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_DreamLifeLog.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_DreamLifeLog.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int BLOCK = 11;

    private static final int BASE_MAGIC = 150;
    private static final int BASE_MAGIC2 = 30;

    private float magicNumber;
    private float magicNumber2;

    private static final int playTimes = 1;


    public CBR_DreamLifeLog() {
        super(new DreamLifeLog(),IMG);
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
        return new CBR_DreamLifeLog();
    }


    public void onEquip() {}

    public void atBattleStart() {
        this.counter = playTimes;
    }

    public void onPlayerEndTurn() {

        if (this.counter > 0) {
            int amount = AbstractCharBoss.boss.currentBlock;
            if(amount> BLOCK){
                int damage = (int) (1.0F*amount*magicNumber2);
                int goodImpressionAmount = AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID) ==null?0:AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID).amount;
                if (goodImpressionAmount > 0) {
                    int add = (int) (1.0F*goodImpressionAmount*(BASE_MAGIC-100)/100);
                    AbstractPower power = new GoodImpression(AbstractCharBoss.boss, add);
                    AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
                    addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, power, add));
                }
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                addToBot(new ModifyDamageAction(AbstractDungeon.player, new DamageInfo(AbstractCharBoss.boss, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
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
