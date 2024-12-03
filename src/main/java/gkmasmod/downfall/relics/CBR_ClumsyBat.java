package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.powers.GoodImpression;
import gkmasmod.relics.ClumsyBat;
import gkmasmod.utils.PlayerHelper;

public class CBR_ClumsyBat extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_ClumsyBat.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_ClumsyBat.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 2;
    private static final int magicNumber2 = 100;
    private static final int magicNumber3 = 230;

    private static final int TURN = 3;

    private static final int GOOD_IMPRESSION = 2;

    public CBR_ClumsyBat() {
        super(new ClumsyBat(),IMG);    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],TURN,GOOD_IMPRESSION,magicNumber2,magicNumber3,magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ClumsyBat();
    }

    public void onEquip() {
        this.counter = 0;
    }

    public void onPlayerEndTurn() {
        if (this.counter == -1) {
            this.counter += 2;
        } else {
            this.counter = (this.counter + 1)%TURN;
        }
        if (this.counter == 0) {
            int count = AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID)==null?0:AbstractCharBoss.boss.getPower(GoodImpression.POWER_ID).amount;
            if(count>GOOD_IMPRESSION){
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                int damage = (int) (1.0F*AbstractCharBoss.boss.currentBlock * magicNumber2/100);
                addToBot(new ModifyDamageAction(AbstractDungeon.player,new DamageInfo(AbstractCharBoss.boss, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                damage = (int) (1.0F* PlayerHelper.getPowerAmount(AbstractCharBoss.boss, DexterityPower.POWER_ID) * magicNumber3/100);
                addToBot(new ModifyDamageAction(AbstractDungeon.player,new DamageInfo(AbstractCharBoss.boss, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                addToBot(new ReducePowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, GoodImpression.POWER_ID, magicNumber));
            }
            else{
                this.counter = TURN -1;
            }
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
