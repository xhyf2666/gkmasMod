package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.powers.GoodTune;
import gkmasmod.relics.RoaringLion;

public class CBR_RoaringLion extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_RoaringLion.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_RoaringLion.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int magicNumber = 6;

    private static final int damage = magicNumber;

    private static final int GOOD_TUNE = 0;

//    private static final  int playTimes = 1;

    public CBR_RoaringLion() {
        super(new RoaringLion(),IMG);
    }


//    @Override
//    public void onVictory() {
//        this.counter = playTimes;
//    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_RoaringLion();
    }


    public void onEquip() {}

    public void atTurnStart() {
//        if (this.counter > 0) {
//            int count = AbstractCharBoss.boss.getPower(GoodTune.POWER_ID)==null?0:AbstractCharBoss.boss.getPower(GoodTune.POWER_ID).amount;
//            if(count>GOOD_TUNE){
//                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
//                this.flash();
//                addToBot(new ModifyDamageAction(AbstractDungeon.player,new DamageInfo(AbstractCharBoss.boss, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
//                this.counter--;
//                if (this.counter == 0) {
//                    this.grayscale = true;
//                }
//            }
//        }
        int count = AbstractCharBoss.boss.getPower(GoodTune.POWER_ID)==null?0:AbstractCharBoss.boss.getPower(GoodTune.POWER_ID).amount;
        if(count>GOOD_TUNE){
            addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
            this.flash();
            addToBot(new ModifyDamageAction(AbstractDungeon.player,new DamageInfo(AbstractCharBoss.boss, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

//    public void atBattleStart() {
//        this.counter = playTimes;
//    }

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
