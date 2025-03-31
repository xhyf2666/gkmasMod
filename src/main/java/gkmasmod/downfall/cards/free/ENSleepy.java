package gkmasmod.downfall.cards.free;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.powers.NotGoodTune;
import gkmasmod.utils.NameHelper;

public class ENSleepy extends GkmasBossCard {
    private static final String CLASSNAME = ENSleepy.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENSleepy.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 0;
    private static final int BASE_MAGIC = 2;

    private static final CardType TYPE = CardType.STATUS;
    private static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENSleepy() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.isEthereal = true;
        this.cardHeader = CardCrawlGame.languagePack.getUIString("gkmasMod:SleepyHeader").TEXT[0];
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerOnEndOfPlayerTurn() {
        addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new NotGoodTune(AbstractCharBoss.boss, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new FrailPower(AbstractCharBoss.boss, this.magicNumber,false), this.magicNumber));

        if (this.isEthereal) {
            this.addToTop(new ExhaustSpecificCardAction(this, AbstractCharBoss.boss.hand));
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new ENSleepy();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
