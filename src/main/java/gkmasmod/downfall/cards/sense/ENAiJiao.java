package gkmasmod.downfall.cards.sense;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gkmasmod.cards.GkmasCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.downfall.cards.GkmasBossCard;
import gkmasmod.downfall.cards.free.ENSleepLate;
import gkmasmod.powers.NotGoodTune;
import gkmasmod.utils.NameHelper;

public class ENAiJiao extends GkmasBossCard {
    private static final String CLASSNAME = ENAiJiao.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final String CLASSNAME2 = ENAiJiao.class.getSimpleName().substring(2);
    public static final String ID2 = NameHelper.makePath(CLASSNAME2);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID2);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = String.format("gkmasModResource/img/cards/common/%s.png", CLASSNAME2);

    private static final int COST = 2;
    private static final int ATTACK_DMG = 13;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int BASE_MAGIC = 4;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public ENAiJiao() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = ATTACK_DMG;
        this.intent = AbstractMonster.Intent.ATTACK_DEBUFF;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(p,p,new NotGoodTune(p,this.magicNumber),this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return  new ENAiJiao();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
