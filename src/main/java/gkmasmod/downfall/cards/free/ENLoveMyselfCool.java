package gkmasmod.downfall.cards.free;

import gkmasmod.downfall.charbosses.actions.common.EnemyMakeTempCardInHandAction;
import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.powers.CoolModePower;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class ENLoveMyselfCool extends GkmasBossCard {
    private static final String CLASSNAME = ENLoveMyselfCool.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENLoveMyselfCool.class.getSimpleName().substring(2);
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

//    private String flavor = "";

    public ENLoveMyselfCool() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.backGroundColor = IdolData.amao;
        this.intent = AbstractMonster.Intent.MAGIC;
        updateBackgroundImg();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,m,new CoolModePower(m)));
        addToBot(new EnemyMakeTempCardInHandAction(new ENFightSkills()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENLoveMyselfCool();
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
        addToBot(new ApplyPowerAction(AbstractCharBoss.boss,AbstractCharBoss.boss,new CoolModePower(AbstractCharBoss.boss)));
        addToBot(new EnemyMakeTempCardInHandAction(new ENFightSkills()));
    }
}
