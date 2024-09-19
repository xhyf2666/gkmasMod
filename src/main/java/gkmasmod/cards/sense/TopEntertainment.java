package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.*;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class TopEntertainment extends GkmasCard {
    private static final String CLASSNAME = TopEntertainment.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = String.format("gkmasModResource/img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 2;

    private static final int BASE_MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = -1;
    private static final int BASE_MAGIC2 = 4;
    private static final int UPGRADE_PLUS_MAGIC2 = 1;
    private static final int BASE_MAGIC3 = 1;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TopEntertainment() {
        super(ID, NAME, String.format("gkmasModResource/img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = String.format("gkmasModResource/img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.FOCUS_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -this.magicNumber), -this.magicNumber));
        if(this.upgraded){
            addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, this.thirdMagicNumber), this.thirdMagicNumber));
            addToBot(new ApplyPowerAction(p, p, new TopEntertainmentPlusPower(p, 1), 1));
        }
        else{
            addToBot(new ApplyPowerAction(p, p, new TopEntertainmentPower(p, 1), 1));
        }

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p,StrengthPower.POWER_ID);
        if (count >= magicNumber)
            return true;
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughStrengthPower").TEXT[0];
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new TopEntertainment();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
