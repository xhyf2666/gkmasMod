package gkmasmod.cards.hmsz;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.AutumnSleepyPower;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.SleepyCardPowerPower;
import gkmasmod.stances.SleepStance;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

public class SleepyCardPower extends GkmasCard {
    private static final String CLASSNAME = SleepyCardPower.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;
    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_MAGIC_PLUS = 1;

    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorMisuzu;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public SleepyCardPower() {
        super(ID, NAME, ImageHelper.getCardImgPath(CLASSNAME,TYPE), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(GkmasCardTag.SLEEP_TAG);
        this.tags.add(GkmasCardTag.FOCUS_TAG);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m!=null){
            addToBot(new ApplyPowerAction(m,m,new SleepyCardPowerPower(m,this.magicNumber),this.magicNumber));
        }
        addToBot(new ApplyPowerAction(p,p,new SleepyCardPowerPower(p,this.magicNumber),this.magicNumber));
        if(p.stance.ID.equals(SleepStance.STANCE_ID)){
            addToBot(new GainTrainRoundPowerAction(p,1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SleepyCardPower();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_PLUS);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
