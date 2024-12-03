package gkmasmod.downfall.cards.logic;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.actions.HardStretchingAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.utils.NameHelper;

public class ENHardStretching extends GkmasBossCard {
    private static final String CLASSNAME = ENHardStretching.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENHardStretching.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 1;
    private static final int BASE_MAGIC2 = 3;
    private static final int BASE_BLOCK = 1;
    private static final int UPGRADE_BLOCK_PLUS = 2;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;


    public ENHardStretching() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseBlock = BASE_BLOCK;
        this.intent = AbstractMonster.Intent.MAGIC;
        this.tags.add(GkmasCardTag.MORE_ACTION_TAG);

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new DrawCardAction(1, new HardStretchingAction(p,this.block)));
        addToBot(new GainTrainRoundPowerAction(m, this.magicNumber));
        if(this.secondMagicNumber > 1){
            upgradeSecondMagicNumber(-1);
            this.initializeDescription();
        }
        else{
            this.exhaust = true;
        }
    }



    @Override
    public AbstractCard makeCopy() {
        return  new ENHardStretching();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
