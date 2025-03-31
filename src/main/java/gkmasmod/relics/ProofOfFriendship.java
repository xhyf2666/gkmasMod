package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gkmasmod.powers.GoodImpression;

public class ProofOfFriendship extends CustomRelic {

    private static final String CLASSNAME = ProofOfFriendship.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP = 2;
    private static final int magicNumber = 30;
    private static int playTimes = 3;

    private boolean flag = true;

    public ProofOfFriendship() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP,magicNumber,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ProofOfFriendship();
    }

    @Override
    public void onEquip() {
        this.counter = playTimes;
    }

    @Override
    public void atBattleStart() {
        flag = true;
        this.counter = playTimes;
    }

    @Override
    public int onPlayerGainedBlock(float blockAmount) {
        if(this.counter > 0&&flag) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP));
            int count = (int) ((AbstractDungeon.player.currentBlock+blockAmount)*magicNumber/100);
            if(count>0){
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GoodImpression(AbstractDungeon.player, count), count));
            }
            flag = false;
            this.counter--;
            if(this.counter == 0){
                this.grayscale = true;
            }
        }
        return super.onPlayerGainedBlock(blockAmount);
    }

    @Override
    public void atTurnStart() {
        flag = true;
    }

    public void loadLargeImg() {
        if (this.largeImg == null) {
            if (Gdx.files.internal(IMG_LARGE).exists()) {
                this.largeImg = ImageMaster.loadImage(IMG_LARGE);
            }
        }

    }
}
