package gkmasmod.cards.othe;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.SelectCardGrowAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.cardGrowEffect.MoreActionGrow;
import gkmasmod.utils.NameHelper;

public class AsYourFan extends GkmasCard {
    private static final String CLASSNAME = AsYourFan.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;

    private static final int BASE_MAGIC = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorOther;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AsYourFan() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardGrowAction(1, MoreActionGrow.growID));
    }

    @Override
    public AbstractCard makeCopy() {
        return new AsYourFan();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
