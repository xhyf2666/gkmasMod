package gkmasmod.cards.hmsz;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import gkmasmod.actions.WakeUpTimeAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.NotGoodTune;
import gkmasmod.powers.WantToSleepEnemy;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class WakeUpTime extends GkmasCard {
    private static final String CLASSNAME = WakeUpTime.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = -1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorMisuzu;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public WakeUpTime() {
        super(ID, NAME, ImageHelper.getCardImgPath(CLASSNAME,TYPE), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(GkmasCardTag.SLEEP_TAG);
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
        this.cardsToPreview = new WantToRun();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new WakeUpTimeAction(p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new WakeUpTime();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
