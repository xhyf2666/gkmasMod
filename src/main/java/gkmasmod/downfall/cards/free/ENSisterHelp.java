package gkmasmod.downfall.cards.free;

import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.logic.ENFirstFuture;
import gkmasmod.downfall.cards.logic.ENGoldfishScoopingChallenge;
import gkmasmod.downfall.cards.logic.ENMotherAI;
import gkmasmod.downfall.cards.logic.ENPow;
import gkmasmod.downfall.cards.sense.*;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

import java.util.ArrayList;

public class ENSisterHelp extends GkmasBossCard {
    private static final String CLASSNAME = ENSisterHelp.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENSisterHelp.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 0;

    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC_PLUS = 1;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

//    private String flavor = "";

    public ENSisterHelp() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.backGroundColor = IdolData.hume;
        updateBackgroundImg();
        this.exhaust = true;
        this.intent = AbstractMonster.Intent.MAGIC;
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EnemyMakeTempCardInHandAction(getOne()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENSisterHelp();
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

    public AbstractBossCard getOne(){
        ArrayList<AbstractBossCard> tmpPool = new ArrayList<>();
        tmpPool.add(new ENRisingStar());
        tmpPool.add(new ENFirstFuture());
        tmpPool.add(new ENNeverYieldFirst());
        tmpPool.add(new ENNeverLose());
        tmpPool.add(new ENPow());
        tmpPool.add(new ENGoldfishScoopingChallenge());
        tmpPool.add(new ENVeryEasy());
        tmpPool.add(new ENTrueLateBloomer());
        tmpPool.add(new ENMotherAI());
        AbstractBossCard tmp = tmpPool.get(AbstractDungeon.cardRandomRng.random(0,tmpPool.size()-1));
        return tmp;
    }


}
