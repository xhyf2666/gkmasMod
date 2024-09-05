package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.AchievementPower;
import gkmasmod.powers.GoodTune;
import gkmasmod.ui.SkinSelectScreen;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class Achievement extends AbstractDefaultCard {
    private static final String CLASSNAME = Achievement.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 3;

    private static final int ATTACK_DMG = 32;
    private static final int UPGRADE_PLUS_DMG = 8;
    private static final int BASE_MAGIC = 4;
    private static final int UPGRADE_PLUS_MAGIC = 3;


    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Achievement() {
        super(ID, NAME, String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new GoodTune(p,this.magicNumber),this.magicNumber));
        addToBot(new ApplyPowerAction(p,p,new AchievementPower(p,this.baseDamage),this.baseDamage));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Achievement();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


}
