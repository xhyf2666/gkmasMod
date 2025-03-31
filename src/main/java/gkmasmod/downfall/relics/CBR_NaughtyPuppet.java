package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.powers.IdolExamPower;
import gkmasmod.relics.NaughtyPuppet;
import gkmasmod.utils.PlayerHelper;

public class CBR_NaughtyPuppet extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_NaughtyPuppet.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_NaughtyPuppet.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP = 50;
    private static final int BASE_MAGIC = 100;
    private static final int BASE_MAGIC2 = 6;
    private static final int BASE_MAGIC3 = 2;
    private static int playTimes = 2;

    private int hp_lost=0;


    public CBR_NaughtyPuppet() {
        super(new NaughtyPuppet(),IMG);
    }

    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP,BASE_MAGIC,BASE_MAGIC2,BASE_MAGIC3,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_NaughtyPuppet();
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (this.counter > 0) {
            float amount = 1.0F* AbstractCharBoss.boss.currentHealth / AbstractCharBoss.boss.maxHealth;
            float HP_ = HP*1.0F/100;
            if (card.type == AbstractCard.CardType.ATTACK && amount<=HP_) {
                int damage = (int)(1.0F*this.hp_lost*BASE_MAGIC/100);
                AbstractCreature target = AbstractDungeon.player;
                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.flash();
                addToBot(new ModifyDamageAction(target, new DamageInfo(AbstractCharBoss.boss, damage, useCardAction.damageType), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                addToBot(new HealAction(AbstractCharBoss.boss, AbstractCharBoss.boss, BASE_MAGIC2));
                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new NoBlockPower(AbstractCharBoss.boss, BASE_MAGIC3,false), BASE_MAGIC3));
                this.counter--;
                if (this.counter == 0) {
                    this.grayscale = true;
                }
            }
        }
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if(AbstractCharBoss.boss!=null)
        {
            int count = PlayerHelper.getPowerAmount(AbstractCharBoss.boss,IdolExamPower.POWER_ID);
            if(count==0)
                return damageAmount;
        }
        int tmp = damageAmount;
        if(tmp>AbstractCharBoss.boss.currentHealth)
            tmp = AbstractCharBoss.boss.currentHealth;
        this.hp_lost += tmp;
        return damageAmount;
    }

    public void atBattleStart() {
        this.counter = playTimes;
        this.hp_lost = 0;
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
