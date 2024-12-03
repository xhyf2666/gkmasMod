package gkmasmod.downfall.cards.sense;

import gkmasmod.downfall.charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.actions.ModifyDamageAction;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.utils.IdolData;
import gkmasmod.utils.NameHelper;

public class ENOverwhelmingNumbers extends GkmasBossCard {
    private static final String CLASSNAME = ENOverwhelmingNumbers.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENOverwhelmingNumbers.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 2;

    private static final int BASE_MAGIC = 4;
    private static final int UPGRADE_PLUS_MAGIC = 4;

    private static final int BASE_DAMAGE = 0;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;


    public ENOverwhelmingNumbers() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "color");
        this.baseDamage = BASE_DAMAGE;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.intent = AbstractMonster.Intent.ATTACK;
        this.backGroundColor = IdolData.hume;
        updateBackgroundImg();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m instanceof AbstractCharBoss) {
            int count = ((AbstractCharBoss) m).cardsPlayedThisCombat - 1;
            int tmp = BASE_DAMAGE+count*BASE_MAGIC;
            addToBot(new ModifyDamageAction(p, new DamageInfo(m, tmp, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new ENOverwhelmingNumbers();
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
