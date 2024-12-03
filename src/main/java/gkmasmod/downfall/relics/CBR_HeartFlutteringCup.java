package gkmasmod.downfall.relics;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import gkmasmod.downfall.charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.relics.FirstHeartProofChina;
import gkmasmod.relics.HeartFlutteringCup;

public class CBR_HeartFlutteringCup extends AbstractCharbossRelic {

    private static final String CLASSNAME = CBR_HeartFlutteringCup.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String CLASSNAME2 = CBR_HeartFlutteringCup.class.getSimpleName().substring(4);
    public static final String ID2 = CLASSNAME2;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME2);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME2);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static int magicNumber = 3;
    private static int magicNumber2 = 3;

    private static int playTimes = 2;

    private int playCounter = 0;

    private static final int HP_LOST = 1;

    public CBR_HeartFlutteringCup() {
        super(new HeartFlutteringCup(),IMG);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],HP_LOST,magicNumber,magicNumber2,playTimes);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FirstHeartProofChina();
    }


    public void onEquip() {}

    public void atBattleStart() {
        this.counter = 0;
        this.playCounter = 0;
    }

    public void onTrainRoundRemove() {

        int damage = counter * magicNumber2 + magicNumber;
        if(this.playCounter < playTimes){
            addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
            this.flash();
            addToBot(new LoseHPAction(AbstractCharBoss.boss, AbstractCharBoss.boss, HP_LOST));
            addToTop(new ModifyDamageAction(AbstractDungeon.player,new DamageInfo(AbstractCharBoss.boss, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            if(this.playCounter==playTimes){
                this.grayscale = true;
            }
            this.playCounter++;
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if(this.grayscale == false){
            counter++;
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
