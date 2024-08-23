package gkmasmod.relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;

public class HeartFlutteringCup extends CustomRelic {

    private static final String CLASSNAME = HeartFlutteringCup.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("img/relics/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static final int TURNS = 7;

    private static int magicNumber = 3;
    private static int magicNumber2 = 3;

    private static final int HP_LOST = 2;
    private static int cardCount = 0;
    private PowerTip tip = new PowerTip();

    public HeartFlutteringCup() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
        tip.header="@STSLIB:FLAVOR@";
        tip.body=String.format("当前可造成%d伤害",cardCount*magicNumber2+magicNumber);
        this.tips.add(tip);
        FlavorText.PowerTipFlavorFields.boxColor.set(tip, CardHelper.getColor(73, 224, 254));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0],TURNS,HP_LOST,magicNumber,magicNumber2,cardCount*magicNumber2+magicNumber);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HeartFlutteringCup();
    }


    public void onEquip() {}

    public void atBattleStart() {
        this.counter = 0;
        cardCount = 0;
        tip.body=String.format("当前可造成%d伤害",cardCount*magicNumber2+magicNumber);
    }

    public void atTurnStart() {
        this.counter++;
        if (this.counter == TURNS){
            flash();
            beginLongPulse();
        }

    }

    public void onPlayerEndTurn() {

        int damage = cardCount * magicNumber2 + magicNumber;

        if (this.counter == TURNS) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP_LOST));
            addToBot(new DamageRandomEnemyAction(new DamageInfo(null, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            //addToBot((AbstractGameAction)new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            stopPulse();
            this.grayscale = true;
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if(this.counter <= TURNS){
            cardCount++;
            tip.body=String.format("当前可造成%d伤害",cardCount*magicNumber2+magicNumber);
        }

    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public void onVictory() {
        this.counter = -1;
        cardCount = 0;
        stopPulse();
    }

}
