package gkmasmod.downfall.cards.free;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.powers.HalfDamageReceive;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

public class ENIdolDeclaration extends GkmasBossCard {
    private static final String CLASSNAME = ENIdolDeclaration.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENIdolDeclaration.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);

    private static final int COST = 0;
    private static final int BASE_MAGIC = 2;
    private static final int BASE_MAGIC2 = 1;

    private static final int BASE_HP = 2;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENIdolDeclaration() {
        super(ID, NAME, ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);
        this.updateShowImg = true;
        updateImg();
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.MORE_ACTION_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if(this.upgraded){
            addToBot(new ApplyPowerAction(m, m, new HalfDamageReceive(m, this.secondMagicNumber), this.secondMagicNumber));
        }
        else{
            addToBot(new LoseHPAction(m, m, this.HPMagicNumber));
        }
//        addToBot(new DrawCardAction(m, this.magicNumber));
        addToBot(new GainTrainRoundPowerAction(m,1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENIdolDeclaration();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
