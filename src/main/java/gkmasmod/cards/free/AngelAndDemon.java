package gkmasmod.cards.free;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.AngelAndDemonPlusPower;
import gkmasmod.powers.AngelAndDemonPower;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class AngelAndDemon extends GkmasCard {
    private static final String CLASSNAME = AngelAndDemon.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AngelAndDemon() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.cardsToPreview = new JustAngel();
        this.cardPreviewList = new ArrayList<>(Arrays.asList(new JustAngel(), new JustDemon()));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new AngelAndDemonPlusPower(p)));
        }
        else{
            addToBot(new ApplyPowerAction(p,p,new AngelAndDemonPower(p)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AngelAndDemon();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            this.cardPreviewList.get(0).upgrade();
            this.cardPreviewList.get(1).upgrade();
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
