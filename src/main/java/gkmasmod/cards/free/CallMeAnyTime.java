package gkmasmod.cards.free;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.AngelAndDemonPlusPower;
import gkmasmod.powers.AngelAndDemonPower;
import gkmasmod.powers.CallMeAnyTimePower;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class CallMeAnyTime extends GkmasCard {
    private static final String CLASSNAME = CallMeAnyTime.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int MAGIC = 1;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

//    private String flavor = "";

    public CallMeAnyTime() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.cardHeader = CardCrawlGame.languagePack.getUIString("gkmasMod:CallMeAnyTimeHeader").TEXT[0];
        this.backGroundColor = IdolData.ttmr;
        updateBackgroundImg();
//        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
//        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new CallMeAnyTimePower(p)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CallMeAnyTime();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeBaseCost(UP_COST);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
