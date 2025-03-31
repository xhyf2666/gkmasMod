package gkmasmod.cards.special;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import gkmasmod.cards.GkmasCard;
import gkmasmod.powers.NotGoodTune;
import gkmasmod.utils.NameHelper;

public class BentoForProducer extends GkmasCard {
    private static final String CLASSNAME = BentoForProducer.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 2;

    private static final CardType TYPE = CardType.STATUS;
    private static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public BentoForProducer() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.isEthereal = true;
//        this.cardHeader = CardCrawlGame.languagePack.getUIString("gkmasMod:SleepyHeader").TEXT[0];
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerOnEndOfPlayerTurn() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NotGoodTune(AbstractDungeon.player, this.magicNumber), this.magicNumber));
        if (this.isEthereal) {
            this.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new BentoForProducer();
    }

    @Override
    public void upgrade() {
    }
}
