package gkmasmod.downfall.cards.free;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.utils.NameHelper;

public class ENSkipWater extends GkmasBossCard {
    private static final String CLASSNAME = ENSkipWater.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENSkipWater.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 1;

    private static final int BLOCK_AMT = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    private static final int BASE_HP = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENSkipWater() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseBlock = BLOCK_AMT;
        this.tags.add(GkmasCardTag.MORE_ACTION_TAG);
        this.energyGeneratedIfPlayed = 1;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(m, m, this.HPMagicNumber));
        addToBot(new GainBlockAction(m, m, this.block));
        addToBot(new GainTrainRoundPowerAction(m,  1));
    }





    @Override
    public AbstractCard makeCopy() {
        return new ENSkipWater();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
