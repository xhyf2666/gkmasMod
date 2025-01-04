package gkmasmod.cards.logic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.cards.GkmasCardTag;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.monster.friend.LittleGundam;
import gkmasmod.powers.SteelSoul;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class YoungDream extends GkmasCard {
    private static final String CLASSNAME = YoungDream.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME);

    private static final int COST = 1;
    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;


    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public YoungDream() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new FutureTrajectory();
        this.exhaust = true;
        this.backGroundColor = IdolData.kllj;
        updateBackgroundImg();
        this.tags.add(GkmasCardTag.OUTSIDE_TAG);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SpawnMonsterAction(new LittleGundam(p.hb_x+200, p.hb_y-50,p),false));
        if(this.upgraded)
            addToBot(new SpawnMonsterAction(new LittleGundam(p.hb_x+200, p.hb_y+250,p), false));
        addToBot(new MakeTempCardInHandAction(new FutureTrajectory()));
        addToBot(new ApplyPowerAction(p,p,new SteelSoul(p)));
        // TODO 召唤
    }

    @Override
    public AbstractCard makeCopy() {
        return new YoungDream();
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
