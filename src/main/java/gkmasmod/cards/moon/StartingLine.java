package gkmasmod.cards.moon;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cardCustomEffect.MoreActionCustom;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.powers.GoodImpression;
import gkmasmod.powers.GoodTune;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class StartingLine extends GkmasCard {
    private static final String CLASSNAME = StartingLine.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;

    private static final int BASE_HP = 10;

    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 4;
    private static final int BASE_MAGIC3 = 3;



    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorMoon;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public StartingLine() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.exhaust = true;
        this.isInnate = true;
        this.tags.add(GkmasCardTag.FULL_POWER_TAG);
        this.tags.add(GkmasCardTag.FOCUS_TAG);
        this.tags.add(GkmasCardTag.GOOD_IMPRESSION_TAG);
        this.tags.add(GkmasCardTag.GOOD_TUNE_TAG);
        this.tags.add(GkmasCardTag.YARUKI_TAG);
        this.tags.add(GkmasCardTag.PRESERVATION_TAG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, this.HPMagicNumber));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new GoodTune(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new GoodImpression(p, this.secondMagicNumber), this.secondMagicNumber));
        addToBot(new ApplyPowerAction(p, p, new FullPowerValue(p, this.thirdMagicNumber), this.thirdMagicNumber));
        addToBot(new ChangeStanceAction(PreservationStance.STANCE_ID));
        if(this.upgraded){
            addToBot(new GainTrainRoundPowerAction(p,1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new StartingLine();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.tags.add(GkmasCardTag.MORE_ACTION_TAG);
            CardModifierManager.addModifier(this,new MoreActionCustom(1));
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
