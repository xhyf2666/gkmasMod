package gkmasmod.cards.hmsz;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.SleepySongPower;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

public class SleepySong extends GkmasCard {
    private static final String CLASSNAME = SleepySong.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static final int BASE_MAGIC = 1;


    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorMisuzu;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SleepySong() {
        super(ID, NAME, ImageHelper.getCardImgPath(CLASSNAME,TYPE), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new SleepySongPower(p,this.magicNumber),this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SleepySong();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
