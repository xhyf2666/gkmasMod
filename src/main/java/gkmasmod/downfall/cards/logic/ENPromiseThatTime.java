package gkmasmod.downfall.cards.logic;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.BlockDamageAction;
import gkmasmod.actions.DexterityPowerDamageAction;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.powers.GoodImpression;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class ENPromiseThatTime extends GkmasBossCard {
    private static final String CLASSNAME = ENPromiseThatTime.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENPromiseThatTime.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);

    private static final int COST = 2;
    private static final int BASE_MAGIC = 4;
    private static final int BASE_MAGIC2 = 100;
    private static final int UPGRADE_PLUS_MAGIC2 = 30;
    private static final int BASE_MAGIC3 = 150;
    private static final int UPGRADE_PLUS_MAGIC3 = 50;

    private static final int BLOCK_AMT = 3;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private String flavor = "";

    public ENPromiseThatTime() {
        super(ID, NAME, ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);
        this.updateShowImg = true;
        updateImg();
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.baseBlock = BLOCK_AMT;
        this.block = this.baseBlock;
        this.intent = AbstractMonster.Intent.ATTACK_DEFEND;
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, CardHelper.getColor(73, 224, 254));
        flavor = FlavorText.CardStringsFlavorField.flavor.get(CARD_STRINGS);
        this.tags.add(GkmasCardTag.YARUKI_TAG);
        this.tags.add(GkmasCardTag.GOOD_IMPRESSION_TAG);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(m, GoodImpression.POWER_ID);
        if (count >= this.magicNumber)
            return true;
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughGoodImpression").TEXT[0];
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, m, new GoodImpression(m, -this.magicNumber), -this.magicNumber));
        addToBot(new BlockDamageAction(1.0F * secondMagicNumber / 100, this.block, m, p,this));
        addToBot(new DexterityPowerDamageAction(this.thirdMagicNumber*1.0F/100, 0,m, p, this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENPromiseThatTime();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            upgradeThirdMagicNumber(UPGRADE_PLUS_MAGIC3);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
