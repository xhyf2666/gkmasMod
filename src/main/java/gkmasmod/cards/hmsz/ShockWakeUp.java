package gkmasmod.cards.hmsz;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.patches.AbstractCardPatch;
import gkmasmod.stances.SleepStance;
import gkmasmod.stances.WakeStance;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

public class ShockWakeUp extends GkmasCard {
    private static final String CLASSNAME = ShockWakeUp.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;
    private static final int BASE_HP = 3;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorMisuzu;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ShockWakeUp() {
        super(ID, NAME, ImageHelper.getCardImgPath(CLASSNAME,TYPE), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        AbstractCardPatch.isDreamField.isDream.set(this, true);
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, this.HPMagicNumber));
        addToBot(new ChangeStanceAction(WakeStance.STANCE_ID));
        if(this.upgraded)
            addToBot(new GainTrainRoundPowerAction(p, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShockWakeUp();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.tags.add(GkmasCardTag.MORE_ACTION_TAG);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
