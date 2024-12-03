package gkmasmod.downfall.cards.sense;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.free.ENSleepLate;
import gkmasmod.powers.BurstAttackTwoRoundPower;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.HalfDamageReceive;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.SoundHelper;

public class ENCumulusCloudsAndYou extends GkmasBossCard {
    private static final String CLASSNAME = ENCumulusCloudsAndYou.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENCumulusCloudsAndYou.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 2;
    private static final int BASE_MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 2;
    private static final int BASE_MAGIC2 = 2;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENCumulusCloudsAndYou() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.exhaust = true;
        this.intent = AbstractMonster.Intent.BUFF;
        this.tags.add(GkmasCardTag.GOOD_TUNE_TAG);
        this.tags.add(GkmasCardTag.IDOL_CARD_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, m, new GoodTune(m, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(m, m, new HalfDamageReceive(m, this.secondMagicNumber), this.secondMagicNumber));
        addToBot(new ApplyPowerAction(m, m, new BurstAttackTwoRoundPower(m, 1), 1));
        SoundHelper.playSound("gkmasModResource/audio/voice/skillcard/cidol_hrnm_3_003_produce_skillcard_01.ogg");

    }

    @Override
    public AbstractCard makeCopy() {
        return new ENCumulusCloudsAndYou();
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
