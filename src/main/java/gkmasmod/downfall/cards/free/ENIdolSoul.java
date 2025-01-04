package gkmasmod.downfall.cards.free;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import gkmasmod.actions.GainTrainRoundPowerAction;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.powers.AutoUpgrade;
import gkmasmod.utils.ImageHelper;
import gkmasmod.utils.NameHelper;

public class ENIdolSoul extends GkmasBossCard {
    private static final String CLASSNAME = ENIdolSoul.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENIdolSoul.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 1;

    private static final int BLOCK_AMT = 3;
    private static final int UPGRADE_PLUS_BLOCK = 2;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENIdolSoul() {
        super(ID, NAME, ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        IMG_PATH = ImageHelper.idolImgPath(AbstractCharBoss.theIdolName, CLASSNAME2);
        this.updateShowImg = true;
        updateImg();
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = BLOCK_AMT;
        this.block = this.baseBlock;
        this.exhaust = true;
        this.isInnate = true;
        this.tags.add(GkmasCardTag.MORE_ACTION_TAG);

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(m, m, this.block));
        addToBot(new ApplyPowerAction(m, m, new ArtifactPower(m, this.magicNumber), this.magicNumber));
        if(this.upgraded){
            addToBot(new ApplyPowerAction(m, m, new AutoUpgrade(m, this.magicNumber), this.magicNumber));
        }
        addToBot(new GainTrainRoundPowerAction(m,1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENIdolSoul();
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
