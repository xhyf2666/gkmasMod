package gkmasmod.downfall.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.cards.logic.FutureTrajectory;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.monster.friend.LittleGundam;
import gkmasmod.powers.SteelSoul;
import gkmasmod.utils.NameHelper;

public class ENYoungDream extends GkmasBossCard {
    private static final String CLASSNAME = ENYoungDream.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENYoungDream.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ENYoungDream() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new FutureTrajectory();
        this.intent = AbstractMonster.Intent.MAGIC;
        this.exhaust = true;
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SpawnMonsterAction(new LittleGundam(m.hb_x+200, m.hb_y-50,m), true));
        if(this.upgraded)
            addToBot(new SpawnMonsterAction(new LittleGundam(m.hb_x+200, m.hb_y+250,m), true));
//        addToBot(new EnemyMakeTempCardInHandAction(new FutureTrajectory()));
        addToBot(new ApplyPowerAction(m,m,new SteelSoul(m)));
        // TODO 召唤
    }

    @Override
    public AbstractCard makeCopy() {
        return new ENYoungDream();
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
