package gkmasmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gkmasmod.actions.ModifyDamageRandomEnemyAction;
import gkmasmod.cards.free.BaseAppeal;

public class HeartFlutteringCup extends CustomRelic {

    private static final String CLASSNAME = HeartFlutteringCup.class.getSimpleName();

    public static final String ID = CLASSNAME;

    private static final String IMG = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_OTL = String.format("gkmasModResource/img/relics/%s.png",CLASSNAME);
    private static final String IMG_LARGE = String.format("gkmasModResource/img/relics/large/%s.png",CLASSNAME);

    private static final RelicTier RARITY = RelicTier.STARTER;

    private static int magicNumber = 3;
    private static int magicNumber2 = 3;

    private static int playTimes = 2;

    private int playCounter = 0;

    private static final int HP_LOST = 1;
    private PowerTip tip = new PowerTip();

    public HeartFlutteringCup() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RARITY, LandingSound.CLINK);
        tip.header="@STSLIB:FLAVOR@";
        tip.body=String.format("当前可造成%d伤害",counter*magicNumber2+magicNumber);
        this.tips.add(tip);
        FlavorText.PowerTipFlavorFields.boxColor.set(tip, CardHelper.getColor(73, 224, 254));
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
        tip.body=String.format("当前可造成%d伤害",counter*magicNumber2+magicNumber);
    }

    public void onTrainRoundRemove() {

        int damage = counter * magicNumber2 + magicNumber;
        if(this.playCounter < playTimes){
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP_LOST));
            addToTop(new ModifyDamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            if(this.playCounter==playTimes){
                this.grayscale = true;
            }
            this.playCounter++;
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if(this.grayscale == false){
            counter++;
            tip.body=String.format("当前可造成%d伤害",counter*magicNumber2+magicNumber);
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
