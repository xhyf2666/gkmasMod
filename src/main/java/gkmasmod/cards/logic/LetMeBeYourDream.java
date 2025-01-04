package gkmasmod.cards.logic;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cardCustomEffect.MoreActionCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class LetMeBeYourDream extends GkmasCard {
    private static final String CLASSNAME = LetMeBeYourDream.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;
    private static final int BASE_BLOCK = 4;
    private static final int UPGRADE_BASE_BLOCK = 2;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    private String flavor = "";

    public LetMeBeYourDream() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseBlock = BASE_BLOCK;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(0, 190, 216));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.backGroundColor = IdolData.shro;
        updateBackgroundImg();
        CardModifierManager.addModifier(this,new MoreActionCustom(1));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,p,this.block));
//        addToBot(new GainTrainRoundPowerAction(p,1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LetMeBeYourDream();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BASE_BLOCK);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
