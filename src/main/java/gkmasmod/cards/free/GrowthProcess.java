package gkmasmod.cards.free;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.AngelAndDemonPlusPower;
import gkmasmod.powers.AngelAndDemonPower;
import gkmasmod.powers.GrowthProcessPower;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class GrowthProcess extends GkmasCard {
    private static final String CLASSNAME = GrowthProcess.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public GrowthProcess() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.cardsToPreview = new Normality();
        this.backGroundColor = IdolData.kllj;
        updateBackgroundImg();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded)
            addToBot(new MakeTempCardInDiscardAction(new Normality(), 1));
        else
            addToBot(new MakeTempCardInHandAction(new Normality(), 1));
        addToBot(new ApplyPowerAction(p,p,new GrowthProcessPower(p,1),1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new GrowthProcess();
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
