package gkmasmod.downfall.cards.sense;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cardCustomEffect.CostCustom;
import gkmasmod.cardCustomEffect.MagicCustom;
import gkmasmod.cardCustomEffect.SecondMagicCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.anomaly.ENFinalSpurt;
import gkmasmod.patches.AbstractPowerPatch;
import gkmasmod.powers.TheScenerySawSomedayPower;
import gkmasmod.utils.*;

import java.util.ArrayList;

public class ENTheScenerySawSomeday extends GkmasBossCard {
    private static final String CLASSNAME = ENTheScenerySawSomeday.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENTheScenerySawSomeday.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 2;
    private static final int BASE_MAGIC = 5;
    private static final int UPGRADE_PLUS_MAGIC = -1;
    private static final int BASE_MAGIC2 = 150;
    private static final int BASE_MAGIC3 = 3;
    private static final int BASE_DMG = 3;
    private static final int UPGRADE_PLUS_DMG = 2;

    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public ENTheScenerySawSomeday() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = BASE_DMG;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
        this.tags.add(GkmasCardTag.FOCUS_TAG);
        this.intent = AbstractMonster.Intent.BUFF;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = PlayerHelper.getPowerAmount(m, StrengthPower.POWER_ID);
        if(amount>0){
            amount = (int) (1.0F*amount*(this.secondMagicNumber-100)/100);
            AbstractPower power = new StrengthPower(m, amount);
            AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
            addToBot(new ApplyPowerAction(m, m, power, amount));
        }
        addToBot(new ApplyPowerAction(m,m,new TheScenerySawSomedayPower(m,this.baseDamage),this.baseDamage));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_kllj_3_010_produce_skillcard_01.ogg");
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(m, StrengthPower.POWER_ID);
        if (count > this.magicNumber)
            return super.canUse(p, m);
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughStrengthPower").TEXT[0];
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENTheScenerySawSomeday();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
