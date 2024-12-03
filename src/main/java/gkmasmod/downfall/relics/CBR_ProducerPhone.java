package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.powers.HalfDamageReceive;
import gkmasmod.relics.ProducerPhone;

public class CBR_ProducerPhone extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_ProducerPhone.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_ProducerPhone.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static int playTimes = 1;

    private static final String AUDIO = "gkmasModResource/audio/voice/phone/phone_%s_%03d.ogg";

    private boolean RclickStart = false;

    private boolean thisBattle = false;

    public CBR_ProducerPhone() {
        super(new ProducerPhone(),IMG);
        this.counter = 0;
    }

    @Override
    public void update() {
        super.update();
    }
    

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ProducerPhone();
    }


    public void onEquip() {}

    @Override
    public void atBattleStart() {
        addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        flash();
        float amount = 1.0F * AbstractCharBoss.boss.currentHealth / AbstractCharBoss.boss.maxHealth;
        if(amount >=0.5f){
            addToBot(new GainTrainRoundPowerAction(AbstractCharBoss.boss,1));
        }
        else{
            addToBot(new ApplyPowerAction(AbstractCharBoss.boss,AbstractCharBoss.boss,new HalfDamageReceive(AbstractCharBoss.boss,1),1));
        }
        this.counter++;
        this.thisBattle = false;
    }

    @Override
    public void atPreBattle() {
    }

    public  void  onPlayerEndTurn(){
    }

    public void onVictory() {
        this.thisBattle = false;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
