package gkmasmod.cards.free;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.RebirthPower;
import gkmasmod.powers.SaySomethingToYouPower;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class NecessaryContrast extends GkmasCard {
    private static final String CLASSNAME = NecessaryContrast.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    private static final int MAGIC1 = 50;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public NecessaryContrast() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseMagicNumber = MAGIC1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
        this.backGroundColor = IdolData.shro;
        updateBackgroundImg();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = p.currentHealth * this.magicNumber / 100;
        addToBot(new LoseHPAction(p, p, amount));
        if (amount ==0)
            amount = 1;
        addToBot(new ApplyPowerAction(p, p, new RebirthPower(p, amount)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new NecessaryContrast();
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
