package gkmasmod.downfall.cards.free;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.GreatGoodTune;
import gkmasmod.utils.NameHelper;

public class ENProduceCompetitorGreatGoodTune extends GkmasBossCard {
    private static final String CLASSNAME = ENProduceCompetitorGreatGoodTune.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENProduceCompetitorGreatGoodTune.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 0;

    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    private static final int BASE_MAGIC2 = 1;

    private static final int BASE_MAGIC3 = 1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENProduceCompetitorGreatGoodTune() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.magicNumber = this.baseMagicNumber = BASE_MAGIC;
        this.baseSecondMagicNumber = this.secondMagicNumber = BASE_MAGIC2;
        this.baseThirdMagicNumber = this.thirdMagicNumber = BASE_MAGIC3;
        this.intent = AbstractMonster.Intent.BUFF;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new GreatGoodTune(p,this.thirdMagicNumber),this.thirdMagicNumber));
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            if(!mo.isDeadOrEscaped()&&!AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                addToBot(new ApplyPowerAction(mo, p, new GreatGoodTune(mo, this.thirdMagicNumber), this.thirdMagicNumber));
            }
        }
        addToBot(new EnemyMakeTempCardInHandAction(new ENLittleAmbition()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENProduceCompetitorGreatGoodTune();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
