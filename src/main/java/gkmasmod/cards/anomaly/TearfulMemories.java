package gkmasmod.cards.anomaly;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.growEffect.DrawCardGrow;
import gkmasmod.powers.FullPowerValue;
import gkmasmod.stances.ConcentrationStance;
import gkmasmod.stances.FullPowerStance;
import gkmasmod.stances.PreservationStance;
import gkmasmod.utils.GrowHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class TearfulMemories extends GkmasCard {
    private static final String CLASSNAME = TearfulMemories.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_MAGIC = 1;

    private static final int BASE_MAGIC2 = 1;

    private static final int BASE_MAGIC3 = 1;

    private static final int BASE_GROW = 3;
    private static final int UPGRADE_GROW_PLUS = 1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorAnomaly;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TearfulMemories() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseThirdMagicNumber = BASE_MAGIC3;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.baseGrowMagicNumber = BASE_GROW;
        this.growMagicNumber = this.baseGrowMagicNumber;
        this.tags.add(GkmasCardTag.FULL_POWER_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    public void switchedStance() {
        if(this.growMagicNumber > 0){
            if(AbstractDungeon.player.stance.ID.equals(FullPowerStance.STANCE_ID)){
                upgradeGrowMagicNumber(-1);
                this.initializeDescription();
                GrowHelper.grow(this, DrawCardGrow.growID,this.thirdMagicNumber);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TearfulMemories();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeGrowMagicNumber(UPGRADE_GROW_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
