package gkmasmod.downfall.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.free.ENSleepLate;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.SummerEveningSparklersPower;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;

public class ENSummerEveningSparklers extends GkmasBossCard {
    private static final String CLASSNAME = ENSummerEveningSparklers.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENSummerEveningSparklers.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 0;

    private static final int BASE_MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private static final int BASE_MAGIC2 = 2;
    private static final int BASE_MAGIC3 = 1;

    private static final int BASE_HP = 5;

    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENSummerEveningSparklers() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.isInnate = true;
        this.backGroundColor = IdolData.fktn;
        updateBackgroundImg();
        this.intent = AbstractMonster.Intent.BUFF;
        this.tags.add(GkmasCardTag.YARUKI_TAG);
        this.tags.add(GkmasCardTag.GOOD_IMPRESSION_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(m, m, this.HPMagicNumber));
        addToBot(new ApplyPowerAction(m, m, new GoodImpression(m, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(m, m, new DexterityPower(m, this.secondMagicNumber), this.secondMagicNumber));
        addToBot(new ApplyPowerAction(m, m, new SummerEveningSparklersPower(m, this.thirdMagicNumber), this.thirdMagicNumber));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_fktn_3_002_produce_skillcard_01.ogg");

    }

    @Override
    public AbstractCard makeCopy() {
        return new ENSummerEveningSparklers();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
