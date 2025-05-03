package gkmasmod.downfall.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.free.ENSleepLate;
import gkmasmod.patches.AbstractPowerPatch;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class ENWakeUp extends GkmasBossCard {
    private static final String CLASSNAME = ENWakeUp.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENWakeUp.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 150;
    private static final int UPGRADE_PLUS_MAGIC = 50;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENWakeUp() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.intent = AbstractMonster.Intent.MAGIC;
        this.cardHeader = CardCrawlGame.languagePack.getUIString("gkmasMod:WakeUpHeader").TEXT[0];
        this.tags.add(GkmasCardTag.GOOD_IMPRESSION_TAG);
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(m, GoodImpression.POWER_ID);
        if (count > 0) {
            int add = (int) (1.0F*count*(this.magicNumber-100)/100);
            AbstractPower power = new GoodImpression(m, add);
            AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
            addToBot(new ApplyPowerAction(m, m, power, add));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENWakeUp();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
