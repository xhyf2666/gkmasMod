package gkmasmod.downfall.cards.logic;

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
import gkmasmod.cards.logic.LetMeBeYourDream;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.free.ENSleepLate;
import gkmasmod.powers.EvenIfDreamNotRealizePower;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class ENEvenIfDreamNotRealize extends GkmasBossCard {
    private static final String CLASSNAME = ENEvenIfDreamNotRealize.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENEvenIfDreamNotRealize.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 2;
    private static final int BASE_BLOCK = 4;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    private String flavor = "";

    public ENEvenIfDreamNotRealize() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseBlock = BASE_BLOCK;
        this.cardsToPreview = new LetMeBeYourDream();
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(0, 190, 216));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.intent = AbstractMonster.Intent.BUFF;
        this.backGroundColor = IdolData.shro;
        updateBackgroundImg();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded){
            addToBot(new GainBlockAction(m,m,this.block));
        }
        addToBot(new ApplyPowerAction(m,m,new EvenIfDreamNotRealizePower(m)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENEvenIfDreamNotRealize();
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
