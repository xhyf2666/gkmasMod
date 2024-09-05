package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;
import gkmasmod.ui.SkinSelectScreen;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class GirlHeart extends AbstractDefaultCard {
    private static final String CLASSNAME = GirlHeart.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 3;
    private static final int BASE_MAGIC2 = 4;
    private static final int UPGRADE_PLUS_MAGIC2 = 1;
    private static final int BASE_MAGIC3 = 9;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public GirlHeart() {
        super(ID, NAME, String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, GoodImpression.POWER_ID);
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -this.magicNumber), -this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, this.secondMagicNumber), this.secondMagicNumber));
        if(count > thirdMagicNumber){
            addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, 2), 2));
        }
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p, DexterityPower.POWER_ID);
        if (count >= this.magicNumber)
            return true;
        // TODO 本地化
        this.cantUseMessage = "敏捷还不够";
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new GirlHeart();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
