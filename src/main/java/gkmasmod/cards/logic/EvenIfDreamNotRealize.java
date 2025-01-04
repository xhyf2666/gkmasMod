package gkmasmod.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.EvenIfDreamNotRealizePower;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class EvenIfDreamNotRealize extends GkmasCard {
    private static final String CLASSNAME = EvenIfDreamNotRealize.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;
    private static final int BASE_BLOCK = 4;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    private String flavor = "";

    public EvenIfDreamNotRealize() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseBlock = BASE_BLOCK;
        this.cardsToPreview = new LetMeBeYourDream();
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(0, 190, 216));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.backGroundColor = IdolData.shro;
        updateBackgroundImg();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded){
            addToBot(new GainBlockAction(p,p,this.block));
        }
        addToBot(new ApplyPowerAction(p,p,new EvenIfDreamNotRealizePower(p)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new EvenIfDreamNotRealize();
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
