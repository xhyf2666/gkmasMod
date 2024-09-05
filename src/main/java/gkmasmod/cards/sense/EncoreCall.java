package gkmasmod.cards.sense;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gkmasmod.cards.AbstractDefaultCard;
import gkmasmod.characters.IdolCharacter;
import gkmasmod.characters.PlayerColorEnum;
import gkmasmod.powers.DoubleDamageReceive;
import gkmasmod.powers.GoodTune;
import gkmasmod.ui.SkinSelectScreen;
import gkmasmod.utils.NameHelper;
import gkmasmod.utils.PlayerHelper;

public class EncoreCall extends AbstractDefaultCard {
    private static final String CLASSNAME = EncoreCall.class.getSimpleName();
    public static final String ID = NameHelper.makePath(CLASSNAME);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static String IMG_PATH = String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);

    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    private static final int BASE_MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = -1;
    private static final int BASE_MAGIC2 = 2;
    private static final int UPGRADE_PLUS_MAGIC2 = 1;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = PlayerColorEnum.gkmasModColorSense;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public EncoreCall() {
        super(ID, NAME, String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        IMG_PATH = String.format("img/idol/%s/cards/%s.png", SkinSelectScreen.Inst.idolName, CLASSNAME);
        this.updateShowImg = true;
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = BASE_MAGIC2;
        this.secondMagicNumber = this.baseSecondMagicNumber;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom( new DamageAction( m, new DamageInfo( p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -this.magicNumber), -this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new GoodTune(p, this.secondMagicNumber), this.secondMagicNumber));
        addToBot(new GainEnergyAction(1));

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = PlayerHelper.getPowerAmount(p,StrengthPower.POWER_ID);
        if (count >= this.magicNumber)
            return true;
        // TODO 本地化
        this.cantUseMessage = "力量还不够";
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return  new EncoreCall();

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeSecondMagicNumber(UPGRADE_PLUS_MAGIC2);
            if (CARD_STRINGS.UPGRADE_DESCRIPTION != null)
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
