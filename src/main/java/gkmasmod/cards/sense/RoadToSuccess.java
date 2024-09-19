package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodTune;
import gkmasmod.screen.SkinSelectScreen;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class RoadToSuccess extends GkmasCard {
    private static final String CLASSNAME = RoadToSuccess.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = String.format("gkmasModResource/img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 7;
    private static final int UPGRADE_PLUS_MAGIC2 = 2;
    private static final int BLOCK_AMT = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public RoadToSuccess() {
        super(ID, NAME, String.format("gkmasModResource/img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = String.format("gkmasModResource/img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseBlock = BLOCK_AMT;
        this.block = this.baseBlock;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.GOOD_TUNE_TAG);
        this.tags.add(GkmasCardTag.FOCUS_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new GoodTune(p, -this.magicNumber), -this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.secondMagicNumber), this.secondMagicNumber));
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p,GoodTune.POWER_ID);
        if (count >= this.magicNumber)
            return true;
        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("gkmasMod:NotEnoughGoodTune").TEXT[0];
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new RoadToSuccess();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
