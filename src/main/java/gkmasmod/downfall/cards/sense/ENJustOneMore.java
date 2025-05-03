package gkmasmod.downfall.cards.sense;

import com.megacrit.cardcrawl.powers.AbstractPower;
import gkmasmod.actions.AnotherLimitBreakAction;
import gkmasmod.cardCustomEffect.EffectReduceCustom;
import gkmasmod.downfall.charbosses.actions.unique.EnemyLimitBreakAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.patches.AbstractPowerPatch;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.NotGoodTune;
import gkmasmod.utils.CustomHelper;
import gkmasmod.utils.NameHelper;

public class ENJustOneMore extends GkmasBossCard {
    private static final String CLASSNAME = ENJustOneMore.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENJustOneMore.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 1;

    private static int BASE_MAGIC = 2;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENJustOneMore() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.intent = AbstractMonster.Intent.BUFF;
        this.tags.add(GkmasCardTag.GOOD_TUNE_TAG);
        this.tags.add(GkmasCardTag.FOCUS_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = AbstractCharBoss.boss.getPower(GoodTune.POWER_ID) == null ? 0 : AbstractCharBoss.boss.getPower(GoodTune.POWER_ID).amount;
        AbstractPower power;
        if (count > 0) {
            addToBot(new RemoveSpecificPowerAction(m, m, GoodTune.POWER_ID));
            power = new StrengthPower(m, count);
            AbstractPowerPatch.IgnoreIncreaseModifyField.flag.set(power, true);
            addToBot(new ApplyPowerAction(m, m,power, count));
        } else if(!CustomHelper.hasCustom(this, EffectReduceCustom.growID)){
            addToBot(new ApplyPowerAction(m,  m,  new NotGoodTune(m, this.magicNumber), this.magicNumber));
        }
        addToBot(new AnotherLimitBreakAction(m));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENJustOneMore();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
