package gkmasmod.downfall.cards.free;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.relics.*;
import gkmasmod.powers.MyPrideBigSisterPower;
import gkmasmod.powers.SayGoodbyeToDislikeMyselfPower;
import gkmasmod.utils.NameHelper;

public class ENTriggerRelic extends GkmasBossCard {
    private static final String CLASSNAME = ENTriggerRelic.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENTriggerRelic.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 0;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColor;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;


    public ENTriggerRelic() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(AbstractCharBoss.boss.hasRelic(CBR_SidewalkResearchNotes.ID2)){
            ((CBR_SidewalkResearchNotes)AbstractCharBoss.boss.getRelic(CBR_SidewalkResearchNotes.ID2)).onTrainRoundRemove();
        }
        if(AbstractCharBoss.boss.hasRelic(CBR_LifeSizeLadyLip.ID2)){
            ((CBR_LifeSizeLadyLip)AbstractCharBoss.boss.getRelic(CBR_LifeSizeLadyLip.ID2)).onTrainRoundRemove();
        }
        if(AbstractCharBoss.boss.hasRelic(CBR_UltimateSleepMask.ID2)){
            ((CBR_UltimateSleepMask)AbstractCharBoss.boss.getRelic(CBR_UltimateSleepMask.ID2)).onTrainRoundRemove();
        }
        if(AbstractCharBoss.boss.hasRelic(CBR_HeartFlutteringCup.ID2)){
            ((CBR_HeartFlutteringCup)AbstractCharBoss.boss.getRelic(CBR_HeartFlutteringCup.ID2)).onTrainRoundRemove();
        }
        if(AbstractCharBoss.boss.hasRelic(CBR_FirstHeartProofChina.ID2)){
            ((CBR_FirstHeartProofChina)AbstractCharBoss.boss.getRelic(CBR_FirstHeartProofChina.ID2)).onTrainRoundRemove();
        }
        if(AbstractCharBoss.boss.hasRelic(CBR_ChristmasLion.ID2)){
            ((CBR_ChristmasLion)AbstractCharBoss.boss.getRelic(CBR_ChristmasLion.ID2)).onTrainRoundRemove();
        }
        if(AbstractCharBoss.boss.hasPower(MyPrideBigSisterPower.POWER_ID)){
            AbstractCharBoss.boss.getPower(MyPrideBigSisterPower.POWER_ID).onSpecificTrigger();
        }
        if(AbstractCharBoss.boss.hasRelic(SayGoodbyeToDislikeMyselfPower.POWER_ID)){
            AbstractCharBoss.boss.getPower(SayGoodbyeToDislikeMyselfPower.POWER_ID).onSpecificTrigger();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENTriggerRelic();
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
