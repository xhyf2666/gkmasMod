package gkmasmod.downfall.relics;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.FrogFanAction;
import gkmasmod.relics.FrogFan;

public class CBR_FrogFan extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_FrogFan.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_FrogFan.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int HP_COST = 2;
    private static final int BASE_MAGIC = 220;

    private static final int YARUKI = 9;
    private float magicNumber;
    private static int playTimes = 4;

    public CBR_FrogFan() {
        super(new FrogFan(),IMG);
        magicNumber = 1.0F*BASE_MAGIC /100;
    }


    @Override
    public void onVictory() {
        this.counter = playTimes;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],YARUKI,HP_COST,BASE_MAGIC,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_FrogFan();
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (card.type == AbstractCard.CardType.ATTACK &&this.counter > 0) {
            AbstractCreature target = useCardAction.target;
            if(target==null) {
                target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            }
            addToBot(new FrogFanAction(AbstractCharBoss.boss,target,YARUKI,magicNumber,HP_COST,this));
//            int amount = AbstractCharBoss.boss.getPower(DexterityPower.POWER_ID)==null?0:AbstractCharBoss.boss.getPower(DexterityPower.POWER_ID).amount;
//
//            if (card.type == AbstractCard.CardType.ATTACK && amount>YARUKI) {
//                int damage = (int)(1.0F*amount*magicNumber);
////                AbstractCreature target = useCardAction.target;
//                if(target==null) {
//                    target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
//                }
//                addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
//                addToBot(new LoseHPAction(AbstractCharBoss.boss, AbstractCharBoss.boss, HP_COST));
//                addToBot(new ModfifyDamageAction(target, new DamageInfo(AbstractCharBoss.boss, damage, useCardAction.damageType), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
//                this.counter--;
//                if (this.counter == 0) {
//                    this.grayscale = true;
//                }
//            }
        }

    }

    public void atBattleStart() {
        this.counter = playTimes;
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
