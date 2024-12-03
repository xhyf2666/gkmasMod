package gkmasmod.downfall.cards.sense;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.free.ENSleepLate;
import gkmasmod.powers.GoodTune;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class ENBalance extends GkmasBossCard {
    private static final String CLASSNAME = ENBalance.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENBalance.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 1;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENBalance() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(GkmasCardTag.GOOD_TUNE_TAG);
        this.tags.add(GkmasCardTag.FOCUS_TAG);
        this.intent = AbstractMonster.Intent.MAGIC;
        this.isEthereal = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count_goodTune = PlayerHelper.getPowerAmount(m, GoodTune.POWER_ID);
        int count_strength = PlayerHelper.getPowerAmount(m, StrengthPower.POWER_ID);
        int count = (count_goodTune + count_strength+1)/2;
        if(count<=0)
            return;
        if(m.hasPower(GoodTune.POWER_ID))
            addToBot(new ApplyPowerAction(m, m, new GoodTune(m, count-count_goodTune), count-count_goodTune));
        else{
            addToBot(new ApplyPowerAction(m, m, new GoodTune(m, count), count));
        }
        if(m.hasPower(StrengthPower.POWER_ID))
            addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, count-count_strength), count-count_strength));
        else{
            addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, count), count));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return  new ENBalance();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = false;
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
