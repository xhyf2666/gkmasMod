package gkmasmod.cards.othe;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.free.Gacha;
import gkmasmod.cards.free.GachaAgain;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.GachaAgainPlusPower;
import gkmasmod.powers.GachaAgainPower;
import gkmasmod.powers.GachaAgainSPPower;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class UniverseFirstHappiness extends GkmasCard {
    private static final String CLASSNAME = UniverseFirstHappiness.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorOther;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public UniverseFirstHappiness() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.cardsToPreview = new GachaAgain();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count1 = PlayerHelper.getPowerAmount(p, GachaAgainPower.POWER_ID);
        int count2 = PlayerHelper.getPowerAmount(p, GachaAgainSPPower.POWER_ID);
        int count3 = PlayerHelper.getPowerAmount(p, GachaAgainPlusPower.POWER_ID);
        if(count1+count2+count3==0){
            addToBot(new MakeTempCardInHandAction(new Gacha()));
        }
        if(count1>0){
            p.getPower(GachaAgainPower.POWER_ID).atStartOfTurn();
        }
        if(count2>0){
            p.getPower(GachaAgainSPPower.POWER_ID).atStartOfTurn();
        }
        if(count3>0){
            p.getPower(GachaAgainPlusPower.POWER_ID).atStartOfTurn();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new UniverseFirstHappiness();
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
