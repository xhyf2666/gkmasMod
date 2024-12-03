package gkmasmod.downfall.cards.logic;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.BlockDamageWallopAction;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.free.ENSleepLate;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class ENSlowGrowth extends GkmasBossCard {
    private static final String CLASSNAME = ENSlowGrowth.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENSlowGrowth.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s_%d.png", CLASSNAME2,0);

    private static final int COST = 1;

    private static final int BASE_MAGIC = 20;
    private static final int UPGRADE_PLUS_MAGIC = 10;

    private static final int BASE_HP = 4;

    private int playCount;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorLogic;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private String flavor = "";

    public ENSlowGrowth() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        playCount = 0;
        this.intent = AbstractMonster.Intent.ATTACK_DEFEND;
        this.backGroundColor = IdolData.kcna;
        updateBackgroundImg();
    }

    public ENSlowGrowth(int playCount){
        super(ID, NAME, String.format("gkmasModResource/img/cards/common/%s_%d.png", CLASSNAME2,playCount), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET,"color");
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseHPMagicNumber = BASE_HP;
        this.HPMagicNumber = this.baseHPMagicNumber;
        this.playCount = playCount;
        this.growMagicNumber = 3-this.playCount;
        this.intent = AbstractMonster.Intent.ATTACK_DEFEND;
        this.backGroundColor = IdolData.kcna;
        updateBackgroundImg();
        loadCardImage(String.format("gkmasModResource/img/cards/common/%s_%d.png", CLASSNAME2,this.playCount));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(m, m, this.HPMagicNumber));
        addToBot(new BlockDamageWallopAction(1.0F * this.magicNumber / 100, 0, m, p,this));
    }


    @Override
    public AbstractCard makeCopy() {
        return new ENSlowGrowth();
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
