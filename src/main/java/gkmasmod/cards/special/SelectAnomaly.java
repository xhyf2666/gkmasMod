package gkmasmod.cards.special;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.TrainRoundAnomalyFirstAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.TrainRoundAnomalyPower;
import gkmasmod.powers.TrainRoundProducePower;
import gkmasmod.powers.TrainRoundSensePower;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class SelectAnomaly extends GkmasCard {
    private static final String CLASSNAME = SelectAnomaly.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_BLOCK = 20;
    private static final int UPGRADE_PLUS_BLOCK = 8;

    private static final int BASE_MAGIC = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SelectAnomaly() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BASE_BLOCK;
        this.magicNumber = this.baseMagicNumber;
        this.backGroundColor = IdolData.empty;
        updateBackgroundImg();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, TrainRoundProducePower.POWER_ID);
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new TrainRoundAnomalyPower(AbstractDungeon.player, count), count));
        addToBot(new TrainRoundAnomalyFirstAction());
    }

    @Override
    public void onChoseThisOption() {
        int count = PlayerHelper.getPowerAmount(AbstractDungeon.player, TrainRoundProducePower.POWER_ID);
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new TrainRoundAnomalyPower(AbstractDungeon.player, count), count));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SelectAnomaly();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
