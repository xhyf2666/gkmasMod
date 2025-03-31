package gkmasmod.cards.free;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.patches.AbstractMonsterPatch;
import gkmasmod.powers.GoodTune;
import gkmasmod.powers.GreatGoodTune;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class ProduceCompetitorGreatGoodTune extends GkmasCard {
    private static final String CLASSNAME = ProduceCompetitorGreatGoodTune.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 0;

    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    private static final int BASE_MAGIC2 = 1;

    private static final int BASE_MAGIC3 = 1;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ProduceCompetitorGreatGoodTune() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.magicNumber = this.baseMagicNumber = BASE_MAGIC;
        this.baseSecondMagicNumber = this.secondMagicNumber = BASE_MAGIC2;
        this.baseThirdMagicNumber = this.thirdMagicNumber = BASE_MAGIC3;
        this.cardsToPreview = new LittleAmbition();
        this.backGroundColor = IdolData.jsna;
        updateBackgroundImg();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new GreatGoodTune(p,this.thirdMagicNumber),this.thirdMagicNumber));
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            if(!mo.isDeadOrEscaped()&&!AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                addToBot(new ApplyPowerAction(mo, p, new GreatGoodTune(mo, this.thirdMagicNumber), this.thirdMagicNumber));
            }
        }
        addToBot(new MakeTempCardInHandAction(new LittleAmbition()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ProduceCompetitorGreatGoodTune();
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

    @Override
    public void onChoseThisOption() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new GreatGoodTune(AbstractDungeon.player,this.thirdMagicNumber),this.thirdMagicNumber));
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            if(!mo.isDeadOrEscaped()&&!AbstractMonsterPatch.friendlyField.friendly.get(mo)){
                addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new GreatGoodTune(mo, this.thirdMagicNumber), this.thirdMagicNumber));
            }
        }
        addToBot(new MakeTempCardInHandAction(new LittleAmbition()));
    }
}
