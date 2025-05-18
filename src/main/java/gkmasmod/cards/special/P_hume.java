package gkmasmod.cards.special;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.CardHelper;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class P_hume extends GkmasCard {
    private static final String CLASSNAME = P_hume.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_BLOCK = 20;
    private static final int UPGRADE_PLUS_BLOCK = 8;

    private static final int BASE_MAGIC = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public P_hume() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BASE_BLOCK;
        this.magicNumber = this.baseMagicNumber;
        this.backGroundColor = IdolData.empty;
        updateBackgroundImg();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard.CardColor> colorList = new ArrayList<>();
        colorList.add(PlayerColorEnum.gkmasModColorLogic);
        colorList.add(PlayerColorEnum.gkmasModColorSense);
        colorList.add(PlayerColorEnum.gkmasModColorAnomaly);
        ArrayList<AbstractCard> cards = CardHelper.getAllIdolCards(colorList,"",IdolData.hume);
        for (AbstractCard card : cards) {
            if (card.cost > 0) {
                card.cost = 0;
                card.costForTurn = 0;
                card.isCostModified = true;
            }
            addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
        }
    }

    @Override
    public void onChoseThisOption() {
        ArrayList<AbstractCard.CardColor> colorList = new ArrayList<>();
        colorList.add(PlayerColorEnum.gkmasModColorLogic);
        colorList.add(PlayerColorEnum.gkmasModColorSense);
        colorList.add(PlayerColorEnum.gkmasModColorAnomaly);
        ArrayList<AbstractCard> cards = CardHelper.getAllIdolCards(colorList,"",IdolData.hume);
        for (AbstractCard card : cards) {
            if (card.cost > 0) {
                card.cost = 0;
                card.costForTurn = 0;
                card.isCostModified = true;
            }
            addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new P_hume();
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
