package gkmasmod.downfall.cards.logic;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.actions.BlockDamageAction;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class ENSparklingConfetti extends GkmasBossCard {
    private static final String CLASSNAME = ENSparklingConfetti.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENSparklingConfetti.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);

    private static final int COST = 0;
    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 100;
    private static final int BASE_MAGIC3 = 1;
    private static final int UPGRADE_PLUS_MAGIC3 = 1;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;


    public ENSparklingConfetti() {
        super(ID, NAME, ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);
        this.updateShowImg = true;
        this.updateImg();
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.intent = AbstractMonster.Intent.ATTACK;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.tags.add(GkmasCardTag.YARUKI_TAG);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(m, DexterityPower.POWER_ID);
        if (count >= this.magicNumber)
            return true;
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughGoodImpression").TEXT[0];
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, m, new DexterityPower(m, -this.magicNumber), -this.magicNumber));
        addToBot(new BlockDamageAction(1.0F * secondMagicNumber / 100, this.block, m, p,this));
//        addToBot(new ApplyPowerAction(m,m,new DrawCardNextTurnPower(p,this.thirdMagicNumber),this.thirdMagicNumber));
    }



    @Override
    public AbstractCard makeCopy() {
        return new ENSparklingConfetti();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeThirdMagicNumber(UPGRADE_PLUS_MAGIC3);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
