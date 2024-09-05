package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GoodTune;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class Balance extends AbstractDefaultCard {
    private static final String CLASSNAME = Balance.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;
    private static final int UPGRADE_PLUS_COST = 1;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Balance() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count_goodTune = PlayerHelper.getPowerAmount(p, GoodTune.POWER_ID);
        int count_strength = PlayerHelper.getPowerAmount(p, StrengthPower.POWER_ID);
        if(count_goodTune > count_strength){
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, count_goodTune - count_strength), count_goodTune - count_strength));
        }
        else if(count_goodTune < count_strength){
            addToBot(new ApplyPowerAction(p, p, new GoodTune(p, count_strength - count_goodTune), count_strength - count_goodTune));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return  new Balance();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_PLUS_COST);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
